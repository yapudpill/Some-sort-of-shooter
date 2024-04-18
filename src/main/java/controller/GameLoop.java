package controller;

import util.IUpdateable;

/**
 * Represents a generic game loop. The updater method is called at each update.
 * It's the updater's responsibility to call other updaters if needed.
 * <p>
 * The assigned updater is intended to serve as the primary updater responsible
 * for invoking all other update methods on the model but also on other needed
 * components (e.g. the view).
 */
public abstract class GameLoop {
    protected final IUpdateable updater;

    public GameLoop(IUpdateable updater) {
        this.updater = updater;
    }

    /**
     * Starts the game loop
     */
    abstract void start();

    /**
     * Stops the game loop
     */
    abstract void stop();
}
