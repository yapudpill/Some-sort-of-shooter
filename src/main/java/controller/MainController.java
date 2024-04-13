package controller;

import java.awt.EventQueue;

import gui.MainFrame;
import gui.editor.EditorMenu;
import gui.launcher.EndMenu;
import gui.launcher.HomeMenu;
import gui.launcher.MapMenu;
import model.ingame.Statistics;
import model.level.InvalidMapException;
import util.Resource;

public class MainController {
    private final MainFrame mainFrame;

    public MainController() {
        mainFrame = new MainFrame();
        loadHomeMenu();
    }

    public void loadHomeMenu() {
        mainFrame.loadMenu(new HomeMenu(this));
    }

    public void loadEditor() {
        mainFrame.loadMenu(new EditorMenu(this));
    }

    public void loadMapMenu() {
        mainFrame.loadMenu(new MapMenu(this));
    }

    public void loadGame(Resource map) throws InvalidMapException {
        GameController gameController = new GameController(map, this);
        mainFrame.loadMenu(gameController.gameView);
        EventQueue.invokeLater(gameController.gameView::setFocusToMainArea);
    }

    public void loadEndMenu(Statistics stats) {
        mainFrame.loadMenu(new EndMenu(this, stats));
    }

    public void closeWindow() {
        mainFrame.dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        new MainController();
    }
}