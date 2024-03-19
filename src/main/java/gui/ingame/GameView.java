package gui.ingame;

import java.awt.BorderLayout;

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
public class GameView extends JPanel implements IUpdateable {
    private final GameMainArea mainArea;

    public GameView(GameModel gameModel) {
        mainArea = new GameMainArea(gameModel);
        setOpaque(true);

        // TODO: setup gridbaglayout to have HUD on the side of the gameRenderer
        setLayout(new BorderLayout());

        PlayerController playerController = new PlayerController(gameModel.getPlayer(), mainArea);
        mainArea.addKeyListener(playerController.getKeyListener());
        mainArea.addMouseListener(playerController.getMouseListener());

        RatioLayout centerFillRatioLayout = new RatioLayout((double) gameModel.getMapModel().getWidth() / gameModel.getMapModel().getHeight());
        JPanel mainAreaWrapper = new JPanel(centerFillRatioLayout);
        mainAreaWrapper.add(mainArea);
        add(mainAreaWrapper, BorderLayout.CENTER);

        mainArea.setFocusable(true);
    }

    @Override
    public void update() {
        mainArea.update();
    }

    public void setFocusToMainArea() {
        mainArea.requestFocus();
    }
}
