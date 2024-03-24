package model.ingame.physics;

@FunctionalInterface
public interface BlockedMovementListener {
    void onMovementBlocked(BlockedMovementEvent e);
}
