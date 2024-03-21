package controller;

import gui.ingame.GameView;
import model.ingame.Coordinates;
import model.ingame.GameModel;

public class GameController {
    final GameView gameView;
    private GameModel gameModel;
    private IGameLoop gameModelLoop;
    private IGameLoop gameViewLoop;
    private MainController mainController;

    public GameController(String mapName, MainController mainController) {
        GameModel gameModel = new GameModel(mapName);
        // TODO: should be part of the map reading
        gameModel.getPlayer().setPos(new Coordinates(9.5, 9.5));

        gameView = new GameView(gameModel);
        IGameLoop gameModelLoop = new ModelGameLoop(gameModel);
        IGameLoop gameViewLoop = new SwingGameLoop(gameView);
        IGameLoop loop = new SwingGameLoop(() -> update());
        this.gameModel = gameModel;
        this.gameModelLoop = gameModelLoop;
        this.gameViewLoop = gameViewLoop;
        this.mainController = mainController;

        gameModelLoop.start();
        gameViewLoop.start();
        loop.start();
    }

    public void update(){
        if(!gameModel.isRunning()) {
            gameModelLoop.stop();
            gameViewLoop.stop();
            gameModel.reset();
            gameView.resetController();
            mainController.loadHomeMenu();
            return;
        }
    }

}
