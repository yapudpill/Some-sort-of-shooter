package gui.ingame.entity;

import java.awt.Image;
import java.util.Map;

import gui.ImageCache;
import model.ingame.entity.BreakableBarrier;
import model.ingame.entity.ExplodingEnemy;
import model.ingame.entity.ExplosionZoneEntity;
import model.ingame.entity.FirstAidKit;
import model.ingame.entity.IEntity;
import model.ingame.entity.IVulnerableEntity;
import model.ingame.entity.KnifeZoneEntity;
import model.ingame.entity.PlayerModel;
import model.ingame.entity.SimpleTrap;
import model.ingame.entity.SmartEnemyModel;
import model.ingame.entity.WalkingEnemyModel;
import model.ingame.entity.WeaponEntity;
import model.ingame.weapon.BlackHoleProjectile;
import model.ingame.weapon.BlackHoleZone;
import model.ingame.weapon.BulletsModel;
import model.ingame.weapon.RocketProjectileModel;
import model.ingame.weapon.RubberProjectile;

/**
 * Factory class for creating entity renderers based on the provided entity model.
 */
public class EntityRendererFactory {
    public static EntityRenderer make(IEntity entity) {
        return switch (entity) {

            // Player
            case PlayerModel e -> new PlayerRenderer(e);

            // Enemies
            case WalkingEnemyModel e -> new VulnerableSpriteRenderer(e, "sprites/EyeBallEnemy.png");
            case SmartEnemyModel e   -> new VulnerableAnimatedRenderer(e, "animations/smart_enemy.xml");
            case ExplodingEnemy e    -> new VulnerableAnimatedRenderer(e, "animations/exploding_enemy.xml");

            // Bullets
            case BulletsModel e          -> new SpriteRenderer(e, "sprites/weapon/bullet.png", e.getMovementHandler().getDirectionVector()::getAngle);
            case RubberProjectile e      -> new SpriteRenderer(e, "sprites/weapon/rubberball.png");
            case RocketProjectileModel e -> new AnimatedEntityRenderer(e, "animations/rocket_projectile.xml", e.getMovementHandler().getDirectionVector()::getAngle);
            case BlackHoleProjectile e   -> new AnimatedEntityRenderer(e, "animations/blackhole.xml");

            // Damage zones
            case ExplosionZoneEntity e -> new AnimatedEntityRenderer(e, "animations/explosion_zone.xml");
            case KnifeZoneEntity e     -> new AnimatedEntityRenderer(e, "animations/knife_zone.xml", e.getDirection()::getAngle);
            case BlackHoleZone e       -> new EntityRenderer(e);
            case SimpleTrap e          -> new SpriteRenderer(e, "sprites/weapon/simple_trap_placer.png");

            // Mics entities
            case BreakableBarrier e -> {
                Image full = ImageCache.loadImage("sprites/breakablebarrier.png", PlayerRenderer.class);
                Image mid = ImageCache.loadImage("sprites/breakablebarrier_2.png", PlayerRenderer.class);
                Image weak = ImageCache.loadImage("sprites/breakablebarrier_3.png", PlayerRenderer.class);

                yield new ConditionalRenderer(e, Map.of(
                    toRender -> ((IVulnerableEntity) toRender).getHealth() >= 60, full,
                    toRender -> ((IVulnerableEntity) toRender).getHealth() >= 30
                    && ((IVulnerableEntity) toRender).getHealth() < 60, mid,
                    toRender -> ((IVulnerableEntity) toRender).getHealth() < 30, weak
                ));
            }
            case FirstAidKit e  -> new SpriteRenderer(e, "sprites/firstaid.png");
            case WeaponEntity e -> new SpriteRenderer(e, String.format("sprites/weapon/%s.png", e.getWeapon().getIdentifier()));

            default -> throw new IllegalArgumentException("Unknown entity model: " + entity.getClass().getName());
        };
    }
}
