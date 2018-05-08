package com.boilerplate.lib_player.core;

/**
 * Created by Louis on 2017/4/17.
 */

public interface IHybridPlayerEventListener {

    void onPrepared(HybridPlayBack hybridPlayBack);

    void onStatePlay();

    void onStatePause();

    void onCompletion(HybridPlayBack hybridPlayBack);

    void onError(HybridPlayBack hybridPlayBack);

    void onBufferStart();

    void onBufferEnd();

    void onLoadIngStart();

    void onLoadIngEnd();

}
