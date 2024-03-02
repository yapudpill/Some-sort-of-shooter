package model.ingame.physics;

@FunctionalInterface
public interface CollisionListener {

    void onCollision(CollisionEvent e);

}
