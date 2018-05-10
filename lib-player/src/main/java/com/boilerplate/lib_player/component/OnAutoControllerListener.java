package com.boilerplate.lib_player.component;

import android.widget.SeekBar;

/**
 * Created by Louis on 2017/4/17.
 */

public interface OnAutoControllerListener extends SeekBar.OnSeekBarChangeListener {
    void onProgressEffectRunning(long position, long duration);

    void onControllerViewClick();
}

