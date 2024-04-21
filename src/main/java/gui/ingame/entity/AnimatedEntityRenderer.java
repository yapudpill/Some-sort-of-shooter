package gui.ingame.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.function.DoubleSupplier;

import gui.animations.AnimationCache;
import gui.animations.AnimationGroup;
import gui.animations.AnimationManager;
import model.ingame.entity.EntityModel;
import util.IUpdateable;

public class AnimatedEntityRenderer extends AbstractEntityRenderer implements IUpdateable {
    protected final AnimationManager animationManager;
    protected final DoubleSupplier angle;

    public AnimatedEntityRenderer(EntityModel entityModel, String path, DoubleSupplier angle) {
        super(entityModel);
        AnimationGroup group = AnimationCache.loadAnimationGroup(path, getClass());
        this.animationManager = new AnimationManager(group, entityModel.getClass());
        this.angle = angle;
    }

    public AnimatedEntityRenderer(EntityModel entityModel, String path) {
        this(entityModel, path, () -> 0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g.create();
        double mid = (double) getWidth() / 2;
        g2D.rotate(angle.getAsDouble(), mid, mid);
        g.drawImage(animationManager.getCurrentImage(), 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    public void update(double delta) {
        animationManager.update(delta);
    }
}
