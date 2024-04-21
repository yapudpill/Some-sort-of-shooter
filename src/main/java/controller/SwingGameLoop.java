package controller;

import javax.swing.Timer;

import util.IUpdateable;

/*
 * This class manages the game loop, implementing a straightforward loop that
 * invokes an updater method every FRAME_TIME milliseconds using Swing's Timer
 * class.
 * <p>
 * For example, to achieve a frame rate of 60 frames per second, set FRAME_TIME to 16.
 */
class SwingGameLoop extends GameLoop {
    private static final int FRAME_TIME = 16; // ms

    private final Timer updateTimer;

    SwingGameLoop(IUpdateable updater) {
        super(updater);
        updateTimer = new Timer(FRAME_TIME, e -> updater.update(FRAME_TIME / 1000.));
    }

    @Override
    void start() {
        updateTimer.start();
    }

    @Override
    void stop() {
        updateTimer.stop();
    }
}
