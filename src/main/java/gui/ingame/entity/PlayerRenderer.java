package gui.ingame.entity;

import gui.ImageCache;
import model.ingame.Coordinates;
import model.ingame.entity.PlayerModel;

import java.awt.*;
import java.io.IOException;

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
        Image image;
        try {
            image = ImageCache.loadImage("sprites/player1/playerRightShoot.png", PlayerRenderer.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }
}
