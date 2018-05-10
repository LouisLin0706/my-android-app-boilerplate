package com.boilerplate.lib_player.component;

import com.boilerplate.lib_player.core.HybridRulePlayBack;
import com.boilerplate.lib_player.core.IControllerView;

import java.util.Formatter;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Louis on 2018/4/20.
 * If your view implement IControllerView this interface
 * Then use this component will auto sync seekbar,time refresh every sec
 * Also If you want know position or duration every sec , You can set up Listener
 */
public class AutoSyncControllerComponent {

    private Disposable disposableLoop;
    private HybridRulePlayBack hybridPlayBack;
    private IControllerView iControllerView;
    private OnAutoControllerListener onAutoControllerListener;

    public AutoSyncControllerComponent(HybridRulePlayBack hybridPlayBack) {
        this(null, hybridPlayBack);
    }

    public AutoSyncControllerComponent(OnAutoControllerListener onAutoControllerListene, HybridRulePlayBack hybridPlayBack) {
        this.onAutoControllerListener = onAutoControllerListener;
        this.iControllerView = hybridPlayBack.getIControllerView();
        this.hybridPlayBack = hybridPlayBack;
        startLoop();
    }


    public void setOnAutoControllerListener(OnAutoControllerListener onAutoControllerListener) {
        this.onAutoControllerListener = onAutoControllerListener;
    }

    private void startLoop() {
        stopLoop();
        disposableLoop = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        long position = hybridPlayBack.getCurrentPosition();
                        long duration = hybridPlayBack.getDuration();
                        if (duration == 0) {
                            if (iControllerView.getCTextView() != null) {
                                iControllerView.getCTextView().setText("00:00");
                            }
                            if (iControllerView.getETextView() != null) {
                                iControllerView.getETextView().setText("00:00");
                            }
                        } else {
                            if (onAutoControllerListener != null) {
                                onAutoControllerListener.onProgressEffectRunning(position, duration);
                            }
                            long seekBarPosition = position * 1000L / duration;
                            if (iControllerView.getSeekBar() != null) {
                                iControllerView.getSeekBar().setProgress((int) seekBarPosition);
                            }

                            if (iControllerView.getETextView() != null) {
                                iControllerView.getETextView().setText(foramtTime2String(duration));
                            }
                            if (iControllerView.getCTextView() != null) {
                                iControllerView.getCTextView().setText(foramtTime2String(position));
                            }
                            int percent = hybridPlayBack.getBufferedPercentage();
                            int progress = 0;
                            if (iControllerView.getSeekBar() != null) {
                                progress = iControllerView.getSeekBar().getProgress();
                            }
                            int bufferPercent = progress + percent;
                            if (iControllerView.getSeekBar() != null) {
                                iControllerView.getSeekBar().setSecondaryProgress(bufferPercent);
                            }

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    private String foramtTime2String(long timeMs) {
        StringBuilder mFormatBuilder = new StringBuilder();
        Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

        long totalSeconds = timeMs / 1000;

        long seconds = totalSeconds % 60;
        long minutes = (totalSeconds / 60) % 60;
        long hours = totalSeconds / 3600;

        mFormatBuilder.setLength(0);
        if (hours > 0) {
            return mFormatter.format("%d:%02d:%02d", hours, minutes, seconds).toString();
        } else {
            return mFormatter.format("%02d:%02d", minutes, seconds).toString();
        }
    }

    //TODO maybe include lifecycle handle
    private void stopLoop() {
        if (disposableLoop != null && disposableLoop.isDisposed() == false) {
            disposableLoop.dispose();
        }
    }
}
