package util;

import model.ingame.IUpdateable;

public class ModelTimer implements IUpdateable {
    private final Runnable runnable;
    private int timer;
    private final int timerDuration;

    public ModelTimer(int timerDuration, Runnable runnable) {
        this.timerDuration = timerDuration;
        this.runnable = runnable;
    }

    @Override
    public void update() {
        timer--;
        if (timer <= 0) {
            runnable.run();
            timer = timerDuration;
        }
    }
}
