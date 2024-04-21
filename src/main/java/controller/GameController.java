package controller;

import gui.ingame.GameView;
import model.ingame.GameModel;
import model.level.InvalidMapException;
import util.Resource;

class GameController {
    final GameView gameView;
    private final GameModel gameModel;
    private final GameLoop modelLoop, viewLoop;
    private final MainController mainController;

    GameController(Resource map, MainController mainController) throws InvalidMapException {
        this.mainController = mainController;

        gameModel = new GameModel(map);
        gameView = new GameView(gameModel);

        modelLoop = new ModelGameLoop(this::updateModel);
        viewLoop = new SwingGameLoop(gameView);
        modelLoop.start();
        viewLoop.start();
    }

    private void updateModel(double delta) {
        if (gameModel.isRunning()) {
            gameModel.update(delta);
        } else {
            modelLoop.stop();
            viewLoop.stop();
            mainController.loadEndMenu(gameModel.stats);
        }
    }
}
