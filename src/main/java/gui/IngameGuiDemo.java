package gui;

import controller.IGameLoop;
import controller.ModelGameLoop;
import controller.SwingGameLoop;
import gui.ingame.GameView;
import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.level.MapModel;
import model.level.TileModel;
import model.level.tiles.StandardTileModel;
import model.level.tiles.WaterTileModel;

public class IngameGuiDemo {

    public GameView gameView;

    // Create a frame, initialise it, and display it
    public IngameGuiDemo() {
        MapModel mapModel = new MapModel("maps/map3");
        GameModel gameModel = new GameModel(mapModel);
        gameModel.getPlayer().setPos(new Coordinates(9.5, 9.5));

        gameView = new GameView(gameModel);
        IGameLoop gameModelLoop = new ModelGameLoop(gameModel);
        IGameLoop gameViewLoop = new SwingGameLoop(gameView);
        gameModelLoop.start();
        gameViewLoop.start();
    }
}
