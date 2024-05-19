package model.ingame.entity;

import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.entity.behavior.FloodFillPathFinder;
import model.ingame.weapon.PistolModel;
import model.ingame.weapon.Projectile;
import model.ingame.weapon.ProjectileWeaponModel;
import util.Coordinates;

/**
 * Model for the smart enemy entity. Shoots at the player from a distance.
 */
public class SmartEnemyModel extends CombatEntityModel implements IEffectEntity {
    private static FloodFillPathFinder pathFinder;
    private boolean stopped = false;
    private final PlayerModel player;
    private ModelTimer shootingTimer;
    private Projectile projectileInstance;

    public SmartEnemyModel(Coordinates pos, GameModel gameModel) {
        super(pos, 3.6, 50, 0.9,0.9, gameModel);
        player = gameModel.getPlayer();
        setWeapon(new PistolModel(this, gameModel));
        shootingTimer = new ModelTimer(1, true, () -> {
            aim();
            attack();
        }, gameModel);
        this.projectileInstance = ((ProjectileWeaponModel) getWeapon()).createProjectile();
    }
    @Override
    public boolean canApplyEffect(IVulnerableEntity target) {
        return target instanceof PlayerModel || target instanceof BreakableBarrier;
    }

    public static void setPathFinder(FloodFillPathFinder pathFinder) {
        SmartEnemyModel.pathFinder = pathFinder;
    }

    @Override
    public void update(double delta) {
        if (!gameModel.getMapModel().obstaclesBetween(player.getPos(), pos, projectileInstance) && pos.isInCenter()) {
            shootingTimer.update(delta);
            if(!stopped) {
                movementHandler.setDirectionVector(Coordinates.ZERO);
                stopped = true;
            }
        } else {
            stopped = false;
            pathFinder.handlePathFindingUpdate(this, player.getPos());
        }
        super.update(delta);
    }

    public void aim() {
        PistolModel pistol = (PistolModel) getWeapon();
        pistol.setDirectionVector(new Coordinates(player.getPos().x() - pos.x(), player.getPos().y() - pos.y()));
    }
}
