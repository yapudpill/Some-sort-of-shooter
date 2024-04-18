package model.ingame.entity;

import model.ingame.EntitySpawner;
import model.ingame.GameModel;
import util.Coordinates;

public class FirstAidKitSpawner extends EntitySpawner{

    public FirstAidKitSpawner(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    public FirstAidKit spawnEntity(double x, double y) {
        FirstAidKit entity = (FirstAidKit) super.spawnEntity(x, y);
        gameModel.addEntity(entity);
        return entity;
    }

    @Override
    protected FirstAidKit makeEntity(double x, double y) {
        return new FirstAidKit(new Coordinates(x, y), gameModel);
    }
}
