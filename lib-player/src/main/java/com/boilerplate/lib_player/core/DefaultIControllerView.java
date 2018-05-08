package com.boilerplate.lib_player.core;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.boilerplate.lib_player.R;

/**
 * Created by Louis on 2017/6/1.
 */

public class DefaultIControllerView extends FrameLayout implements IControllerView {

    public View mRoot;
    public SeekBar mProgress;
    public TextView mEndTime, mCurrentTime;
    public ImageButton mPauseButton;
    public ImageButton mFfwdButton;
    public ImageButton mRewButton;
    public TextView textViewTitle;

    public DefaultIControllerView(@NonNull Context context) {
        super(context);
        addView(makeControllerView());
    }

    @Override
    public SeekBar getSeekBar() {
        return mProgress;
    }

    @Override
    public TextView getCTextView() {
        return mCurrentTime;
    }

    @Override
    public TextView getETextView() {
        return mEndTime;
    }

    @Override
    public View getPlayPauseView() {
        return mPauseButton;
    }


    @NonNull
    @Override
    public View getMainView() {
        return this;
    }


    protected View makeControllerView() {
        LayoutInflater inflate = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRoot = inflate.inflate(R.layout.default_controller, null);
        initControllerView(mRoot);
        return mRoot;
    }

    private void initControllerView(View v) {
        mPauseButton = (ImageButton) v.findViewById(R.id.pause_2);
        mFfwdButton = (ImageButton) v.findViewById(R.id.ffwd_2);
        mRewButton = (ImageButton) v.findViewById(R.id.rew_2);
        mProgress = (SeekBar) v.findViewById(R.id.mediacontroller_progress_2);
        textViewTitle = (TextView) v.findViewById(R.id.textView_two_title);
        mEndTime = (TextView) v.findViewById(R.id.time_2);
        mCurrentTime = (TextView) v.findViewById(R.id.time_current_2);
    }


}
