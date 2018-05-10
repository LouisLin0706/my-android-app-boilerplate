package com.boilerplate.lib_player.core;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.IntDef;
import android.view.SurfaceView;

import java.lang.annotation.Retention;
import java.util.concurrent.CopyOnWriteArraySet;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by Louis on 2018/4/13.
 */

public abstract class HybridPlayBack {


    protected CopyOnWriteArraySet<IHybridPlayerEventListener> iHybridPlayerEventListeners;
    protected boolean shouldAutoPlay;
    protected boolean isBufferIng;
    protected boolean isPrepare;

    @Retention(SOURCE)
    @IntDef({TYPE_ANDROID_NATIVE_PLAYBACK, TYPE_EXO_PLAYBACK, TYPE_DEFAULT})
    public @interface PlaybackType {
    }

    public static final int TYPE_ANDROID_NATIVE_PLAYBACK = 0;
    public static final int TYPE_EXO_PLAYBACK = 1;
    public static final int TYPE_DEFAULT = 2;


    public abstract static class HybridPlayBackManager {


    }

    public HybridPlayBack() {
        iHybridPlayerEventListeners = new CopyOnWriteArraySet<>();
    }

    public void addHybridEventListener(IHybridPlayerEventListener iHybridPlayerEventListener) {
        iHybridPlayerEventListeners.add(iHybridPlayerEventListener);
    }

    public void removeHybridEventListener(IHybridPlayerEventListener iHybridPlayerEventListener) {
        iHybridPlayerEventListeners.remove(iHybridPlayerEventListener);
    }

    public HybridRulePlayBack createInstance(Context context) {
        return createInstance(context, TYPE_DEFAULT);
    }

    public HybridRulePlayBack createInstance(Context context, @PlaybackType int type) {
        HybridRulePlayBack hybridPlayBack;
        switch (type) {
            case TYPE_ANDROID_NATIVE_PLAYBACK:
                hybridPlayBack = new AndroidMediaPlayBack(context);
                break;
            case TYPE_EXO_PLAYBACK:
                hybridPlayBack = new ExoPlayBack(context);
                break;
            default:
                int currentapiVersion = android.os.Build.VERSION.SDK_INT;
                if (currentapiVersion >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    hybridPlayBack = new ExoPlayBack(context, null);
                } else {
                    hybridPlayBack = new AndroidMediaPlayBack(context);
                }
                break;
        }
        return hybridPlayBack;
    }

    public abstract void setDataSource(Uri path);

    public void setDataSource(Uri path, String overrideExtension) {
        if (isPrepare) {
            release();
            initialize();
        }
    }

    public abstract void play();

    public abstract void pause();

    public abstract void seekTo(int msec);

    public abstract int getDuration();

    public abstract int getCurrentPosition();

    public abstract float getVolume();

    public abstract void setVolume(float level);

    public abstract boolean isPlaying();

    public abstract void release();

    public abstract int getBufferedPercentage();

    public abstract void setPlaybackParams(float speed, float pitch);

    public abstract void initialize();

    public abstract void setPlayerView(Context context, SurfaceView surfaceView);
}
