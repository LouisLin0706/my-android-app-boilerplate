package com.boilerplate.lib_player.core;

import android.graphics.SurfaceTexture;
import android.view.TextureView;

/**
 * Created by Louis on 2018/4/13.
 */

public abstract class HybridRulePlayBack extends HybridPlayBack {

    protected HybridPlayerView hybridPlayerView;

    public HybridRulePlayBack(HybridPlayerView hybridPlayerView) {
        this.hybridPlayerView = hybridPlayerView;
        setUpSurfaceView();
    }

    public IControllerView getIControllerView() {
        return hybridPlayerView.getIControllerView();
    }


    public void setHybridPlayerView(HybridPlayerView hybridPlayerView) {
        this.hybridPlayerView = hybridPlayerView;
        setUpSurfaceView();
    }


    protected void setUpSurfaceView() {
        if (hybridPlayerView != null && hybridPlayerView.getTextureView().isAvailable()) {
            setSurface(hybridPlayerView.getTextureView().getSurfaceTexture());
        } else if (hybridPlayerView != null) {
            hybridPlayerView.getTextureView().setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                    setSurface(surface);
                }

                @Override
                public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

                }

                @Override
                public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                    return false;
                }

                @Override
                public void onSurfaceTextureUpdated(SurfaceTexture surface) {

                }
            });
        } else {
            // do nothing
        }
    }

    protected abstract void setSurface(SurfaceTexture surface);
}

