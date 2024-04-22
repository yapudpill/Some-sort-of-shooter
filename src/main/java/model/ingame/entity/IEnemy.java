package model.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.GameModel;

// Tag interface for enemies
public interface IEnemy extends IEntity {
    @FunctionalInterface
    interface IEnemyFactory extends IEntityFactory {
        @Override
        IEnemy make(Coordinates pos, GameModel gameModel);
    }
}
