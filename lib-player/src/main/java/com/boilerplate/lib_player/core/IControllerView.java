package com.boilerplate.lib_player.core;

import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * Created by Louis on 2017/4/17.
 */

public interface IControllerView {

    public SeekBar getSeekBar();

    public TextView getCTextView();

    public TextView getETextView();

    public View getPlayPauseView();

    public View getMainView();


}
