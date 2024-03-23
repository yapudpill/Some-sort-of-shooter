package model.ingame.physics;

import model.ingame.entity.EntityModel;
import model.ingame.entity.IMobileEntity;
import model.level.TileModel;

public record BlockedMovementEvent(IMobileEntity blockedEntity, TileModel blockingTile, boolean outOfBounds) {
}
