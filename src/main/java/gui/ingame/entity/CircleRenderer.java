package gui.ingame.entity;

import java.awt.Color;
import java.awt.Graphics;

import model.ingame.entity.IEntity;

public class CircleRenderer extends AbstractEntityRenderer {
    private final Color color;

    public CircleRenderer(IEntity entity, Color color) {
        super(entity);
        this.color = color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(color);
        g.fillOval(0, 0, getWidth(), getHeight());
    }
}
