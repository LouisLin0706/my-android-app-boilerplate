package com.boilerplate.lib_player.core;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.Uri;
import android.view.Surface;
import android.view.SurfaceView;

import com.boilerplate.lib_player.extension.OverrideExtensionAdapter;
import com.boilerplate.lib_player.view.HybridPlayerView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Louis on 2018/4/13.
 */

public class AndroidMediaPlayBack extends HybridLifecyclePlayBack {
    private MediaPlayer mediaPlayer;
    private Context context;
    private EventListener eventListener;
    private float volume;


    public AndroidMediaPlayBack(Context context) {
        this(context, null);
    }

    public AndroidMediaPlayBack(Context context, HybridPlayerView hybridPlayerView) {
        super(hybridPlayerView);
        this.context = context;
        this.hybridPlayerView = hybridPlayerView;
        this.eventListener = new EventListener();
        volume = 1;
        initialize();
    }

    @Override
    public void preSetDataSource(Uri path) {
        preSetDataSource(path, null);
    }

    @Override
    public void preSetDataSource(Uri path, OverrideExtensionAdapter overrideExtensionAdapter) {
        super.preSetDataSource(path, overrideExtensionAdapter);
    }

    @Override
    public void setDataSourceToPlay() {
        super.setDataSourceToPlay();
        Map<String, String> headers = new HashMap<String, String>();
        if (currentOverrideExtensionAdapter != null) {
            headers.put(currentOverrideExtensionAdapter.getAuthorizationHeader(), currentOverrideExtensionAdapter.getAuthorizationToken());
        }
        try {
            mediaPlayer.setDataSource(context, currentUri, headers);
            mediaPlayer.setOnVideoSizeChangedListener(hybridPlayerView);
            mediaPlayer.setOnCompletionListener(eventListener);
            mediaPlayer.setOnBufferingUpdateListener(eventListener);
            mediaPlayer.setOnPreparedListener(eventListener);
            mediaPlayer.setOnErrorListener(eventListener);
            mediaPlayer.setOnInfoListener(eventListener);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void play() {
        mediaPlayer.start();
        isBufferIng = false;
        for (IHybridPlayerEventListener iHybridPlayerEventListener : hybridPlayerEventAdapters) {
            iHybridPlayerEventListener.onStatePlay();
        }
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
        for (IHybridPlayerEventListener iHybridPlayerEventListener : hybridPlayerEventAdapters) {
            iHybridPlayerEventListener.onStatePause();
        }
    }

    @Override
    public void seekTo(int msec) {
        mediaPlayer.seekTo(msec);

    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public float getVolume() {
        return volume;
    }

    @Override
    public void setVolume(float level) {
        volume = level;
        mediaPlayer.setVolume(level, level);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public void release() {
        super.release();
        if (mediaPlayer != null) {
            resumeWindow = 1;
            resumePosition = Math.max(0, mediaPlayer.getCurrentPosition());
            shouldAutoPlay = mediaPlayer.isPlaying();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public int getBufferedPercentage() {
        return 0;
    }

    @Override
    public void setPlaybackParams(float speed, float pitch) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            PlaybackParams params = new PlaybackParams();
            params.setSpeed(speed);
            params.setPitch(pitch);
            mediaPlayer.setPlaybackParams(params);
        }
    }

    @Override
    public void initialize() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        super.initialize();
    }

    @Override
    public void setPlayerView(Context context, SurfaceView surfaceView) {

    }

    @Override
    protected void setSurface(SurfaceTexture surface) {
        mediaPlayer.setSurface(new Surface(surface));
    }

    private class EventListener implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener {


        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {

        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            for (IHybridPlayerEventListener iHybridPlayerEventListener : hybridPlayerEventAdapters) {
                iHybridPlayerEventListener.onCompletion(AndroidMediaPlayBack.this);
            }
        }

        @Override
            public void onPrepared(MediaPlayer mp) {
            mp.seekTo((int) resumePosition);
            if (shouldAutoPlay) {
                mp.start();
            } else {
                mp.pause();
            }
            for (IHybridPlayerEventListener iHybridPlayerEventListener : hybridPlayerEventAdapters) {
                iHybridPlayerEventListener.onPrepared(AndroidMediaPlayBack.this);
            }
        }

        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            for (IHybridPlayerEventListener iHybridPlayerEventListener : hybridPlayerEventAdapters) {
                iHybridPlayerEventListener.onError(AndroidMediaPlayBack.this);
            }
            return true;
        }

        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            switch (what) {
                case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                    isBufferIng = true;
                    for (IHybridPlayerEventListener iHybridPlayerEventListener : hybridPlayerEventAdapters) {
                        iHybridPlayerEventListener.onBufferStart();
                    }
                    break;
                case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    isBufferIng = false;
                    for (IHybridPlayerEventListener iHybridPlayerEventListener : hybridPlayerEventAdapters) {
                        iHybridPlayerEventListener.onBufferEnd();
                    }
                    break;
            }
            return true;
        }
    }

}
