package com.boilerplate.lib_player.extension.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Louis on 2018/6/22.
 */

public class Container extends RecyclerView {


    public Container(Context context) {
        this(context, null);
    }

    public Container(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Container(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        playerManager = new PlayerManager();
//        childLayoutChangeListener = new ChildLayoutChangeListener(this);
        requestDisallowInterceptTouchEvent(true);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
