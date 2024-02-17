package gui.ingame.entity;

import model.ingame.entity.EntityModel;
import model.ingame.entity.PlayerModel;

public class EntityRendererFactory {
    static public AbstractEntityRenderer makeEntityRenderer(EntityModel entityModel) {
        return switch (entityModel) {
            case PlayerModel playerModel -> new PlayerRenderer(playerModel);
            default -> {
                System.out.println("EntityRendererFactory: unknown entity model: " + entityModel.getClass().getName());
                yield null; // TODO: should we throw an exception here? or return a default renderer?
            }
        };
    }
}
