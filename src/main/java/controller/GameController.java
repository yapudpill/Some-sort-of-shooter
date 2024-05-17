package controller;

import gui.ingame.GameView;
import model.ingame.GameModel;
import model.ingame.Statistics;
import model.level.InvalidMapException;
import model.level.MapModel;
import model.level.scenario.IScenario;
import model.level.scenario.InvalidScenarioException;
import model.level.scenario.MarathonScenario;
import model.level.scenario.ScenarioParser;
import util.Resource;

public class GameController {
    final GameView gameView;
    private final GameModel gameModel;
    private final GameLoop modelLoop, viewLoop;
    private final MainController mainController;

    public GameController(Resource mapResource, Resource scenarioResource, MainController mainController) throws InvalidMapException, InvalidScenarioException {
        this.mainController = mainController;
        MapModel mapModel = new MapModel(mapResource);
        IScenario scenario;
        if (scenarioResource == null) {
            scenario = new MarathonScenario();

        } else {
            scenario = ScenarioParser.loadScenario(scenarioResource.toStream());
        }

        gameModel = new GameModel(mapModel, new Statistics(mapResource, scenarioResource), scenario);
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
