package gui.ingame.entity;

import model.ingame.entity.AttachedDamageZoneEntity;
import model.ingame.entity.ExplosionZoneEntity;
import model.ingame.entity.FirstAidKit;
import model.ingame.entity.IEntity;
import model.ingame.entity.IVulnerableEntity;
import model.ingame.entity.SimpleTrap;
import model.ingame.entity.WeaponEntity;
import model.ingame.weapon.ProjectileModel;
import model.ingame.weapon.RocketProjectileModel;
import model.ingame.weapon.RubberProjectile;

public class EntityRendererFactory {
    static public AbstractEntityRenderer make(IEntity entityModel) {
        return switch (entityModel) {
            case IVulnerableEntity vul -> new VulnerableRenderer(vul);

            case RocketProjectileModel rocketProjectileModel -> new RocketRenderer(rocketProjectileModel);
            case RubberProjectile rubberProjectile -> new RubberBallRenderer(rubberProjectile);
            case ProjectileModel projectileModel -> new ProjectileRenderer(projectileModel);

            case ExplosionZoneEntity explosionZoneEntity -> new ExplosionZoneRenderer(explosionZoneEntity);
            case AttachedDamageZoneEntity debugDamageZoneEntity -> new DebugDamageZoneRenderer(debugDamageZoneEntity);

            case FirstAidKit firstAidKit -> new FirstAidKitRenderer(firstAidKit);
            case WeaponEntity weaponEntity -> new WeaponRenderer(weaponEntity);
            case SimpleTrap simpleTrap -> new TrapRenderer(simpleTrap);

            default -> throw new IllegalArgumentException("Unknown entity model: " + entityModel.getClass().getName());
        };
    }
}
