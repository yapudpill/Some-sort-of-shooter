package gui.ingame.entity;

import model.ingame.entity.SimpleTrap;

import java.awt.*;

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
