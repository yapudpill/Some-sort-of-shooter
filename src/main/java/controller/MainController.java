package controller;

import gui.launcher.HomeMenu;
import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.level.MapModel;
import model.level.StandardTileModel;
import model.level.TileModel;
import model.level.WaterTileModel;
import gui.IngameGuiDemo;
import gui.MainFrame;
import gui.ingame.GameView;

import java.awt.*;

public class MainController {
    private final MainFrame mainFrame;
    private GameModel gameModel;
    private GameView gameView;
    private IGameLoop gameModelLoop;
    private IGameLoop gameViewLoop;

    public MainController() {
        mainFrame = new MainFrame();
        loadHomeMenu();
    }

    public void loadHomeMenu() {
        mainFrame.loadMenu(new HomeMenu(this));
    }

    public void loadGame(/* Add arguments if needed */) {
        if(gameModel == null) gameModel = makeGameModel();
        if(gameView == null) gameView = new GameView(gameModel);
        gameView.resetController();
        gameModel.getPlayer().getMovementHandler().setDirectionVector(new Coordinates(0, 0));
        gameModel.getPlayer().setPos(new Coordinates(2, 2));
        gameModelLoop = new ModelGameLoop(() -> update());
        gameViewLoop = new SwingGameLoop(gameView);
        gameModelLoop.start();
        gameViewLoop.start();
        mainFrame.loadMenu(gameView.getComponent());
        EventQueue.invokeLater(gameView::setFocusToMainArea);
    }

    public GameModel makeGameModel() {
        TileModel[][] tiles = new TileModel[5][10];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                tiles[i][j] = new StandardTileModel();
            }
        }
        tiles[3][2] = new WaterTileModel();
        tiles[3][3] = new WaterTileModel();
        tiles[3][4] = new WaterTileModel();
        tiles[3][5] = new WaterTileModel();
        tiles[3][6] = new WaterTileModel();


        MapModel mapModel = new MapModel(10, 5, tiles);
        GameModel gameModel = new GameModel(mapModel);
        return gameModel;
    }

    public void closeWindow() {
        mainFrame.dispose();
        System.exit(0);
    }

    public void update(){
        if(!gameModel.isRunning()) {
            gameModelLoop.stop();
            gameViewLoop.stop();
            gameModel.reset();
            loadHomeMenu();
            return;
        }
        gameModel.update();
    }

    public static void main(String[] args) {
        new MainController();
    }
}