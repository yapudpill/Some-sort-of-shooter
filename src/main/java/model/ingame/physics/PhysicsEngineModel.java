package model.ingame.physics;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import model.ingame.Coordinates;
import model.ingame.IUpdateable;
import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IMobileEntity;
import model.ingame.entity.IVulnerableEntity;
import model.level.MapModel;
import model.level.TileModel;

/**
 * The <code>PhysicsEngineModel</code> class is used to handle the physics of the game, such as collision detection and entity movement.
 */
public class PhysicsEngineModel implements IUpdateable{
    private final Set<ICollisionEntity> collisionEntities;
    private final MapModel map;

    public PhysicsEngineModel(MapModel map, Set<ICollisionEntity> collisionEntities) {
        if (map == null)
            throw new IllegalArgumentException("Map cannot be null");
        this.map = map;
        this.collisionEntities = collisionEntities;
    }

    public void update() {
        for(ICollisionEntity entity : collisionEntities) {
            checkForCollisions(entity);
        }
    }
    /**
     * Returns a list of all entities that are colliding with the given entity.
     *
     * @param entity the entity to check for collisions
     * @return a list of all entities that are colliding with the given entity
     */

    public Set<ICollisionEntity> getCollidedEntities(ICollisionEntity entity) {
        Set<ICollisionEntity> involvedEntities = new CopyOnWriteArraySet<>();
        // Get all the entities that could be colliding with the given entity, i.e the entities in the 3x3 grid around the given entity.
        Set<ICollisionEntity> potentiallyCollided = map.getAllCollidablesAround((int) entity.getPos().x, (int) entity.getPos().y);
        for (ICollisionEntity other : potentiallyCollided) {
            // check if the entities are actually colliding, make sure not to check the entity with itself
            if (entity.getCollisionBox().intersects(other.getCollisionBox()) && !entity.equals(other)) {
                involvedEntities.add(other);
            }
        }
        return involvedEntities;
    }


    /**
     * Moves the given entity to the given position, and checks for collisions.
     *
     * @param entity         the entity to move
     * @param movementVector the new position of the entity
     */
    public void move(IMobileEntity entity, Coordinates movementVector) {
        if(entity == null || entity instanceof IVulnerableEntity vuln && vuln.isDead()) return;
        // Adjust movement to collide with walls
        var blockedMovementEvent = handleBlockedMovement(entity, movementVector);
        Coordinates adjustedMovement = movementVector;
        if (blockedMovementEvent != null) {
            entity.notifyBlockedMovementListeners(blockedMovementEvent);
            adjustedMovement = blockedMovementEvent.getAdjustedMovement();
        }
        if (adjustedMovement.isZero()) {
            entity.getMovementHandler().setMoving(false);
            return;

        }

        // move the entity and its collision box
        entity.getMovementHandler().setMoving(true);
        entity.setPos(entity.getPos().add(adjustedMovement));
    }

    public void checkForCollisions(ICollisionEntity entity) {
        // check if the entity has collision listeners, and if it does, check if it is colliding with anything
        Set<ICollisionEntity> collidedEntities = getCollidedEntities(entity);
        if (!collidedEntities.isEmpty()) {
            // create a collision event
            CollisionEvent event = new CollisionEvent(entity, collidedEntities);
            // notify the entity's collision listeners
            entity.notifyCollisionListeners(event);
        }
    }


    private BlockedMovementEvent handleBlockedMovement(IMobileEntity entity, Coordinates movementVector) {
        // If horizontal movement is blocked, only keep the vertical movement
        Coordinates newPosX = entity.getPos().add(movementVector.xProjection());
        BlockedMovementEvent potentialBlockedMovementX = canMoveTo(entity, newPosX);
        boolean vertical = false;
        boolean horizontal = false;
        if (potentialBlockedMovementX == null) { // If the movement is not blocked, keep it
            horizontal = true;
        }
        // Same for vertical movement
        Coordinates newPosY = entity.getPos().add(movementVector.yProjection());
        BlockedMovementEvent potentialBlockedMovementY = canMoveTo(entity, newPosY);
        if (potentialBlockedMovementY == null) {
            vertical = true;
        }

        BlockedMovementEvent blockedMovementEvent = mergeBlockedMovementEvents(potentialBlockedMovementX, potentialBlockedMovementY);
        if(blockedMovementEvent!=null){
            blockedMovementEvent.setHorizontalBlocked(horizontal);
            blockedMovementEvent.setVerticalBlocked(vertical);
            blockedMovementEvent.setMovementVector(movementVector);
        }

        return blockedMovementEvent;
    }

    private BlockedMovementEvent mergeBlockedMovementEvents(BlockedMovementEvent blockedMovementEvent1, BlockedMovementEvent blockedMovementEvent2) {
        if (blockedMovementEvent1 == null) return blockedMovementEvent2;
        if (blockedMovementEvent2 == null) return blockedMovementEvent1;
        if (blockedMovementEvent1.outOfBounds() || blockedMovementEvent2.outOfBounds()) { // Give priority to Out of bounds events
            return new BlockedMovementEvent(blockedMovementEvent1.blockedEntity(), null, true);
        }
        return blockedMovementEvent1;
    }

    private BlockedMovementEvent canMoveTo(IMobileEntity entity, Coordinates pos) {
        double halfWidth = entity.getWidth() / 2;
        double halfHeight = entity.getHeight() / 2;
        for (double i = -halfWidth; i <= halfWidth; i += entity.getWidth()) {
            for (double j = -halfHeight; j <= halfHeight; j += entity.getHeight()) {
                double newX = pos.x + i;
                double newY = pos.y + j;
                if (newX < 0 || newY < 0 || map.isOutOfBounds((int) newX, (int) newY)) {
                    return new BlockedMovementEvent(entity, null, true);
                } else {
                    TileModel tile = map.getTile((int) newX, (int) newY);
                    if (!tile.canEnter(entity)) {
                        return new BlockedMovementEvent(entity, map.getTile((int) newX, (int) newY), false);
                    }
                }
            }
        }
        return null; // All tiles are walkable
    }
}

