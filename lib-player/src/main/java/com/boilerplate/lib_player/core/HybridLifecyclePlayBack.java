package com.boilerplate.lib_player.core;

import com.google.android.exoplayer2.util.Util;

/**
 * Created by Louis on 2018/5/24.
 */

public abstract class HybridLifecyclePlayBack extends HybridRulePlayBack {

    public HybridLifecyclePlayBack(HybridPlayerView hybridPlayerView) {
        super(hybridPlayerView);
    }

    @Override
    public void lifecycleIsPause() {
        if (Util.SDK_INT <= 23) {
            release();
        }
    }

    @Override
    public void lifecycleIsResume() {
        if (Util.SDK_INT <= 23) {
            initialize();
        }
    }

    @Override
    public void lifecycleIsStart() {
        if (Util.SDK_INT > 23) {
            initialize();
        }
    }

    @Override
    public void lifecycleIsStop() {
        if (Util.SDK_INT > 23) {
            release();
        }
    }
}
