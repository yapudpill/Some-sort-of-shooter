package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.CollisionEntityModel;
import model.ingame.entity.CreatureModel;
import model.ingame.entity.PlayerModel;
import model.ingame.physics.MovementHandler;
import util.Coordinates;

public class BlackHoleZone extends CollisionEntityModel{
    public BlackHoleZone(Coordinates pos, GameModel gameModel, Coordinates center) {
        super(pos, 1,1, gameModel);
        addCollisionListener(e-> {
            e.getInvolvedEntitiesList().forEach(ent -> {
                if(!(ent instanceof PlayerModel) && ent instanceof CreatureModel c) {
                    // pull the creature towards the black hole
                    MovementHandler movementHandler = c.getMovementHandler();
                    movementHandler.setDirectionVector(movementHandler.getDirectionVector().add(center.add(c.getPos().opposite()).normalize()));
                }
            });

        });
    }

}