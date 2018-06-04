package com.boilerplate.lib_player.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.TextOutput;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.video.VideoListener;

import java.util.List;

/**
 * Created by Louis on 2018/4/15.
 */

public class HybridPlayerView extends IControllerSpec implements VideoListener, TextOutput, MediaPlayer.OnVideoSizeChangedListener {

    private AspectRatioFrameLayout aspectRatioFrameLayout;
    protected IControllerSpec playerControllerView;
    private TextureView textureView;


    public HybridPlayerView(@NonNull Context context) {
        this(context, null);
    }

    public HybridPlayerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HybridPlayerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public SeekBar getSeekBar() {
        return playerControllerView.getSeekBar();
    }

    @Override
    public TextView getCTextView() {
        return playerControllerView.getCTextView();
    }

    @Override
    public TextView getETextView() {
        return playerControllerView.getETextView();
    }

    @Override
    public View getPlayPauseView() {
        return playerControllerView.getPlayPauseView();
    }

    @Override
    public View getMainView() {
        return playerControllerView.getMainView();
    }

    @Override
    public void init() {
        FrameLayout.LayoutParams paramsAspect = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        paramsAspect.gravity = Gravity.CENTER;
        aspectRatioFrameLayout = new AspectRatioFrameLayout(getContext());
        addView(aspectRatioFrameLayout, paramsAspect);

        //the view will render video source
        aspectRatioFrameLayout.addView(getTextureView());
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // Ensure the movable child list.
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; ++i) {
            final View child = getChildAt(i);
            final LayoutParams params = (LayoutParams) child.getLayoutParams();
            if (params == null) continue;
            if (child instanceof IControllerSpec) {
                playerControllerView = (IControllerSpec) child;
            }
        }
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

    public TextureView getTextureView() {
        if (textureView == null) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            textureView = new TextureView(getContext());
            textureView.setLayoutParams(params);
        }
        return textureView;
    }

    public void showController() {
        playerControllerView.setVisibility(VISIBLE);
    }


    public void hideController() {
        playerControllerView.setVisibility(GONE);
    }

}
