package gui.ingame.entity;

import gui.animations.AnimationGroup;
import gui.animations.AnimationManager;
import model.ingame.entity.EntityModel;
import util.IUpdateable;

import java.awt.*;

public abstract class AnimatedEntityRenderer extends AbstractEntityRenderer implements IUpdateable {
    protected AnimationManager animationManager;

    public AnimatedEntityRenderer(EntityModel entityModel) {
        super(entityModel);
        this.animationManager = new AnimationManager(getAnimationGroup());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        double mid = (double) getWidth() / 2;
        ((Graphics2D) g).rotate(getRotationAngle(), mid, mid);
        g.drawImage(animationManager.getCurrentImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST), 0, 0, null);
    }

    protected abstract AnimationGroup getAnimationGroup();
    protected double getRotationAngle() {
        return 0;
    }

    @Override
    public void update(double deltaT) {
        animationManager.nextImage(deltaT);
    }

    protected void switchToAnimation(String animationName) {
        animationManager.switchToAnimation(animationName);
    }
}
