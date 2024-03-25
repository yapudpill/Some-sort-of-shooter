package model.ingame.physics;

import model.ingame.Coordinates;
import model.ingame.entity.IMobileEntity;
import model.level.TileModel;

public class BlockedMovementEvent {
    private final IMobileEntity blockedEntity;
    private final boolean outOfBounds;
    private final TileModel blockingTile;
    private Coordinates adjustedMovement = Coordinates.ZERO;
    private boolean verticalBlocked;
    private boolean horizontalBlocked;
    private Coordinates movementVector;

    public BlockedMovementEvent(IMobileEntity entity, TileModel blockingTile, boolean outOfBounds){
        this.blockedEntity = entity;
        this.outOfBounds = outOfBounds;
        this.blockingTile = blockingTile;
    }

    public IMobileEntity blockedEntity(){
        return blockedEntity;
    }

    public boolean outOfBounds(){
        return outOfBounds;
    }

    public TileModel blockingTile() {
        return blockingTile;
    }

    public void setAdjustedMovement(Coordinates adjustedMovement){
        this.adjustedMovement = adjustedMovement;
    }

    public Coordinates getAdjustedMovement(){
        return adjustedMovement;
    }

    public boolean isVerticalBlocked() {
        return verticalBlocked;
    }

    public void setVerticalBlocked(boolean verticalBlocked) {
        this.verticalBlocked = verticalBlocked;
    }

    public boolean isHorizontalBlocked() {
        return horizontalBlocked;
    }

    public void setHorizontalBlocked(boolean horizontalBlocked) {
        this.horizontalBlocked = horizontalBlocked;
    }

    public Coordinates getMovementVector(){
        return movementVector;
    }

    public void setMovementVector(Coordinates movementVector){
        this.movementVector = movementVector;
    }
}
