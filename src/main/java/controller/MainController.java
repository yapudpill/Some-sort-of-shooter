package controller;

import gui.launcher.HomeMenu;
import gui.MainFrame;

public class MainController {
    private final MainFrame mainFrame;

    public MainController() {
        mainFrame = new MainFrame();
        loadHomeMenu();
    }

    public void loadHomeMenu() {
        mainFrame.loadMenu(new HomeMenu(this));
    }

    public void closeWindow() {
        mainFrame.dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        new MainController();
    }
}