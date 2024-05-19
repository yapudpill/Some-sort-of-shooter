package model.ingame.physics;

/**
 * Listener for blocked movement events.
 */
@FunctionalInterface
public interface BlockedMovementListener {
    void onMovementBlocked(BlockedMovementEvent e);
}
