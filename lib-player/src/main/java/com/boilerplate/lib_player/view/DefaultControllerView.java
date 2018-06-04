package com.boilerplate.lib_player.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.boilerplate.lib_player.R;

/**
 * Created by Louis on 2018/5/29.
 */

public class DefaultControllerView extends IControllerSpec {

    public View mRoot;
    public SeekBar mProgress;
    public TextView mEndTime, mCurrentTime;
    public ImageButton mPauseButton;
    public ImageButton mFfwdButton;
    public ImageButton mRewButton;
    public TextView textViewTitle;

    public DefaultControllerView(@NonNull Context context) {
        this(context, null);
    }

    public DefaultControllerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultControllerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

    @Override
    public View getMainView() {
        return this;
    }

    @Override
    public void init() {
        addView(makeControllerView());
    }


    protected View makeControllerView() {
        LayoutInflater inflate = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRoot = inflate.inflate(R.layout.default_controller, null);
        initControllerView(mRoot);
        return mRoot;
    }

    private void initControllerView(View v) {
        mPauseButton = v.findViewById(R.id.pause_2);
        mFfwdButton = v.findViewById(R.id.ffwd_2);
        mRewButton = v.findViewById(R.id.rew_2);
        mProgress = v.findViewById(R.id.mediacontroller_progress_2);
        textViewTitle = v.findViewById(R.id.textView_two_title);
        mEndTime = v.findViewById(R.id.time_2);
        mCurrentTime = v.findViewById(R.id.time_current_2);
    }

}
