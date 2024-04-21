package controller;

import java.util.Timer;
import java.util.TimerTask;

import util.IUpdateable;

class ModelGameLoop extends GameLoop {
    private static final int FRAME_TIME = 16; // ms

    private final Timer updateTimer;

    ModelGameLoop(IUpdateable updater) {
        super(updater);
        updateTimer = new Timer();
    }

    @Override
    void start() {
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updater.update(FRAME_TIME / 1000.);
            }
        }, 0, FRAME_TIME);
    }

    @Override
    void stop() {
        updateTimer.cancel();
    }
}
