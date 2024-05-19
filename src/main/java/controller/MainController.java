package controller;

import gui.MainFrame;
import gui.editor.EditorMenu;
import gui.launcher.EndMenu;
import gui.launcher.HomeMenu;
import gui.launcher.LevelMenu;
import model.ingame.Statistics;
import model.level.InvalidMapException;
import model.level.scenario.InvalidScenarioException;
import util.Resource;

import java.awt.*;

/**
 * Controller for the main menu. It is responsible for loading the different menus and launch the game.
 */
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

    public void loadLevelMenu() {
        mainFrame.loadMenu(new LevelMenu(this));
    }

    public void loadGame(Resource mapResource, Resource scenarioResource) throws InvalidMapException, InvalidScenarioException {
        GameController gameController = new GameController(mapResource, scenarioResource, this);
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