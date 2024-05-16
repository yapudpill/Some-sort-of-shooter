package gui.ingame;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import gui.RatioLayout;
import model.ingame.GameModel;
import model.level.MapModel;
import util.IUpdateable;

/**
 * The main View for the game once it is launched. It contains the GameMainArea and the GameHUD. Intended to be the
 * main pane of the frame.
 * <p>
 * Also responsible for catching the player input to pass them to a controller
 */
public class GameView extends JPanel implements IUpdateable {
    private final GameMainArea mainArea;

    public GameView(GameModel gameModel) {
        setOpaque(true);
        setLayout(new BorderLayout());

        mainArea = new GameMainArea(gameModel);

        MapModel map = gameModel.getMapModel();
        RatioLayout ratioLayout = new RatioLayout((double) map.getWidth() / map.getHeight());
        JPanel mainAreaWrapper = new JPanel(ratioLayout);
        mainAreaWrapper.add(mainArea);
        add(mainAreaWrapper, BorderLayout.CENTER);
    }

    @Override
    public void update(double delta) {
        mainArea.update(delta);
    }

    public void setFocusToMainArea() {
        mainArea.requestFocus();
    }
}
