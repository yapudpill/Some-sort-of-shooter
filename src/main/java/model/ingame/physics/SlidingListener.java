package model.ingame.physics;

import util.Coordinates;

/**
 * A listener for BlockedMovementEvents that adjusts the movement vector to slide along the blocking tile.
 */
public class SlidingListener implements BlockedMovementListener {

    @Override
    public void onMovementBlocked(BlockedMovementEvent e) {
        Coordinates adjustedMovement = Coordinates.ZERO;
        Coordinates movementVector = e.getMovementVector();
        if (e.isHorizontalBlocked()) {
            adjustedMovement = adjustedMovement.add(movementVector.xProjection());
        }
        if (e.isVerticalBlocked()) {
            adjustedMovement = adjustedMovement.add(movementVector.yProjection());
        }
        e.setAdjustedMovement(adjustedMovement);
    }
}
