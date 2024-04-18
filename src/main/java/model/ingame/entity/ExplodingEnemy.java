package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.entity.behavior.FloodFillPathFinder;
import model.ingame.physics.MovementHandlerModel;

public class ExplodingEnemy extends CreatureModel implements IEffectEntity{
    private static FloodFillPathFinder pathFinder;
    private final PlayerModel player;

    public ExplodingEnemy(Coordinates pos, GameModel gameModel) {
        super(50, 0.8, 0.8, gameModel);
        this.pos = pos;
        this.player = gameModel.getPlayer();
        movementHandler = new MovementHandlerModel<ExplodingEnemy>(this, gameModel.getPhysicsEngine());
        movementHandler.setSpeed(0.09);
        addCollisionListener(e -> {
            if(e.getInvolvedEntitiesList().contains(player)) {
                gameModel.addEntity(new ExplosionZoneEntity(this.pos,2, 2, 10,100, gameModel));
                this.despawn();
            }
        });
    }

    @Override
    public boolean canApplyEffect(IVulnerableEntity target) {
        return target instanceof PlayerModel;
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
    }

    public static void setPathFinder(FloodFillPathFinder pathFinder) {
        ExplodingEnemy.pathFinder = pathFinder;
    }

    @Override
    public void update() {
        pathFinder.handlePathFindingUpdate(this, player.getPos());
        super.update();
    }
}
