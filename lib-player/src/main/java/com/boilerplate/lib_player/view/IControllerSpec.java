package com.boilerplate.lib_player.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Louis on 2018/5/29.
 */

public abstract class IControllerSpec extends FrameLayout {

    public IControllerSpec(@NonNull Context context) {
        this(context, null);
    }

    public IControllerSpec(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IControllerSpec(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public abstract SeekBar getSeekBar();

    public abstract TextView getCTextView();

    public abstract TextView getETextView();

    public abstract View getPlayPauseView();

    public abstract View getMainView();

    public abstract void init();
}
