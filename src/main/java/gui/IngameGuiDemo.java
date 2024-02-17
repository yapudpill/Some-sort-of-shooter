package gui;

import javax.swing.*;

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
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(IngameGuiDemo::createAndShowGUI);
    }

    // Create a frame, initialise it, and display it
    public static void createAndShowGUI() {
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        TileModel[][] tiles = new TileModel[5][10];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                tiles[i][j] = new StandardTileModel();
            }
        }

        tiles[3][3] = new WaterTileModel();
        MapModel mapModel = new MapModel(10, 5, tiles);
        GameModel gameModel = new GameModel(mapModel);
        gameModel.getPlayer().setPos(new Coordinates(3, 3));

        IGameLoop gameModelLoop = new SwingGameLoop(gameModel);
        GameView gameView = new GameView(gameModel);
        IGameLoop gameViewLoop = new SwingGameLoop(gameView);
        frame.getContentPane().add(gameView.getComponent());

        frame.pack();
        frame.setVisible(true);
        frame.setSize(800, 800);
        frame.setLocationRelativeTo(null);
        gameModelLoop.start();
        gameViewLoop.start();
    }
}
