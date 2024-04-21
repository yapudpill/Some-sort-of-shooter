package model.ingame.physics;

import model.ingame.entity.IMobileEntity;
import model.level.TileModel;
import util.Coordinates;

public class BlockedMovementEvent {
    private final IMobileEntity blockedEntity;
    private final boolean outOfBounds;
    private final TileModel blockingTile;
    private Coordinates adjustedMovement = Coordinates.ZERO;
    private Coordinates movementVector;
    private boolean verticalBlocked;
    private boolean horizontalBlocked;

    BlockedMovementEvent(IMobileEntity entity, TileModel blockingTile, boolean outOfBounds) {
        this.blockedEntity = entity;
        this.outOfBounds = outOfBounds;
        this.blockingTile = blockingTile;
    }

    IMobileEntity blockedEntity() {
        return blockedEntity;
    }

    boolean outOfBounds() {
        return outOfBounds;
    }

    TileModel blockingTile() {
        return blockingTile;
    }

    void setAdjustedMovement(Coordinates adjustedMovement) {
        this.adjustedMovement = adjustedMovement;
    }

    Coordinates getAdjustedMovement() {
        return adjustedMovement;
    }

    boolean isVerticalBlocked() {
        return verticalBlocked;
    }

    void setVerticalBlocked(boolean verticalBlocked) {
        this.verticalBlocked = verticalBlocked;
    }

    boolean isHorizontalBlocked() {
        return horizontalBlocked;
    }

    void setHorizontalBlocked(boolean horizontalBlocked) {
        this.horizontalBlocked = horizontalBlocked;
    }

    Coordinates getMovementVector() {
        return movementVector;
    }

    void setMovementVector(Coordinates movementVector) {
        this.movementVector = movementVector;
    }
}
