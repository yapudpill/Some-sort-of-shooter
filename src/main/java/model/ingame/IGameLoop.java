package model.ingame;

/**
 * Represents a generic game loop. The updater method (set with setUpdater) should be called at each tick.
 * It's the updater's responsibility to call other updaters if needed.
 * <p>
 * The assigned updater is intended to serve as the primary updater responsible for invoking all other update methods on
 * the model but also on other needed components (e.g. the view)
 */
public interface IGameLoop {
    /**
     * Sets the updater method (meant to be called at each tick).
     * Only one updater can be set at a time, thus it's the updater's responsibility to call other updaters if needed.
     * @param updater the updater method
     */
    void setUpdater(IUpdateable updater);
    /**
     * Starts the game loop
     */
    void start();

    /**
     * Stops the game loop
     */
    void stop();

    /**
     * @return true if the game loop is running, false otherwise
     */
    boolean isRunning();
}
