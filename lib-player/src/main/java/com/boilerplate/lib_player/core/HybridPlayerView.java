package com.boilerplate.lib_player.core;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.video.VideoListener;

import java.util.List;

/**
 * Created by Louis on 2018/4/15.
 */

public abstract class HybridPlayerView extends FrameLayout implements VideoListener, TextOutput, MediaPlayer.OnVideoSizeChangedListener {

    private AspectRatioFrameLayout aspectRatioFrameLayout;
    public IControllerView iControllerView;
    private TextureView textureView;

    public HybridPlayerView(@NonNull Context context) {
        super(context);
        FrameLayout.LayoutParams paramsAspect = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        paramsAspect.gravity = Gravity.CENTER;
        aspectRatioFrameLayout = new AspectRatioFrameLayout(getContext());
        addView(aspectRatioFrameLayout, paramsAspect);

        //the view will render video source
        aspectRatioFrameLayout.addView(getTextureView());

        iControllerView = getIControllerView();
        addView(iControllerView.getMainView());
    }

    @Override
    public void onCues(List<Cue> cues) {
        //subtitle can implement there
    }

    @Override
    public void onVideoSizeChanged(int width, int height, int unappliedRotationDegrees, float pixelWidthHeightRatio) {
        float aspectRatio = height == 0 ? 1 : (width * pixelWidthHeightRatio) / height;
        aspectRatioFrameLayout.setAspectRatio(aspectRatio);
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        float aspectRatio = height == 0 ? 1 : width / height;
        aspectRatioFrameLayout.setAspectRatio(aspectRatio);
    }

    @Override
    public void onRenderedFirstFrame() {

    }

    protected TextureView getTextureView() {
        if (textureView == null) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            textureView = new TextureView(getContext());
            textureView.setLayoutParams(params);
        }
        return textureView;
    }

    @Nullable
    protected IControllerView getIControllerView() {
        return new DefaultIControllerView(getContext());
    }

    protected void showController() {
        iControllerView.getMainView().setVisibility(VISIBLE);
    }


    protected void hideController() {
        iControllerView.getMainView().setVisibility(GONE);
    }

}
