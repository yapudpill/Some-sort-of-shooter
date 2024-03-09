package gui.ingame;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;

import controller.PlayerController;
import gui.RatioLayout;
import model.ingame.GameModel;
import model.ingame.IUpdateable;

/**
 * The main View for the game once it is launched. It contains the GameMainArea and the GameHUD. Intended to be the
 * main pane of the frame.
 * <p>
 * Also responsible for catching the player input to pass them to a controller
 */
public class GameView implements IUpdateable {
    private final JPanel rootPane;
    private final GameModel gameModel;
    private final GameMainArea gameMainArea;

    private final JPanel mainAreaWrapper = new JPanel();
    private final GameHUD gameHUD;

    private final PlayerController player1SwingController;

    public GameView(GameModel gameModel) {
        this.gameModel = gameModel;
        this.gameMainArea = new GameMainArea(gameModel);
        this.gameHUD = new GameHUD();
        this.rootPane = new JPanel();
        this.player1SwingController = new PlayerController(gameModel.getPlayer(), gameMainArea);
        rootPane.setOpaque(true);


        // TODO: setup gridbaglayout to have HUD on both sides of the gameRenderer (Player 1 & Player 2)
        rootPane.setLayout(new BorderLayout());

        gameMainArea.getJComponent().addKeyListener(player1SwingController.getKeyListener());
        gameMainArea.getJComponent().addMouseListener(player1SwingController.getMouseListener());

        RatioLayout centerFillRatioLayout = new RatioLayout();
        mainAreaWrapper.setLayout(centerFillRatioLayout);
        centerFillRatioLayout.setWidthHeightRatio((double) gameModel.getMapModel().getWidth() / gameModel.getMapModel().getHeight());
        mainAreaWrapper.add(gameMainArea.getJComponent());

        rootPane.add(mainAreaWrapper, BorderLayout.CENTER);

        gameMainArea.getJComponent().setFocusable(true);
    }

    public JPanel getComponent() {
        return rootPane;
    }

    @Override
    public void update() {
        gameHUD.update();
        gameMainArea.update();
    }

    public void setFocusToMainArea() {
        gameMainArea.getJComponent().requestFocus();
    }
}
