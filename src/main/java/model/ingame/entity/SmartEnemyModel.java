package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.entity.behavior.FloodFillPathFinder;
import model.ingame.entity.behavior.StandardBehavior;
import model.ingame.physics.MovementHandler;
import model.ingame.weapon.IProjectile;
import model.ingame.weapon.PistolModel;
import model.ingame.weapon.ProjectileWeaponModel;

public class SmartEnemyModel extends CombatEntityModel implements IEffectEntity, IEnemy {
    private final PlayerModel player;
    private static FloodFillPathFinder pathFinder;
    private ModelTimer shootingTimer;
    private IProjectile projectileInstance;

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
        this.projectileInstance = ((ProjectileWeaponModel) getWeapon()).createProjectile();
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
        if(!gameModel.getMapModel().obstaclesBetween(player.getPos(), pos, projectileInstance)){
            shootingTimer.update(delta);
            StandardBehavior.circleAround(this, player, gameModel.getMapModel());
        }
        else{
            pathFinder.handlePathFindingUpdate(this, player.getPos());
        }
        super.update(delta);
    }

    public void aim() {
        PistolModel pistol = (PistolModel) getWeapon();
        pistol.setDirectionVector(new Coordinates(player.getPos().x - pos.x, player.getPos().y - pos.y));
    }
}
