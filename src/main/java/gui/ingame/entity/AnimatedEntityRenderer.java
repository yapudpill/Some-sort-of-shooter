package gui.ingame.entity;

import gui.animations.AnimationGroup;
import gui.animations.AnimationManager;
import model.ingame.entity.EntityModel;

import java.awt.*;

public abstract class AnimatedEntityRenderer extends AbstractEntityRenderer {
    private AnimationManager animationManager;

    public AnimatedEntityRenderer(EntityModel entityModel) {
        super(entityModel);
        this.animationManager = new AnimationManager(getAnimationGroup());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(animationManager.getCurrentImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST), 0, 0, null);
    }

    protected abstract AnimationGroup getAnimationGroup();

    @Override
    public void update() {
        super.update();
        animationManager.nextImage();
    }
}
