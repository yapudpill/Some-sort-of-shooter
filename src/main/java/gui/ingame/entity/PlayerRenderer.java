package gui.ingame.entity;

import gui.animations.AnimationCache;
import gui.animations.AnimationGroup;
import model.ingame.entity.IVulnerableEntity;
import model.ingame.entity.PlayerModel;

import java.awt.*;

public class PlayerRenderer extends AnimatedEntityRenderer {
    public PlayerRenderer(PlayerModel entityModel) {
        super(entityModel);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw triangle pointing upwards
        /*Image image;
        image = ImageCache.loadImage("sprites/player1/playerRightShoot.png", PlayerRenderer.class);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);*/
        // show health bar
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), 5);
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, (int) (getWidth() * ((double) ((IVulnerableEntity) entityModel).getHealth() / ((IVulnerableEntity) entityModel).getMaxHealth())), 5);
    }

    @Override
    protected AnimationGroup getAnimationGroup() {
        return AnimationCache.loadAnimationGroup("animation_groups/player1.xml", getClass());
    }
}
