package gui;

import gui.ingame.GameView;
import gui.ingame.SwingGameLoop;
import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.IGameLoop;
import model.level.MapModel;
import model.level.StandardTileModel;
import model.level.TileModel;
import model.level.WaterTileModel;

public class IngameGuiDemo {

    public GameView gameView;

    // Create a frame, initialise it, and display it
    public IngameGuiDemo() {

        TileModel[][] tiles = new TileModel[5][10];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                tiles[i][j] = new StandardTileModel();
            }
        }
        tiles[3][3] = new WaterTileModel();

        MapModel mapModel = new MapModel(10, 5, tiles);
        GameModel gameModel = new GameModel(mapModel);
        gameModel.getPlayer().setPos(new Coordinates(2, 2));
        gameModel.spawnWalking(7.5, 3.5);

        gameView = new GameView(gameModel);
        IGameLoop gameModelLoop = new SwingGameLoop(gameModel);
        IGameLoop gameViewLoop = new SwingGameLoop(gameView);
        gameModelLoop.start();
        gameViewLoop.start();
    }
}
