package gui.ingame.entity;

import java.awt.Color;
import java.awt.Graphics;

import model.ingame.Coordinates;
import model.ingame.entity.WalkingEnemyModel;

public class WalkingEnemyRenderer extends AbstractEntityRenderer {
    public WalkingEnemyRenderer(WalkingEnemyModel entityModel) {
        super(entityModel);
    }

    @Override
    public Coordinates getOriginalSize() {
        return new Coordinates(entityModel.getWidth(), entityModel.getHeight());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
