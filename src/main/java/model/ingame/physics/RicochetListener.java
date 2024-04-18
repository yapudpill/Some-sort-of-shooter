package model.ingame.physics;

import util.Coordinates;

public class RicochetListener implements BlockedMovementListener {

    @Override
    public void onMovementBlocked(BlockedMovementEvent e) {
        Coordinates adjustedMovement = Coordinates.ZERO;
        Coordinates movementVector = e.getMovementVector();
        if(e.isHorizontalBlocked()){
            adjustedMovement = adjustedMovement.add(movementVector.xProjection());
        }
        else {
            adjustedMovement = adjustedMovement.add(movementVector.xProjection().multiply(-1));
        }
        if(e.isVerticalBlocked()){
            adjustedMovement = adjustedMovement.add(movementVector.yProjection());
        }
        else {
            adjustedMovement = adjustedMovement.add(movementVector.yProjection().multiply(-1));
        }
        e.setAdjustedMovement(adjustedMovement);
        e.blockedEntity().getMovementHandler().setDirectionVector(adjustedMovement);
    }


}
