package model.ingame.physics;

import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IMobileEntity;
import model.ingame.entity.IVulnerableEntity;
import model.level.MapModel;
import model.level.TileModel;
import util.Coordinates;
import util.IUpdateable;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * The <code>PhysicsEngineModel</code> class is used to handle the physics of the game, such as collision detection and entity movement.
 */
public class PhysicsEngine implements IUpdateable {
    private final Set<ICollisionEntity> collisionEntities;
    private final MapModel map;

    public PhysicsEngine(MapModel map, Set<ICollisionEntity> collisionEntities) {
        if (map == null) {
            throw new IllegalArgumentException("Map cannot be null");
        }
        this.map = map;
        this.collisionEntities = collisionEntities;
    }

    public void update(double delta) {
        for (ICollisionEntity entity : collisionEntities) {
            Set<ICollisionEntity> collidedEntities = getCollidedEntities(entity);

            if (!collidedEntities.isEmpty()) {
                CollisionEvent event = new CollisionEvent(entity, collidedEntities);
                entity.notifyCollisionListeners(event);
            }
        }
    }

    /**
     * Returns a list of all entities that are colliding with the given entity.
     *
     * @param entity the entity to check for collisions
     * @return a set of all entities that are colliding with the given entity
     */
    public Set<ICollisionEntity> getCollidedEntities(ICollisionEntity entity) {
        Set<ICollisionEntity> involvedEntities = new CopyOnWriteArraySet<>();
        // Get all the entities that could be colliding with the given entity, i.e the entities in the 3x3 grid around the given entity.
        Set<ICollisionEntity> potentiallyCollided = map.getAllCollidablesAround(entity.getPos());
        for (ICollisionEntity other : potentiallyCollided) {
            // check if the entities are actually colliding, make sure not to check the entity with itself
            if (entity.getCollisionBox().intersects(other.getCollisionBox()) && !entity.equals(other)) {
                involvedEntities.add(other);
            }
        }
        handleExit(entity.updateCollidedEntities(involvedEntities), entity);
        return involvedEntities;
    }

    /**
     * Moves the given entity to the given position, and checks for collisions.
     *
     * @param entity         the entity to move
     * @param movementVector the new position of the entity
     */
    public void move(IMobileEntity entity, Coordinates movementVector) {
        if (entity == null || (entity instanceof IVulnerableEntity vuln && vuln.isDead())) return;
        // Adjust movement to collide with walls
        BlockedMovementEvent blockedMovementEvent = handleBlockedMovement(entity, movementVector);
        Coordinates adjustedMovement = movementVector;
        if (blockedMovementEvent != null) {
            entity.notifyBlockedMovementListeners(blockedMovementEvent);
            adjustedMovement = blockedMovementEvent.getAdjustedMovement();
        }
        if (adjustedMovement.equals(Coordinates.ZERO)) {
            entity.getMovementHandler().setMoving(false);
            return;
        }

        // move the entity and its collision box
        entity.getMovementHandler().setMoving(true);
        entity.setPos(entity.getPos().add(adjustedMovement));
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
        if (blockedMovementEvent != null) {
            blockedMovementEvent.setHorizontalBlocked(horizontal);
            blockedMovementEvent.setVerticalBlocked(vertical);
            blockedMovementEvent.setMovementVector(movementVector);
        }

        return blockedMovementEvent;
    }

    private void handleExit(Set<ICollisionEntity> exitEntities, ICollisionEntity entity) {
        ExitEvent event = new ExitEvent(entity, exitEntities);
        entity.notifyExitListeners(event);
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
                Coordinates newPos = pos.add(new Coordinates(i, j));
                if (map.isOutOfBounds(newPos)) {
                    return new BlockedMovementEvent(entity, null, true);
                } else {
                    TileModel tile = map.getTile(newPos);
                    if (!tile.canEnter(entity)) {
                        return new BlockedMovementEvent(entity, map.getTile(newPos), false);
                    }
                }
            }
        }
        return null; // All tiles are walkable
    }
}

