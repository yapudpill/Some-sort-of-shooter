package model.ingame.physics;

/**
 * Listener for collision events.
 */
@FunctionalInterface
public interface CollisionListener {

    void onCollision(CollisionEvent e);

}
