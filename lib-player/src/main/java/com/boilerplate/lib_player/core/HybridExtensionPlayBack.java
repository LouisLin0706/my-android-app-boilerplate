package com.boilerplate.lib_player.core;

import android.graphics.SurfaceTexture;
import android.net.Uri;
import android.view.TextureView;

import com.boilerplate.lib_player.component.AutoSyncControllerComponent;
import com.boilerplate.lib_player.extension.OverrideExtensionAdapter;
import com.boilerplate.lib_player.view.HybridPlayerView;

/**
 * Created by Louis on 2018/4/13.
 */

public abstract class HybridExtensionPlayBack extends HybridPlayBack {

    public HybridPlayerView hybridPlayerView;
    private AutoSyncControllerComponent autoSyncControllerComponent;
    protected Uri currentUri;
    protected OverrideExtensionAdapter currentOverrideExtensionAdapter;

    public HybridExtensionPlayBack(HybridPlayerView hybridPlayerView) {
        setHybridPlayerView(hybridPlayerView);
        setUpSurfaceView();
    }

    public void setAutoSyncControllerComponent(AutoSyncControllerComponent autoSyncControllerComponent) {
        this.autoSyncControllerComponent = autoSyncControllerComponent;
    }

    public HybridPlayerView getHybridPlayerView() {
        return hybridPlayerView;
    }

    public void setHybridPlayerView(HybridPlayerView hybridPlayerView) {
        if (this.hybridPlayerView != null && this.hybridPlayerView.getIHybridPlayerEventAdapter() != null) {
            removeHybridEventListener(this.hybridPlayerView.getIHybridPlayerEventAdapter());
        }
        if (hybridPlayerView != null && hybridPlayerView.getIHybridPlayerEventAdapter() != null) {
            addHybridEventListener(hybridPlayerView.getIHybridPlayerEventAdapter());
        }
        if (hybridPlayerView != null) {
            this.hybridPlayerView = hybridPlayerView;
            setUpSurfaceView();
        }
    }

    @Override
    public void setDataSource(Uri path, OverrideExtensionAdapter overrideExtensionAdapter) {
        super.setDataSource(path, overrideExtensionAdapter);
        this.currentUri = path;
        this.currentOverrideExtensionAdapter = overrideExtensionAdapter;

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

    @Override
    public void release() {
        if (autoSyncControllerComponent != null) {
            autoSyncControllerComponent.stopLoop();
        }
    }

    @Override
    public void initialize() {
        if (autoSyncControllerComponent != null) {
            autoSyncControllerComponent.startLoop(this);
        }
        if (currentUri != null) {
            setDataSource(currentUri, currentOverrideExtensionAdapter);
        }
    }

    protected abstract void setSurface(SurfaceTexture surface);
}

