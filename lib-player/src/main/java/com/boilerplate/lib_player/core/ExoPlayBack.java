package com.boilerplate.lib_player.core;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.boilerplate.lib_player.extension.OverrideExtensionAdapter;
import com.boilerplate.lib_player.extension.cdn.CDNHttpDataSourceFactory;
import com.boilerplate.lib_player.view.HybridPlayerView;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.mediacodec.MediaCodecRenderer;
import com.google.android.exoplayer2.mediacodec.MediaCodecUtil;
import com.google.android.exoplayer2.source.BehindLiveWindowException;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.ads.AdsLoader;
import com.google.android.exoplayer2.source.ads.AdsMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.DefaultSsChunkSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;

import java.lang.reflect.Constructor;

/**
 * Created by Louis on 2018/4/13.
 * TODO Add IMAMedia source in feature
 * TODO WindWind drm
 * TODO Aes dram
 */
public class ExoPlayBack extends HybridLifecyclePlayBack implements PlaybackPreparer {
    private DefaultBandwidthMeter BANDWIDTH_METER;
    private SimpleExoPlayer player;
    private DefaultTrackSelector trackSelector;
    private EventLogger eventLogger;
    private Context context;
    private boolean inErrorState;
    protected String userAgent;
    private AdsLoader adsLoader;
    private Handler mainHandler;
    private ViewGroup adUiViewGroup;

    public ExoPlayBack(@NonNull Context context) {
        this(context, null);
    }

    public ExoPlayBack(Context context, HybridPlayerView hybridPlayerView) {
        super(hybridPlayerView);
        this.context = context;
        mainHandler = new Handler();
        BANDWIDTH_METER = new DefaultBandwidthMeter(mainHandler, new BandwidthMeter.EventListener() {
            @Override
            public void onBandwidthSample(int elapsedMs, long bytes, long bitrate) {
                Log.d("Test_band_width", "elapsedMs=" + elapsedMs + "  bytes = " + bytes + "  bitrate=" + bitrate);
            }
        });
        this.userAgent = Util.getUserAgent(context, "HybridExo");
        initialize();
    }

    @Override
    public void preSetDataSource(Uri path) {
        this.preSetDataSource(path, null);
    }

    @Override
    public void preSetDataSource(Uri path, OverrideExtensionAdapter overrideExtension) {
        super.preSetDataSource(path, overrideExtension);
    }

    @Override
    public void setDataSourceToPlay() {
        super.setDataSourceToPlay();
        boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
        if (haveResumePosition) {
            player.seekTo(resumeWindow, resumePosition);
        }
        player.prepare(buildMediaSource(currentUri, currentOverrideExtensionAdapter, mainHandler, eventLogger), !haveResumePosition, false);
        inErrorState = false;
    }

    @Override
    public void play() {
        if (player == null) {
            return;
        }
        player.setPlayWhenReady(true);
    }

    @Override
    public void pause() {
        if (player == null) {
            return;
        }
        player.setPlayWhenReady(false);
    }

    @Override
    public void seekTo(int msec) {
        if (player == null) {
            return;
        }
        player.seekTo(msec);
        updateResumePosition();
    }

    @Override
    public int getDuration() {
        if (player == null) {
            return 0;
        }
        return (int) player.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        if (player == null) {
            return 0;
        }
        return (int) player.getCurrentPosition();
    }

    @Override
    public float getVolume() {
        if (player == null) {
            return 0;
        }
        return player.getVolume();
    }

    @Override
    public void setVolume(float level) {
        if (player == null) {
            return;
        }
        player.setVolume(level);
    }

    @Override
    public boolean isPlaying() {
        return player != null && player.getPlayWhenReady();
    }

    @Override
    public void release() {
        super.release();
        if (player != null) {
            shouldAutoPlay = player.getPlayWhenReady();
            updateResumePosition();
            player.release();
            player = null;
            trackSelector = null;
            eventLogger = null;
            isPrepare = false;
        }
    }

    @Override
    public int getBufferedPercentage() {
        return player.getBufferedPercentage();
    }

    @Override
    public void setPlaybackParams(float speed, float pitch) {
        PlaybackParameters params = new PlaybackParameters(speed, pitch);
        player.setPlaybackParameters(params);
    }

    @Override
    public void initialize() {
        boolean needNewPlayer = player == null;
        if (needNewPlayer) {
            TrackSelection.Factory adaptiveTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
            trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
            eventLogger = new EventLogger(trackSelector);
            /*
             * drm
            DrmSessionManager<FrameworkMediaCrypto> drmSessionManager = null;
            if (intent.hasExtra(DRM_SCHEME_EXTRA) || intent.hasExtra(DRM_SCHEME_UUID_EXTRA)) {
                String drmLicenseUrl = intent.getStringExtra(DRM_LICENSE_URL);
                String[] keyRequestPropertiesArray = intent.getStringArrayExtra(DRM_KEY_REQUEST_PROPERTIES);
                boolean multiSession = intent.getBooleanExtra(DRM_MULTI_SESSION, false);
                int errorStringId = R.string.error_drm_unknown;
                if (Util.SDK_INT < 18) {
                    errorStringId = R.string.error_drm_not_supported;
                } else {
                    try {
                        String drmSchemeExtra = intent.hasExtra(DRM_SCHEME_EXTRA) ? DRM_SCHEME_EXTRA
                                : DRM_SCHEME_UUID_EXTRA;
                        UUID drmSchemeUuid = Util.getDrmUuid(intent.getStringExtra(drmSchemeExtra));
                        if (drmSchemeUuid == null) {
                            errorStringId = R.string.error_drm_unsupported_scheme;
                        } else {
                            drmSessionManager =
                                    buildDrmSessionManagerV18(
                                            drmSchemeUuid, drmLicenseUrl, keyRequestPropertiesArray, multiSession);
                        }
                    } catch (UnsupportedDrmException e) {
                        errorStringId = e.reason == UnsupportedDrmException.REASON_UNSUPPORTED_SCHEME
                                ? R.string.error_drm_unsupported_scheme : R.string.error_drm_unknown;
                    }
                }
                if (drmSessionManager == null) {
                    showToast(errorStringId);
                    return;
                }
            }
            */
            DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(context,
                    null);
            player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector);
            player.addListener(new PlayerEventListener());
            player.addListener(eventLogger);
            player.addMetadataOutput(eventLogger);
            player.addAudioDebugListener(eventLogger);
            player.addVideoDebugListener(eventLogger);
            player.setPlayWhenReady(shouldAutoPlay);
            if (hybridPlayerView != null) {
                setUpSurfaceView();
                player.addVideoListener(hybridPlayerView);
                player.addTextOutput(hybridPlayerView);
            }
        }
        super.initialize();
    }

    private void releaseAdsLoader() {
        if (adsLoader != null) {
            adsLoader.release();
            adsLoader = null;
            if (hybridPlayerView != null) {
                hybridPlayerView.removeViewInLayout(adUiViewGroup);
            }
        }
    }

    /**
     * Returns an ads media source, reusing the ads loader if one exists.
     */
    private @Nullable
    MediaSource createAdsMediaSource(MediaSource mediaSource, Uri adTagUri) {
        // Load the extension source using reflection so the demo app doesn't have to depend on it.
        // The ads loader is reused for multiple playbacks, so that ad playback can resume.
        try {
            Class<?> loaderClass = Class.forName("com.google.android.exoplayer2.ext.ima.ImaAdsLoader");
            if (adsLoader == null) {
                // Full class names used so the LINT.IfChange rule triggers should any of the classes move.
                // LINT.IfChange
                Constructor<? extends AdsLoader> loaderConstructor =
                        loaderClass
                                .asSubclass(AdsLoader.class)
                                .getConstructor(android.content.Context.class, android.net.Uri.class);
                // LINT.ThenChange(../../../../../../../../proguard-rules.txt)
                adsLoader = loaderConstructor.newInstance(this, adTagUri);
                adUiViewGroup = new FrameLayout(context);
                // The demo app has a non-null overlay frame layout.
                hybridPlayerView.addView(adUiViewGroup);
            }
            AdsMediaSource.MediaSourceFactory adMediaSourceFactory =
                    new AdsMediaSource.MediaSourceFactory() {
                        @Override
                        public MediaSource createMediaSource(
                                Uri uri, @Nullable Handler handler, @Nullable MediaSourceEventListener listener) {
                            return buildMediaSource(
                                    uri, /* overrideExtension= */ null, handler, listener);
                        }

                        @Override
                        public int[] getSupportedTypes() {
                            return new int[]{C.TYPE_DASH, C.TYPE_SS, C.TYPE_HLS, C.TYPE_OTHER};
                        }
                    };
            return new AdsMediaSource(
                    mediaSource, adMediaSourceFactory, adsLoader, adUiViewGroup, mainHandler, eventLogger);
        } catch (ClassNotFoundException e) {
            // IMA extension not loaded.
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private MediaSource buildMediaSource(
            Uri uri,
            OverrideExtensionAdapter overrideExtension,
            @Nullable Handler handler,
            @Nullable MediaSourceEventListener listener) {
        @C.ContentType int type = (overrideExtension == null || TextUtils.isEmpty(overrideExtension.getOverrideSourceType())) ? Util.inferContentType(uri)
                : Util.inferContentType("." + overrideExtension.getOverrideSourceType());
        switch (type) {
            case C.TYPE_DASH:
                return new DashMediaSource.Factory(
                        new DefaultDashChunkSource.Factory(buildHttpDataSourceFactory(true)),
                        buildDataSourceFactory(false))
                        .createMediaSource(uri, handler, listener);
            case C.TYPE_SS:
                return new SsMediaSource.Factory(
                        new DefaultSsChunkSource.Factory(buildHttpDataSourceFactory(true)),
                        buildDataSourceFactory(false))
                        .createMediaSource(uri, handler, listener);
            case C.TYPE_HLS:
                return new HlsMediaSource.Factory(buildHttpDataSourceFactory(true))
                        .createMediaSource(uri, handler, listener);
            case C.TYPE_OTHER:
                return new ExtractorMediaSource.Factory(buildHttpDataSourceFactory(true))
                        .createMediaSource(uri, handler, listener);
            default: {
                throw new IllegalStateException("Unsupported type: " + type);
            }
        }
    }


    /**
     * Returns a new DataSource factory.
     *
     * @param useBandwidthMeter Whether to set {@link #BANDWIDTH_METER} as a listener to the new
     *                          DataSource factory.
     * @return A new DataSource factory.
     */
    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    /**
     * Returns a new HttpDataSource factory.
     *
     * @param useBandwidthMeter Whether to set {@link #BANDWIDTH_METER} as a listener to the new
     *                          DataSource factory.
     * @return A new HttpDataSource factory.
     */
    private HttpDataSource.Factory buildHttpDataSourceFactory(boolean useBandwidthMeter) {
        return buildHttpDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    /**
     * Returns a {@link DataSource.Factory}.
     */
    public DataSource.Factory buildDataSourceFactory(TransferListener<? super DataSource> listener) {
        return new DefaultDataSourceFactory(context, listener, buildHttpDataSourceFactory(listener));
    }

    /**
     * Returns a {@link HttpDataSource.Factory}.
     */
    public HttpDataSource.Factory buildHttpDataSourceFactory(
            TransferListener<? super DataSource> listener) {
        return new CDNHttpDataSourceFactory(userAgent, listener);
    }


    private void updateResumePosition() {
        resumeWindow = player.getCurrentWindowIndex();
        resumePosition = Math.max(0, player.getContentPosition());
    }

    private void clearResumePosition() {
        resumeWindow = C.INDEX_UNSET;
        resumePosition = C.TIME_UNSET;
    }

    @Override
    public void setPlayerView(Context context, SurfaceView surfaceView) {

    }

    @Override
    public void preparePlayback() {

    }

    @Override
    protected void setSurface(SurfaceTexture surface) {
        if (player != null) {
            player.setVideoTextureView(hybridPlayerView.getTextureView());
        }
    }

    private class PlayerEventListener extends Player.DefaultEventListener {

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            switch (playbackState) {
                case Player.STATE_ENDED:
                    for (IHybridPlayerEventListener iHybridPlayerEventListener : hybridPlayerEventAdapters) {
                        iHybridPlayerEventListener.onCompletion(ExoPlayBack.this);
                    }
                    break;
                case Player.STATE_READY:
                    if (isBufferIng == true && playWhenReady) {
                        isBufferIng = false;
                        for (IHybridPlayerEventListener iHybridPlayerEventListener : hybridPlayerEventAdapters) {
                            iHybridPlayerEventListener.onBufferEnd();
                        }
                    }
                    if (isPrepare == false) {
                        for (IHybridPlayerEventListener iHybridPlayerEventListener : hybridPlayerEventAdapters) {
                            iHybridPlayerEventListener.onPrepared(ExoPlayBack.this);
                        }
                        isPrepare = true;
                    }
                    if (playWhenReady) {
                        for (IHybridPlayerEventListener iHybridPlayerEventListener : hybridPlayerEventAdapters) {
                            iHybridPlayerEventListener.onStatePlay();
                        }
                    } else {
                        for (IHybridPlayerEventListener iHybridPlayerEventListener : hybridPlayerEventAdapters) {
                            iHybridPlayerEventListener.onStatePause();
                        }
                    }
                    break;

                case Player.STATE_BUFFERING:
                    isBufferIng = true;
                    for (IHybridPlayerEventListener iHybridPlayerEventListener : hybridPlayerEventAdapters) {
                        iHybridPlayerEventListener.onBufferStart();
                    }
                    break;
                case Player.STATE_IDLE:
                    break;
            }
            if (hybridPlayerView != null && playbackState == Player.STATE_ENDED) {
                hybridPlayerView.showController();
            }
//            updateButtonVisibilities();
        }

        @Override
        public void onPositionDiscontinuity(@Player.DiscontinuityReason int reason) {
            if (inErrorState) {
                // This will only occur if the user has performed a seek whilst in the error state. Update
                // the resume position so that if the user then retries, playback will resume from the
                // position to which they seeked.
                updateResumePosition();
            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException e) {
            for (IHybridPlayerEventListener iHybridPlayerEventListener : hybridPlayerEventAdapters) {
                iHybridPlayerEventListener.onError(ExoPlayBack.this);
            }
            String errorString = null;
            if (e.type == ExoPlaybackException.TYPE_RENDERER) {
                Exception cause = e.getRendererException();
                if (cause instanceof MediaCodecRenderer.DecoderInitializationException) {
                    // Special case for decoder initialization failures.
                    MediaCodecRenderer.DecoderInitializationException decoderInitializationException =
                            (MediaCodecRenderer.DecoderInitializationException) cause;
                    if (decoderInitializationException.decoderName == null) {
                        if (decoderInitializationException.getCause() instanceof MediaCodecUtil.DecoderQueryException) {
//                            errorString = getString(R.string.error_querying_decoders);
                        } else if (decoderInitializationException.secureDecoderRequired) {
//                            errorString = getString(R.string.error_no_secure_decoder,
//                                    decoderInitializationException.mimeType);
                        } else {
//                            errorString = getString(R.string.error_no_decoder,
//                                    decoderInitializationException.mimeType);
                        }
                    } else {
//                        errorString = getString(R.string.error_instantiating_decoder,
//                                decoderInitializationException.decoderName);
                    }
                }
            }
            if (errorString != null) {
//                showToast(errorString);
            }
            inErrorState = true;
            if (isBehindLiveWindow(e)) {
                clearResumePosition();
                initialize();
            } else {
                updateResumePosition();
                //show maybe retry ui
            }
        }

        @Override
        @SuppressWarnings("ReferenceEquality")
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//            updateButtonVisibilities();
//            if (trackGroups != lastSeenTrackGroupArray) {
//                MappingTrackSelector.MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
//                if (mappedTrackInfo != null) {
//                    if (mappedTrackInfo.getTrackTypeRendererSupport(C.TRACK_TYPE_VIDEO)
//                            == MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
//                        showToast(R.string.error_unsupported_video);
//                    }
//                    if (mappedTrackInfo.getTrackTypeRendererSupport(C.TRACK_TYPE_AUDIO)
//                            == MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
//                        showToast(R.string.error_unsupported_audio);
//                    }
//                }
//                lastSeenTrackGroupArray = trackGroups;
//            }
        }

    }


    private static boolean isBehindLiveWindow(ExoPlaybackException e) {
        if (e.type != ExoPlaybackException.TYPE_SOURCE) {
            return false;
        }
        Throwable cause = e.getSourceException();
        while (cause != null) {
            if (cause instanceof BehindLiveWindowException) {
                return true;
            }
            cause = cause.getCause();
        }
        return false;
    }
}
