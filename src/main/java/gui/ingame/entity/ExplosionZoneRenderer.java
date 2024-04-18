package gui.ingame.entity;

import gui.animations.Animation;
import gui.animations.AnimationCollection;
import gui.animations.AnimationManager;
import model.ingame.entity.EntityModel;

import java.awt.*;

public class ExplosionZoneRenderer extends AbstractEntityRenderer {
    private AnimationManager animationManager;
    public ExplosionZoneRenderer(EntityModel entityModel) {
        super(entityModel);
        Animation animation = new Animation(Animation.EndReachedBehaviour.INFINITE, "explosion");
        animation.addNextAssociation(10, "sprites/explosion/explosion1.png");
        animation.addNextAssociation(20, "sprites/explosion/explosion2.png");
        animation.addNextAssociation(30, "sprites/explosion/explosion3.png");
        AnimationCollection animationCollection = new AnimationCollection("explosion", getClass());
        animationCollection.put("explosion", animation);
        this.animationManager = new AnimationManager(animationCollection);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(animationManager.getCurrentImage().getScaledInstance(getWidth(), getHeight(), Image.SCALE_FAST), 0, 0, null);
    }

    @Override
    public void update() {
        super.update();
        animationManager.nextImage();
    }
}
