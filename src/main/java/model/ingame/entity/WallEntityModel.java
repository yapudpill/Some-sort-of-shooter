package model.ingame.entity;

import model.ingame.Coordinates;

public class WallEntityModel extends CollisionEntityModel{

    public WallEntityModel(Coordinates pos) {
        super(pos, 1, 1);
        addCollisionListener(e -> {
            if(e.getSource() instanceof IMobileEntity mobileEntity){
                mobileEntity.getMovementHandler().setDirectionVector(mobileEntity.getMovementHandler().getDirectionVector().opposite());
            }
        });
    }
    
}
