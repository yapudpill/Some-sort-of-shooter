package gui.ingame.entity;

import model.ingame.entity.KnifeZoneEntity;
import model.ingame.entity.BreakableBarrier;
import model.ingame.entity.ExplodingEnemy;
import model.ingame.entity.ExplosionZoneEntity;
import model.ingame.entity.FirstAidKit;
import model.ingame.entity.IEntity;
import model.ingame.entity.PlayerModel;
import model.ingame.entity.SimpleTrap;
import model.ingame.entity.SmartEnemyModel;
import model.ingame.entity.WalkingEnemyModel;
import model.ingame.entity.WeaponEntity;
import model.ingame.weapon.ProjectileModel;
import model.ingame.weapon.RocketProjectileModel;
import model.ingame.weapon.RubberProjectile;

public class EntityRendererFactory {
    static public AbstractEntityRenderer make(IEntity entityModel) {
        return switch (entityModel) {
            case PlayerModel playerModel -> new PlayerRenderer(playerModel);

            case WalkingEnemyModel enemy -> new WalkingEnemyRenderer(enemy);
            case SmartEnemyModel smartEnemy -> new SmartEnemyRenderer(smartEnemy);
            case ExplodingEnemy explodingEnemy -> new ExplodingEnemyRenderer(explodingEnemy);

            case RocketProjectileModel rocketProjectileModel -> new RocketRenderer(rocketProjectileModel);
            case RubberProjectile rubberProjectile -> new RubberBallRenderer(rubberProjectile);
            case ProjectileModel projectileModel -> new ProjectileRenderer(projectileModel);

            case ExplosionZoneEntity explosionZoneEntity -> new ExplosionZoneRenderer(explosionZoneEntity);
            case KnifeZoneEntity knifeZoneEntity -> new KnifeZoneRenderer(knifeZoneEntity);

            case BreakableBarrier breakableBarrier -> new BreakableBarrierRenderer(breakableBarrier);
            case FirstAidKit firstAidKit -> new FirstAidKitRenderer(firstAidKit);
            case WeaponEntity weaponEntity -> new WeaponRenderer(weaponEntity);
            case SimpleTrap simpleTrap -> new TrapRenderer(simpleTrap);

            default -> throw new IllegalArgumentException("Unknown entity model: " + entityModel.getClass().getName());
        };
    }
}
