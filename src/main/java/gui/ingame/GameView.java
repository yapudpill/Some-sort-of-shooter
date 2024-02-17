package gui.ingame;

import controller.PlayerSwingController;
import model.ingame.GameModel;
import model.ingame.IUpdateable;

import javax.swing.*;
import java.awt.*;

public class GameView implements IUpdateable {
    private final JPanel rootPane;
    private final GameModel gameModel;
    private final GameRenderer gameRenderer;
    private final GameHUD gameHUD;

    private final PlayerSwingController player1SwingController;

    public GameView(GameModel gameModel) {
        this.gameModel = gameModel;
        this.gameRenderer = new GameRenderer(gameModel);
        this.gameHUD = new GameHUD();
        this.rootPane = new JPanel();
        this.player1SwingController = new PlayerSwingController(gameModel.getPlayer());
        rootPane.setOpaque(true);


        // TODO: setup gridbaglayout to have HUD on both sides of the gameRenderer (Player 1 & Player 2)
        rootPane.setLayout(new BorderLayout());

        rootPane.add(gameRenderer.getJComponent());
        rootPane.addKeyListener(player1SwingController.getKeyListener());
        rootPane.addMouseListener(player1SwingController.getMouseListener());

        rootPane.setFocusable(true);
    }

    public Component getComponent() {
        return rootPane;
    }

    @Override
    public void update() {
        gameHUD.update();
        gameRenderer.update();
    }
}
