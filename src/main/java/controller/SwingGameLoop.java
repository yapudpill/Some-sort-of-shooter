package controller;

import util.IUpdateable;

import javax.swing.*;

/**
 * This class manages the game loop, implementing a straightforward loop that
 * invokes an updater method every FRAME_TIME milliseconds using Swing's Timer
 * class.
 */
public class SwingGameLoop extends GameLoop {
    private static final int FRAME_TIME = 16; // ms

    private final Timer updateTimer;

    public SwingGameLoop(IUpdateable updater) {
        super(updater);
        updateTimer = new Timer(FRAME_TIME, e -> updater.update(FRAME_TIME / 1000.));
    }

    @Override
    public void start() {
        updateTimer.start();
    }

    @Override
    public void stop() {
        updateTimer.stop();
    }
}
