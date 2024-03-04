package controller;

import java.awt.EventQueue;

import gui.IngameGuiDemo;
import gui.MainFrame;
import gui.launcher.HomeMenu;

public class MainController {
    private final MainFrame mainFrame;

    public MainController() {
        mainFrame = new MainFrame();
        loadHomeMenu();
    }

    public void loadHomeMenu() {
        mainFrame.loadMenu(new HomeMenu(this));
    }

    public void loadGame(/* Add arguments if needed */) {
        IngameGuiDemo demo = new IngameGuiDemo();
        mainFrame.loadMenu(demo.gameView.getComponent());
        EventQueue.invokeLater(demo.gameView::setFocusToMainArea);
    }

    public void closeWindow() {
        mainFrame.dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        new MainController();
    }
}