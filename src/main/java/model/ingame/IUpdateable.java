package model.ingame;

/**
 * Represents a function meant to be called at every tick of the game loop.
 * An updater can itself call other update methods, and so on, to update the whole game state.
 */
public interface IUpdateable {
    void update();
}
