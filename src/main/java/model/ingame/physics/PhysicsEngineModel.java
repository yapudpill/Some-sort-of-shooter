package model.ingame.physics;

import java.util.ArrayList;
import java.util.List;

import model.ingame.Coordinates;
import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IMobileEntity;
import model.level.MapModel;

/**
 * The <code>PhysicsEngineModel</code> class is used to handle the physics of the game, such as collision detection and entity movement.
 */
public class PhysicsEngineModel {
    private final MapModel map;

    public PhysicsEngineModel(MapModel map) {
        if (map == null)
            throw new IllegalArgumentException("Map cannot be null");
        this.map = map;
    }

    /**
     * Returns a list of all entities that are colliding with the given entity.
     *
     * @param entity the entity to check for collisions
     * @return a list of all entities that are colliding with the given entity
     */

    public List<ICollisionEntity> getCollidedEntities(ICollisionEntity entity) {
        List<ICollisionEntity> involvedEntities = new ArrayList<>();
        // Get all the entities that could be colliding with the given entity, i.e the entities in the 3x3 grid around the given entity.
        List<ICollisionEntity> potentiallyCollided = map.getAllCollidablesAround((int) entity.getPos().x, (int) entity.getPos().y);
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
     * @param entity the entity to move
     * @param newPos the new position of the entity
     */

    public void move(IMobileEntity entity, Coordinates newPos){

        if(entity == null || newPos == null)
            throw new IllegalArgumentException("Entity or newPos cannot be null");


        // check if the entity has collision listeners, and if it does, check if it is colliding with anything
        List<ICollisionEntity> collidedEntities = getCollidedEntities(entity);
        if(collidedEntities.size() > 0){
            // create a collision event
            CollisionEvent event = new CollisionEvent(entity, collidedEntities);
            // notify the entity's collision listeners
            entity.notifyCollisionListeners(event);
            // notify the collided entities' collision listeners
            collidedEntities.forEach(collidedEntity -> collidedEntity.notifyCollisionListeners(event));
        }

        // check if the next position will be on a walkable tile, if not, cancel the movement
        if(!isWalkable(entity, newPos)){
            entity.getMovementHandler().setMoving(false);
            return;
        }

        // move the entity and its collision box
        entity.getMovementHandler().setMoving(true);
        entity.setPos(newPos);

    }

    /**
     * Checks if the next position will be on a walkable tile, given the entity's width and height.
     *
     * @param entity the entity to check for walkability
     * @param newPos the new position of the entity
     * @return true if the next position will be on a walkable tile, false otherwise
     */
    public boolean isWalkable(IMobileEntity entity, Coordinates newPos){
        if (entity == null || newPos == null) {
            throw new IllegalArgumentException("Entity or newPos cannot be null");
        }

        double halfWidth = entity.getWidth() / 2;
        double halfHeight = entity.getHeight() / 2;

        for(double i = -halfWidth; i <= halfWidth; i += entity.getWidth()){
            for(double j = -halfHeight; j <= halfHeight; j += entity.getHeight()){
                double newX = newPos.x + i;
                double newY = newPos.y + j;
                if(newX < 0 || newY < 0 || !map.isWalkableAt((int) newX, (int) newY)){
                    return false; // At least one tile is not walkable
                }
            }
        }

        return true; // All tiles are walkable
    }

}
