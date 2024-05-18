package model.ingame;

import util.IUpdateable;

public class ModelTimer implements IUpdateable {
    private final double timerDuration;
    private final boolean repeats;
    private final Runnable runnable;
    private final GameModel gameModel;
    private double timer;
    private boolean isRunning = false;

    public ModelTimer(double timerDuration, boolean repeats, Runnable runnable, GameModel gameModel) {
        this.timerDuration = timerDuration;
        this.repeats = repeats;
        this.runnable = runnable;
        this.gameModel = gameModel;
    }

    @Override
    public void update(double delta) {
        timer -= delta;
        if (timer <= 0) {
            runnable.run();
            if (repeats) {
                timer = timerDuration;
            } else {
                stop();
            }
        }
    }

    public void start() {
        timer = timerDuration;
        gameModel.attachAsUpdateable(this);
        isRunning = true;
    }

    public void stop() {
        timer = timerDuration;
        gameModel.detachAsUpdateable(this);
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public double getTimeLeft() {
        return timer;
    }

    public double getTimerDuration() {
        return timerDuration;
    }
}
