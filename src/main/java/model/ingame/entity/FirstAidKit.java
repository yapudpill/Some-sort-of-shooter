package model.ingame.entity;

import model.ingame.GameModel;
import util.Coordinates;

public class FirstAidKit extends CollisionEntityModel {

    FirstAidKit(Coordinates pos, GameModel gameModel) {
        super(pos, 0.5, 0.5, gameModel);
        addCollisionListener(e -> {
            if (e.getInvolvedEntitiesList().stream().anyMatch(entity -> entity instanceof PlayerModel)) {
                PlayerModel player = gameModel.getPlayer();
                player.setHealth(player.getHealth() + 20);
                this.despawn();
            }
        });
    }
}
