package gui.ingame.entity;

import gui.animations.AnimationCache;
import gui.animations.AnimationGroup;
import gui.animations.AnimationManager;
import model.ingame.entity.IEntity;
import util.IUpdateable;

import java.awt.*;
import java.util.function.DoubleSupplier;

/**
 * Class for rendering entities with animations. Allows for the entity to be animated and rotated. The angle is provided
 * by a function passed to the constructor to allow quickly creating new renderers without extending the class, as a lot
 * of them are very similar.
 */
public class AnimatedEntityRenderer extends EntityRenderer implements IUpdateable {
    protected final AnimationManager animationManager;
    protected final DoubleSupplier angle;

    public AnimatedEntityRenderer(IEntity entityModel, String path, DoubleSupplier angle) {
        super(entityModel);
        AnimationGroup group = AnimationCache.loadAnimationGroup(path, getClass());
        this.animationManager = new AnimationManager(group, getClass());
        this.angle = angle;
    }

    public AnimatedEntityRenderer(IEntity entityModel, String path) {
        this(entityModel, path, () -> 0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g.create();
        double mid = (double) getWidth() / 2;
        g2D.rotate(angle.getAsDouble(), mid, mid);
        g2D.drawImage(animationManager.getCurrentImage(), 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    public void update(double delta) {
        animationManager.update(delta);
    }
}
