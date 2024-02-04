package main.java.model.ingame;

public class GameModel {
    private GameState state;


    public GameState getState() {
        return state;
    }
    
    public void setState(GameState state) {
        this.state = state;
    }

}
