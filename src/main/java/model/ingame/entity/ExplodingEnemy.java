package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.entity.behavior.FloodFillPathFinder;
import util.Coordinates;

/**
 * An enemy that explodes when it comes into contact with the player or a breakable barrier.
 */
public class ExplodingEnemy extends CombatEntityModel implements IEffectEntity {
    private static FloodFillPathFinder pathFinder;
    private final PlayerModel player;

    public ExplodingEnemy(Coordinates pos, GameModel gameModel) {
        super(pos, 2.4, 50, 0.8, 0.8, gameModel, 0);
        this.pos = pos;
        this.player = gameModel.getPlayer();
        addCollisionListener(e -> {
            if (e.getInvolvedEntitiesList().contains(player)) {
                explode();
            }
        });
        addBlockedMovementListener(e -> {
            if (!e.outOfBounds() && e.blockingTile().getCollidablesSet().stream().anyMatch(entity -> entity instanceof BreakableBarrier)) {
                explode();
            }
        });
    }

    public void explode() {
        gameModel.addEntity(new ExplosionZoneEntity(this.pos, 2, 2, 10, 1, gameModel));
        this.takeDamage(health);
    }

    @Override
    public boolean canApplyEffect(IVulnerableEntity target) {
        return target instanceof PlayerModel;
    }

    @Override
    public void takeDamage(double damage) {
        super.takeDamage(damage);
    }

    public static void setPathFinder(FloodFillPathFinder pathFinder) {
        ExplodingEnemy.pathFinder = pathFinder;
    }

    @Override
    public void update(double delta) {
        pathFinder.handlePathFindingUpdate(this, player.getPos(),true);
        super.update(delta);
    }
}
