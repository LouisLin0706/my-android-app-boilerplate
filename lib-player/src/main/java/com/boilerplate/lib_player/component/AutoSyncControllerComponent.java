package com.boilerplate.lib_player.component;

import android.view.View;
import android.widget.SeekBar;

import com.boilerplate.lib_player.core.HybridExtensionPlayBack;
import com.boilerplate.lib_player.util.PlayerUtils;

import java.util.ArrayList;
import java.util.List;
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
    private HybridExtensionPlayBack hybridExtensionPlayBack;
    private List<OnAutoControllerAdapter> onAutoControllerAdapters = new ArrayList<>();


    public abstract class OnAutoControllerAdapter implements OnAutoControllerListener {

    }

    public interface OnAutoControllerListener extends SeekBar.OnSeekBarChangeListener {
        void onProgressEffectRunning(long position, long duration);

        void onControllerViewClick();
    }


    public AutoSyncControllerComponent() {
    }


    public void addAutoControllerListener(OnAutoControllerAdapter onAutoControllerListener) {
        onAutoControllerAdapters.add(onAutoControllerListener);
    }

    public void removeAutoControllerListener(OnAutoControllerAdapter onAutoControllerListener) {
        onAutoControllerAdapters.remove(onAutoControllerListener);
    }


    private View.OnClickListener playPauseEventListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (hybridExtensionPlayBack.isPlaying()) {
                hybridExtensionPlayBack.pause();
            } else {
                hybridExtensionPlayBack.play();
            }
        }
    };

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            long duration = hybridExtensionPlayBack.getDuration();
            long newPosition = (duration * progress) / 1000L;
            if (hybridExtensionPlayBack.getHybridPlayerView() != null && hybridExtensionPlayBack.getHybridPlayerView().getCTextView() != null) {
                hybridExtensionPlayBack.getHybridPlayerView().getCTextView().setText(PlayerUtils.foramtTime2String((int) newPosition));
            }
            for (OnAutoControllerAdapter onAutoControllerAdapter : onAutoControllerAdapters) {
                onAutoControllerAdapter.onProgressChanged(seekBar, progress, fromUser);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            stopLoop();
            for (OnAutoControllerAdapter onAutoControllerAdapter : onAutoControllerAdapters) {
                onAutoControllerAdapter.onStartTrackingTouch(seekBar);
            }
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            startLoop(hybridExtensionPlayBack);
            long duration = hybridExtensionPlayBack.getDuration();
            long newPosition = (duration * seekBar.getProgress()) / 1000L;
            hybridExtensionPlayBack.seekTo((int) newPosition);
            for (OnAutoControllerAdapter onAutoControllerAdapter : onAutoControllerAdapters) {
                onAutoControllerAdapter.onStopTrackingTouch(seekBar);
                onAutoControllerAdapter.onControllerViewClick();
            }
        }
    };

    private void syncListener() {
        if (hybridExtensionPlayBack.getHybridPlayerView().getPlayPauseView() != null) {
            hybridExtensionPlayBack.getHybridPlayerView().getPlayPauseView().setOnClickListener(playPauseEventListener);
        }
        if (hybridExtensionPlayBack.getHybridPlayerView().getSeekBar() != null) {
            hybridExtensionPlayBack.getHybridPlayerView().getSeekBar().setOnSeekBarChangeListener(onSeekBarChangeListener);
        }
    }



    public void startLoop(final HybridExtensionPlayBack hybridPlayBack) {
        this.hybridExtensionPlayBack = hybridPlayBack;
        stopLoop();
        syncListener();
        disposableLoop = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        long position = hybridPlayBack.getCurrentPosition();
                        long duration = hybridPlayBack.getDuration();
                        if (duration == 0) {
                            if (hybridPlayBack.getHybridPlayerView().getCTextView() != null) {
                                hybridPlayBack.getHybridPlayerView().getCTextView().setText("00:00");
                            }
                            if (hybridPlayBack.getHybridPlayerView().getETextView() != null) {
                                hybridPlayBack.getHybridPlayerView().getETextView().setText("00:00");
                            }
                        } else {
                            for (OnAutoControllerAdapter onAutoControllerAdapter : onAutoControllerAdapters) {
                                onAutoControllerAdapter.onProgressEffectRunning(position, duration);
                            }
                            long seekBarPosition = position * 1000L / duration;
                            if (hybridPlayBack.getHybridPlayerView().getSeekBar() != null) {
                                hybridPlayBack.getHybridPlayerView().getSeekBar().setProgress((int) seekBarPosition);
                            }

                            if (hybridPlayBack.getHybridPlayerView().getETextView() != null) {
                                hybridPlayBack.getHybridPlayerView().getETextView().setText(PlayerUtils.foramtTime2String(duration));
                            }
                            if (hybridPlayBack.getHybridPlayerView().getCTextView() != null) {
                                hybridPlayBack.getHybridPlayerView().getCTextView().setText(PlayerUtils.foramtTime2String(position));
                            }
                            int percent = hybridPlayBack.getBufferedPercentage();
                            int progress = 0;
                            if (hybridPlayBack.getHybridPlayerView().getSeekBar() != null) {
                                progress = hybridPlayBack.getHybridPlayerView().getSeekBar().getProgress();
                            }
                            int bufferPercent = progress + percent;
                            if (hybridPlayBack.getHybridPlayerView().getSeekBar() != null) {
                                hybridPlayBack.getHybridPlayerView().getSeekBar().setSecondaryProgress(bufferPercent);
                            }

                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }

    //TODO maybe include lifecycle handle
    public void stopLoop() {
        if (disposableLoop != null && disposableLoop.isDisposed() == false) {
            disposableLoop.dispose();
        }
    }
}
