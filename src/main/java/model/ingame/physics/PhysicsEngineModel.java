package model.ingame.physics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.geom.Rectangle2D;

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
        List<Iterator<ICollisionEntity>> potentiallyCollided = map.getAllCollidableIteratorsAround((int) entity.getPos().x,(int) entity.getPos().y);

        for (Iterator<ICollisionEntity> iterator : potentiallyCollided) {
            while (iterator.hasNext()) {
                ICollisionEntity other = iterator.next();
                // check if the entities are actually colliding, make sure not to check the entity with itself
                if (entity.getCollisionBox().intersects(other.getCollisionBox()) && !entity.equals(other)) {
                    involvedEntities.add(other);
                }
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
        Coordinates oldPos = entity.getPos();
        int oldX = (int) oldPos.x;
        int oldY = (int) oldPos.y;
        int newX = (int) newPos.x;
        int newY = (int) newPos.y;

        if(entity == null || newPos == null)
            throw new IllegalArgumentException("Entity or newPos cannot be null");


        // check if the entity has collision listeners, and if it does, check if it is colliding with anything  
        List<ICollisionEntity> collidedEntities = getCollidedEntities(entity);
        if(entity.hasCollisionListeners() && collidedEntities.size() > 0){
            // create a collision event
            CollisionEvent event = new CollisionEvent(entity, collidedEntities);
            // notify the entity's collision listeners
            entity.notifyCollisionListeners(event);
            // notify the collided entities' collision listeners
            collidedEntities.forEach(collidedEntity -> collidedEntity.notifyCollisionListeners(event));
        }

        // check if the next position will be on a walkable tile, if not, cancel the movement
        if(!isWalkable(entity, newPos)) return;
        

        // move the entity and its collision box
        entity.setPos(newPos);
        entity.setColisionBox(newX, newY);

        // if the entity has moved to a new tile, update the collidables in the old and new tile
        if(oldX != newX || oldY != newY){
            map.removeCollidableAt(entity, oldX, oldY);
            map.addCollidableAt(entity, newX, newY);
        }
    }

    /**
     * Checks if the next position will be on a walkable tile, given the entity's width and height.
     * 
     * @param entity the entity to check for walkability
     * @param newPos the new position of the entity
     * @return true if the next position will be on a walkable tile, false otherwise
     */
    public boolean isWalkable(IMobileEntity entity, Coordinates newPos){
        if(entity == null || newPos == null)
            throw new IllegalArgumentException("Entity or newPos cannot be null");
        int x = (int) newPos.x;
        int y = (int) newPos.y;
        Rectangle2D.Double tileBox = new Rectangle2D.Double(x, y, 1, 1);
        return tileBox.contains(entity.getCollisionBox());
    }

}
