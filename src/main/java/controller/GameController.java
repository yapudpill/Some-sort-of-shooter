package controller;

import gui.ingame.GameView;
import model.ingame.GameModel;
import model.ingame.Statistics;
import model.level.InvalidMapException;
import model.level.MapModel;
import model.level.scenario.Scenario;
import util.Resource;

public class GameController {
    final GameView gameView;
    private final GameModel gameModel;
    private final GameLoop modelLoop, viewLoop;
    private final MainController mainController;

    public GameController(Resource mapResource, MainController mainController) throws InvalidMapException {
        this.mainController = mainController;

        gameModel = new GameModel(new MapModel(mapResource), new Statistics(mapResource), Scenario.loadScenario(null));
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
