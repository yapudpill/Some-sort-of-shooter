package util;

import model.ingame.GameModel;
import model.ingame.IUpdateable;



public class ModelTimer implements IUpdateable {
    private GameModel gameModel;
    private final Runnable runnable;
    private int timer;
    private final int timerDuration;
    private boolean repeats = true;


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
            if(repeats) timer = timerDuration;
            else gameModel.detachAsUpdateable(this);
        }
    }

    public void start() {
        timer = timerDuration;
        gameModel.attachAsUpdateable(this);
    }

    public void stop() {
        gameModel.detachAsUpdateable(this);
    }

    public void setRepeats(boolean repeats) {
        this.repeats = repeats;
    }


}
