package gui.ingame.entity;

import model.ingame.entity.IEntity;
import model.ingame.entity.PlayerModel;
import model.ingame.entity.SmartEnemyModel;
import model.ingame.entity.WalkingEnemyModel;
import model.ingame.entity.WeaponEntity;
import model.ingame.weapon.ProjectileModel;

public class EntityRendererFactory {
    static public AbstractEntityRenderer makeEntityRenderer(IEntity entityModel) {
        return switch (entityModel) {
            case PlayerModel playerModel -> new PlayerRenderer(playerModel);
            case ProjectileModel projectileModel -> new ProjectileRenderer(projectileModel);
            case WalkingEnemyModel enemy -> new WalkingEnemyRenderer(enemy);
            case WeaponEntity weaponEntity -> new WeaponRenderer(weaponEntity);
            case SmartEnemyModel smartEnemy -> new SmartEnemyRenderer(smartEnemy);
            default -> {
                System.out.println("EntityRendererFactory: unknown entity model: " + entityModel.getClass().getName());
                yield null; // TODO: should we throw an exception here? or return a default renderer?
            }
        };
    }
}
