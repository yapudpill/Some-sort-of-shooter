package model.ingame;

import java.util.List;

import model.ingame.entity.EnemyModel;
import model.ingame.entity.PlayerModel;
import model.level.MapModel;

public class GameModel {
    private GameState state;
    private List<EnemyModel> enemies;
    private List<PlayerModel> players;
    private MapModel map;

    public GameModel(GameState state, List<EnemyModel> enemies, List<PlayerModel> players, MapModel map) {
        this.state = state;
        this.enemies = enemies;
        this.players = players;
        this.map = map;
    }

    public GameState getState() {
        return state;
    }
    
    public void setState(GameState state) {
        this.state = state;
    }

}
