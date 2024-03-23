package controller;


import java.awt.EventQueue;

import gui.launcher.HomeMenu;
import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.level.MapModel;
import model.level.tiles.StandardTileModel;
import model.level.TileModel;
import model.level.tiles.WaterTileModel;
import gui.MainFrame;
import gui.ingame.GameView;

import gui.MainFrame;
import gui.launcher.HomeMenu;
import gui.launcher.MapMenu;

public class MainController {
    private final MainFrame mainFrame;
    private GameModel gameModel;
    private GameView gameView;


    public MainController() {
        mainFrame = new MainFrame();
        loadHomeMenu();
    }

    public void loadHomeMenu() {
        mainFrame.loadMenu(new HomeMenu(this));
    }

    public void loadMapMenu() {
        mainFrame.loadMenu(new MapMenu(this));
    }

    public void loadGame(String mapName) {
        GameController gameController = new GameController(mapName, this);
        mainFrame.loadMenu(gameController.gameView);
        EventQueue.invokeLater(gameController.gameView::setFocusToMainArea);
    }

    public void closeWindow() {
        mainFrame.dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        new MainController();
    }
}