package controller;

import gui.ingame.GameView;
import model.ingame.Coordinates;
import model.ingame.GameModel;

public class GameController {
    final GameView gameView;

    public GameController(String mapName) {
        GameModel gameModel = new GameModel(mapName);
        // TODO: should be part of the map reading
        gameModel.getPlayer().setPos(new Coordinates(9.5, 9.5));

        gameView = new GameView(gameModel);
        IGameLoop gameModelLoop = new ModelGameLoop(gameModel);
        IGameLoop gameViewLoop = new SwingGameLoop(gameView);
        gameModelLoop.start();
        gameViewLoop.start();
    }
}
