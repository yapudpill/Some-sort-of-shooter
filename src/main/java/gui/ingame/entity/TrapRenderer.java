package gui.ingame.entity;

import java.awt.Color;
import java.awt.Graphics;

import model.ingame.entity.SimpleTrap;

public class TrapRenderer extends AbstractEntityRenderer {
    public TrapRenderer(SimpleTrap entityModel) {
        super(entityModel);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
