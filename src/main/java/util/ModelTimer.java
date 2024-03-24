package util;

import model.ingame.GameModel;
import model.ingame.IUpdateable;


public class ModelTimer implements IUpdateable {
    private final Runnable runnable;
    private final int timerDuration;
    private GameModel gameModel;
    private int timer;
    private boolean repeats = true;
    private boolean isRunning = false;


    public ModelTimer(int timerDuration, Runnable runnable, GameModel gameModel) {
        this.timerDuration = timerDuration;
        this.runnable = runnable;
        this.gameModel = gameModel;
    }

    @Override
    public void update() {
        timer--;
        if (timer <= 0) {
            runnable.run();
            if (repeats) timer = timerDuration;
            else stop();
        }
    }

    public void start() {
        timer = timerDuration;
        isRunning = true;
        gameModel.attachAsUpdateable(this);
    }

    public void stop() {
        timer = timerDuration;
        gameModel.detachAsUpdateable(this);
        isRunning = false;
    }

    public void setRepeats(boolean repeats) {
        this.repeats = repeats;
    }

    public boolean isRunning() {
        return isRunning;
    }

}
