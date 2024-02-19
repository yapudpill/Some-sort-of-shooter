package gui.ingame.entity;

import model.ingame.entity.EntityModel;
import model.ingame.entity.IEntity;
import model.ingame.entity.PlayerModel;
import model.ingame.weapon.ProjectileModel;

public class EntityRendererFactory {
    static public AbstractEntityRenderer makeEntityRenderer(IEntity entityModel) {
        return switch (entityModel) {
            case PlayerModel playerModel -> new PlayerRenderer(playerModel);
            case ProjectileModel projectileModel -> new ProjectileRenderer(projectileModel);
            default -> {
                System.out.println("EntityRendererFactory: unknown entity model: " + entityModel.getClass().getName());
                yield null; // TODO: should we throw an exception here? or return a default renderer?
            }
        };
    }
}
