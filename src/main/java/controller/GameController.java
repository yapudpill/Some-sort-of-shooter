package controller;

import gui.ingame.GameView;
import model.ingame.Coordinates;
import model.ingame.GameModel;

public class GameController {
    final GameView gameView;
    private final GameModel gameModel;
    private final IGameLoop modelLoop, viewLoop;
    private final MainController mainController;

    public GameController(String mapName, MainController mainController) {
        this.mainController = mainController;
        gameModel = new GameModel(mapName);
        // TODO: should be part of the map reading
        gameModel.getPlayer().setPos(new Coordinates(9.5, 9.5));

        gameView = new GameView(gameModel);
        modelLoop = new ModelGameLoop(this::updateModel);
        viewLoop = new SwingGameLoop(gameView);
        modelLoop.start();
        viewLoop.start();
    }

    private void updateModel() {
        if (gameModel.isRunning()) {
            gameModel.update();
        } else {
            modelLoop.stop();
            viewLoop.stop();
            mainController.loadEndMenu(gameModel.stats);
        }
    }
}
