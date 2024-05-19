package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.entity.behavior.FloodFillPathFinder;
import model.ingame.physics.MovementHandler;
import model.ingame.weapon.KnifeWeapon;
import util.Coordinates;

/**
 * Model for the walking enemy entity. Moves towards the player and attacks when close.
 */
public class WalkingEnemyModel extends CombatEntityModel implements IEffectEntity {
    private static FloodFillPathFinder pathFinder;

    private final PlayerModel player;

    public WalkingEnemyModel(Coordinates pos, GameModel gameModel) {
        super(pos, 50, 0.8, 0.8, gameModel, 0);
        player = gameModel.getPlayer();
        movementHandler = new MovementHandler(this, gameModel.getPhysicsEngine());
        movementHandler.setSpeed(2.4);
        addCollisionListener(e -> {
            for (ICollisionEntity entity : e.getInvolvedEntitiesList()) {
                if (entity instanceof PlayerModel) {
                    attack();
                }
            }
        });
        setWeapon(new KnifeWeapon(this, gameModel));
        addBlockedMovementListener(e -> {
            if (e.blockingTile().getCollidablesSet().stream().anyMatch(entity -> entity instanceof BreakableBarrier)) {
                attack();
            }
        });
    }

    @Override
    public boolean canApplyEffect(IVulnerableEntity target) {
        return target instanceof PlayerModel;
    }

    public static void setPathFinder(FloodFillPathFinder pathFinder) {
        WalkingEnemyModel.pathFinder = pathFinder;
    }

    @Override
    public void update(double delta) {
        pathFinder.handlePathFindingUpdate(this, player.getPos());
        super.update(delta);
    }
}
