package gui.ingame;

import model.ingame.IGameLoop;
import model.ingame.IUpdateable;

import javax.swing.*;

/*
 * This class manages the game loop, implementing a straightforward loop that invokes an updater method every FRAME_TIME
 * milliseconds using Swing's Timer class.
 * For example, to achieve a frame rate of 60 frames per second, set FRAME_TIME to 16.
 */
public class SwingGameLoop implements IGameLoop {
    final static int FRAME_TIME = 16; // ms
    private IUpdateable updater; // Method to be called at each tick
    private final Timer updateTimer = new Timer(FRAME_TIME, e -> updater.update());

    public SwingGameLoop(IUpdateable updater) {
        setUpdater(updater);
    }

    @Override
    public void setUpdater(IUpdateable updater) {
        this.updater = updater;
    }

    @Override
    public void start() {
        updateTimer.start();
    }

    @Override
    public void stop() {
        updateTimer.stop();
    }

    @Override
    public boolean isRunning() {
        return updateTimer.isRunning();
    }
}
