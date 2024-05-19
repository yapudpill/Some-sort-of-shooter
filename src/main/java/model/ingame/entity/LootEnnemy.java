package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.entity.behavior.FloodFillPathFinder;
import model.ingame.physics.MovementHandler;
import model.ingame.weapon.KnifeWeapon;
import util.Coordinates;

/**
 * Model for the walking enemy entity. Moves towards the player and attacks when close.
 */
public class LootEnnemy extends CombatEntityModel implements IEffectEntity {
    private static FloodFillPathFinder pathFinder;

    private final PlayerModel player;

    public LootEnnemy(Coordinates pos, GameModel gameModel) {
        super(pos, 300, 1.1, 1.1, gameModel, 3);
        player = gameModel.getPlayer();
        movementHandler = new MovementHandler(this, gameModel.getPhysicsEngine());
        movementHandler.setSpeed(3);
        addCollisionListener(e -> {
            for (ICollisionEntity entity : e.getInvolvedEntitiesList()) {
                if (entity instanceof PlayerModel) {
                    attack();
                }
            }
        });
        setWeapon(new KnifeWeapon(this, gameModel));
        addBlockedMovementListener(e -> {
            if (!e.outOfBounds() && e.blockingTile().getCollidablesSet().stream().anyMatch(entity -> entity instanceof BreakableBarrier)) {
                attack();
            }
        });
    }

    @Override
    public void despawn() {
        super.despawn();
        player.upgradeRandom();
    }

    @Override
    public boolean canApplyEffect(IVulnerableEntity target) {
        return target instanceof PlayerModel;
    }

    public static void setPathFinder(FloodFillPathFinder pathFinder) {
        LootEnnemy.pathFinder = pathFinder;
    }

    @Override
    public void update(double delta) {
        pathFinder.handlePathFindingUpdate(this, player.getPos(), false);
        super.update(delta);
    }
}
