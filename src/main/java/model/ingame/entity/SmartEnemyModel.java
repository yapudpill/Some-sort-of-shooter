package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.entity.behavior.FloodFillPathFinder;
import model.ingame.physics.MovementHandler;
import model.ingame.weapon.PistolModel;

public class SmartEnemyModel extends CombatEntityModel implements IEffectEntity {
    private final PlayerModel player;
    private static FloodFillPathFinder pathFinder;
    private ModelTimer shootingTimer;

    public SmartEnemyModel(Coordinates pos, GameModel gameModel) {
        super(pos, 50, 0.8, 0.8, gameModel);
        player = gameModel.getPlayer();
        movementHandler = new MovementHandler(this, gameModel.getPhysicsEngine());
        movementHandler.setSpeed(3.6);
        setWeapon(new PistolModel(this, gameModel));
        shootingTimer = new ModelTimer(1, true, () -> {
            aim();
            attack();
        }, gameModel);
    }

    @Override
    public boolean canApplyEffect(IVulnerableEntity target) {
        return target instanceof PlayerModel;
    }

    public static void setPathFinder(FloodFillPathFinder pathFinder) {
        SmartEnemyModel.pathFinder = pathFinder;
    }

    @Override
    public void update(double delta) {
        if(!gameModel.getMapModel().obstaclesBetween(player.getPos(), pos)) {
            if(!shootingTimer.isRunning()) shootingTimer.start();
            // circle around player
            Coordinates playerPos = player.getPos();
            Coordinates direction = new Coordinates(playerPos.x - pos.x, playerPos.y - pos.y);
            movementHandler.setDirectionVector(direction.rotate(Math.PI/2).normalize());
        }
        else {
            shootingTimer.stop();
            pathFinder.setTarget(player.getPos());
            if(!pathFinder.isRunning()) pathFinder.start();
            Coordinates lowestCoord = pathFinder.getLowestNodeAround((int) pos.x, (int) pos.y);
            if(pos.isInCenter() || !movementHandler.isMoving()) movementHandler.setDirectionVector(new Coordinates( lowestCoord.x - pos.x, lowestCoord.y - pos.y));
        }
        super.update(delta);
    }

    public void aim() {
        PistolModel pistol = (PistolModel) getWeapon();
        pistol.setDirectionVector(new Coordinates(player.getPos().x - pos.x, player.getPos().y - pos.y));
    }
}
