package model.ingame.entity;

import model.ingame.EntitySpawner;
import model.ingame.GameModel;
import util.Coordinates;

public class FirstAidKitSpawner extends EntitySpawner{

    public FirstAidKitSpawner(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    protected FirstAidKit makeEntity(Coordinates pos) {
        return new FirstAidKit(pos, gameModel);
    }
}
