package controller;

import java.util.Timer;
import java.util.TimerTask;

import model.ingame.IUpdateable;

public class ModelGameLoop implements IGameLoop {
    final static int FRAME_TIME = 16; // ms
    private IUpdateable updater; // Method to be called at each tick
    private final Timer updateTimer = new Timer();

    public ModelGameLoop(IUpdateable updater) {
        setUpdater(updater);
    }

    @Override
    public void setUpdater(IUpdateable updater) {
        this.updater = updater;
    }

    @Override
    public void start() {
        updateTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updater.update();
            }
        }, 0, FRAME_TIME);
    }

    @Override
    public void stop() {
        updateTimer.cancel();
    }
}
