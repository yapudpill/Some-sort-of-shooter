package gui.ingame.entity;

import java.awt.Color;

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
import model.ingame.weapon.Projectile;
import model.ingame.weapon.RocketProjectileModel;
import model.ingame.weapon.RubberProjectile;

public class EntityRendererFactory {
    public static AbstractEntityRenderer make(IEntity entity) {
        return switch (entity) {
            case PlayerModel e -> new PlayerRenderer(e);

            case WalkingEnemyModel e -> new VulnerableSpriteRenderer(e, "sprites/EyeBallEnemy.png");
            case SmartEnemyModel e   -> new VulnerableSpriteRenderer(e, "sprites/Brain_of_Cthulhu.png");
            case ExplodingEnemy e    -> new VulnerableSpriteRenderer(e, "sprites/bombman.png");
            case BreakableBarrier e  -> new VulnerableSpriteRenderer(e, "sprites/breakablebarrier.png");

            case RocketProjectileModel e -> new CircleRenderer(e, Color.RED);
            case RubberProjectile e      -> new CircleRenderer(e, Color.BLUE);
            case Projectile e            -> new CircleRenderer(e, Color.BLACK);

            case ExplosionZoneEntity e -> new AnimatedEntityRenderer(e, "animations/explosion_zone.xml");
            case KnifeZoneEntity e     -> new AnimatedEntityRenderer(e, "animations/knife_zone.xml", e.getDirection()::getAngle);

            case SimpleTrap e -> new RectangleRenderer(e, Color.BLACK);

            case FirstAidKit e  -> new SpriteRenderer(e, "sprites/firstaid.png");
            case WeaponEntity e -> new SpriteRenderer(e, String.format("sprites/weapon/%s.png", e.getWeapon().getIdentifier()));

            default -> throw new IllegalArgumentException("Unknown entity model: " + entity.getClass().getName());
        };
    }
}
