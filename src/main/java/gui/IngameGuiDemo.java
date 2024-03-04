package gui;

import controller.IGameLoop;
import controller.ModelGameLoop;
import controller.SwingGameLoop;
import gui.ingame.GameView;
import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.level.MapModel;
import model.level.Tiles.StandardTileModel;

public class IngameGuiDemo {

    public GameView gameView;

    // Create a frame, initialise it, and display it
    public IngameGuiDemo() {
        /* TileModel[][] tiles = new TileModel[5][10];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                tiles[i][j] = new StandardTileModel();
            }
        }
        tiles[3][3] = new WaterTileModel();
        */
        MapModel mapModel = new MapModel("mapTest");
        GameModel gameModel = new GameModel(mapModel);
        gameModel.getPlayer().setPos(new Coordinates(2, 2));

        gameView = new GameView(gameModel);
        IGameLoop gameModelLoop = new ModelGameLoop(gameModel);
        IGameLoop gameViewLoop = new SwingGameLoop(gameView);
        gameModelLoop.start();
        gameViewLoop.start();
    }
}
