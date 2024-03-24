package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;

public class FirstAidKit extends CollisionEntityModel {

    public FirstAidKit(Coordinates pos, GameModel gameModel) {
        super(pos, 0.5, 0.5, gameModel);
        addCollisionListener(e -> {
            if (e.getSource() instanceof PlayerModel) {
                PlayerModel player = (PlayerModel) e.getSource();
                player.setHealth(player.getHealth() + 20);
                despawn();
            }
        });
    }
}
