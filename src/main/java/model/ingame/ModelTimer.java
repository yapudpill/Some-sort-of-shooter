package model.ingame;

import util.IUpdateable;

/**
 * A timer that can be used in the game model, updated using the game model's update loop. Needs a reference to the
 * {@link GameModel} as it is an {@link IUpdateable} and needs to be attached to the game model's update loop.
 */
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
            if (repeats) {
                timer = timerDuration;
            } else {
                stop();
            }
            runnable.run();
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
