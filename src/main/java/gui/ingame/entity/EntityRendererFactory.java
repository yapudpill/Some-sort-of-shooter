package gui.ingame.entity;

import model.ingame.entity.AttachedDamageZoneEntity;
import model.ingame.entity.ExplosionZoneEntity;
import model.ingame.entity.IEntity;
import model.ingame.entity.PlayerModel;
import model.ingame.entity.SimpleTrap;
import model.ingame.entity.SmartEnemyModel;
import model.ingame.entity.WalkingEnemyModel;
import model.ingame.entity.WeaponEntity;
import model.ingame.weapon.ProjectileModel;
import model.ingame.weapon.RocketProjectileModel;

public class EntityRendererFactory {
    static public AbstractEntityRenderer make(IEntity entityModel) {
        return switch (entityModel) {
            case PlayerModel playerModel -> new PlayerRenderer(playerModel);

            case WalkingEnemyModel enemy -> new WalkingEnemyRenderer(enemy);
            case SmartEnemyModel smartEnemy -> new SmartEnemyRenderer(smartEnemy);

            case RocketProjectileModel rocketProjectileModel -> new RocketRenderer(rocketProjectileModel);
            case ProjectileModel projectileModel -> new ProjectileRenderer(projectileModel);
            case SimpleTrap simpleTrap -> new TrapRenderer(simpleTrap);

            case ExplosionZoneEntity explosionZoneEntity -> new ExplosionZoneRenderer(explosionZoneEntity);
            case AttachedDamageZoneEntity debugDamageZoneEntity -> new DebugDamageZoneRenderer(debugDamageZoneEntity);
            case WeaponEntity weaponEntity -> new WeaponRenderer(weaponEntity);

            default -> throw new IllegalArgumentException("Unknown entity model: " + entityModel.getClass().getName());
        };
    }
}
