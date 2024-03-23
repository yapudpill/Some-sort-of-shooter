package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.entity.behavior.FloodFillPathFinder;
import model.ingame.physics.MovementHandlerModel;
import model.ingame.weapon.PistolModel;
import util.ModelTimer;

public class SmartEnemyModel extends CombatEntityModel implements IEffectEntity {
    private final PlayerModel player;
    private static FloodFillPathFinder pathFinder;
    private ModelTimer shootingTimer;

    public SmartEnemyModel(Coordinates pos, GameModel gameModel) {
        super(50, 0.8, 0.8, gameModel);
        this.player = gameModel.getPlayer();
        movementHandler = new MovementHandlerModel<SmartEnemyModel>(this, gameModel.getPhysicsEngine());
        movementHandler.setSpeed(0.06);
        this.pos = pos;
        setWeapon(new PistolModel(this, gameModel));
        shootingTimer = new ModelTimer(1*60, () -> {
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
    public void update() {
        if(!gameModel.getMapModel().obstaclesBetween(player.getPos(), pos)){
            if(!shootingTimer.isRunning()) shootingTimer.start();
            // circle around player
            movementHandler.setDirectionVector(getNextDirectionToCircleAround());
        }
        else{
            shootingTimer.stop();
            pathFinder.setTarget(player.getPos());
            if(!pathFinder.isRunning()) pathFinder.start();
            Coordinates lowestCoord = pathFinder.getLowestNodeAround((int) pos.x, (int) pos.y);
            if(pos.isInCenter() || !movementHandler.isMoving()) movementHandler.setDirectionVector(new Coordinates( lowestCoord.x - pos.x, lowestCoord.y - pos.y));
        }
        super.update();
    }

    public void aim(){
        PistolModel pistol = (PistolModel) getWeapon();
        pistol.setDirectionVector(new Coordinates(player.getPos().x - pos.x, player.getPos().y - pos.y));
    }

    public Coordinates getNextDirectionToCircleAround(){
        Coordinates result = new Coordinates(0,0);
        int[][] rotation = new int[][]{{0,-1},{1,0}};
        Coordinates playerPos = player.getPos();
        Coordinates direction = new Coordinates(playerPos.x - pos.x, playerPos.y - pos.y);
        result.x = direction.x * rotation[0][0] + direction.y * rotation[0][1];
        result.y = direction.x * rotation[1][0] + direction.y * rotation[1][1];
        return result;
    }

}
