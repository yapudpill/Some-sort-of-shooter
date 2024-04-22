package controller;

import util.IUpdateable;

import java.util.Timer;
import java.util.TimerTask;

public class ModelGameLoop extends GameLoop {
    public static final int FRAME_TIME = 16; // ms

    private final Timer updateTimer;

    public ModelGameLoop(IUpdateable updater) {
        super(updater);
        updateTimer = new Timer();
    }

    @Override
    public void start() {
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updater.update(FRAME_TIME / 1000.);
            }
        }, 0, FRAME_TIME);
    }

    @Override
    public void stop() {
        updateTimer.cancel();
    }
}
