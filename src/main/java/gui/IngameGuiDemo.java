package gui;

import javax.swing.*;
import gui.ingame.GameRenderer;
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
        GameRenderer gameRenderer = new GameRenderer(new MapModel(10, 5, tiles));
        frame.getContentPane().add(gameRenderer.getJComponent());


        frame.pack();
        frame.setVisible(true);
    }
}
