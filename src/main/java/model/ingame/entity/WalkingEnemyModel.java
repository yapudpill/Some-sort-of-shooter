package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.entity.behavior.FloodFillPathFinder;
import model.ingame.physics.MovementHandlerModel;
import model.ingame.weapon.KnifeWeapon;

public class WalkingEnemyModel extends CombatEntityModel implements IEffectEntity {
    private final PlayerModel player;
    private static FloodFillPathFinder pathFinder;

    public WalkingEnemyModel(Coordinates pos, GameModel gameModel) {
        super(50, 0.8, 0.8, gameModel);
        this.player = gameModel.getPlayer();
        this.pos = pos;
        movementHandler = new MovementHandlerModel<WalkingEnemyModel>(this, gameModel.getPhysicsEngine());
        movementHandler.setSpeed(0.01);
        addCollisionListener(e -> {
            for (ICollisionEntity entity : e.getInvolvedEntitiesList()) {
                if (entity instanceof PlayerModel) {
                    attack();
                }
            }
        });
        setWeapon(new KnifeWeapon(this, gameModel));
    }

    @Override
    public boolean canApplyEffect(IVulnerableEntity target) {
        return target instanceof PlayerModel;
    }

    public static void setPathFinder(FloodFillPathFinder pathFinder) {
        WalkingEnemyModel.pathFinder = pathFinder;
    }

    @Override
    public void update() {
        pathFinder.handlePathFindingUpdate(this, player.getPos());
        super.update();
    }

}
