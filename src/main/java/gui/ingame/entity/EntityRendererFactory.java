package gui.ingame.entity;

import model.ingame.entity.BreakableBarrier;
import model.ingame.entity.ExplodingEnemy;
import model.ingame.entity.ExplosionZoneEntity;
import model.ingame.entity.FirstAidKit;
import model.ingame.entity.IEntity;
import model.ingame.entity.KnifeZoneEntity;
import model.ingame.entity.PlayerModel;
import model.ingame.entity.SimpleTrap;
import model.ingame.entity.SmartEnemyModel;
import model.ingame.entity.WalkingEnemyModel;
import model.ingame.entity.WeaponEntity;
import model.ingame.weapon.BulletsModel;
import model.ingame.weapon.RocketProjectileModel;
import model.ingame.weapon.RubberProjectile;
import model.ingame.weapon.TornadoProjectileModel;

public class EntityRendererFactory {
    public static AbstractEntityRenderer make(IEntity entity) {
        return switch (entity) {
            case PlayerModel e -> new PlayerRenderer(e);

            case WalkingEnemyModel e -> new VulnerableSpriteRenderer(e, "sprites/EyeBallEnemy.png");
            case BreakableBarrier e  -> new BreakableBarrierRenderer(e);

            case RubberProjectile e -> new SpriteRenderer(e, "sprites/weapon/rubberball.png");
            case BulletsModel e -> new AnimatedEntityRenderer(e, "animations/bullets.xml", e.getMovementHandler().getDirectionVector()::getAngle);
            case RocketProjectileModel e -> new AnimatedEntityRenderer(e, "animations/rocket_projectile.xml", e.getMovementHandler().getDirectionVector()::getAngle);
            case TornadoProjectileModel e -> new AnimatedEntityRenderer(e, "animations/tornado.xml", () -> {return 0.0;});

            case SmartEnemyModel e   -> new VulnerableAnimatedRenderer(e, "animations/smart_enemy.xml");
            case ExplodingEnemy e   -> new VulnerableAnimatedRenderer(e, "animations/exploding_enemy.xml");
            case ExplosionZoneEntity e -> new AnimatedEntityRenderer(e, "animations/explosion_zone.xml");
            case KnifeZoneEntity e     -> new AnimatedEntityRenderer(e, "animations/knife_zone.xml", e.getDirection()::getAngle);

            case SimpleTrap e -> new SpriteRenderer(e, "sprites/weapon/simple_trap_placer.png");

            case FirstAidKit e  -> new SpriteRenderer(e, "sprites/firstaid.png");
            case WeaponEntity e -> new SpriteRenderer(e, String.format("sprites/weapon/%s.png", e.getWeapon().getIdentifier()));

            default -> throw new IllegalArgumentException("Unknown entity model: " + entity.getClass().getName());
        };
    }
}
