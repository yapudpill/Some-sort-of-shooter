package gui.ingame.entity;

import model.ingame.Coordinates;
import model.ingame.entity.PlayerModel;

import java.awt.*;

public class PlayerRenderer extends AbstractEntityRenderer {
    public PlayerRenderer(PlayerModel entityModel) {
        super(entityModel);
    }

    @Override
    public Coordinates getOriginalSize() {
        return new Coordinates(entityModel.getWidth(), entityModel.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw triangle pointing upwards
        int[] xPoints = {0, getWidth() / 2, getWidth()};
        int[] yPoints = {getHeight(), 0, getHeight()};
        g.setColor(Color.RED);
        g.fillPolygon(xPoints, yPoints, 3);
    }
}
