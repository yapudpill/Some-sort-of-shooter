package gui.ingame.entity;

import gui.animations.AnimationCache;
import gui.animations.AnimationGroup;
import gui.animations.AnimationManager;
import model.ingame.entity.EntityModel;

public class ExplosionZoneRenderer extends AnimatedEntityRenderer {
    private AnimationManager animationManager;
    public ExplosionZoneRenderer(EntityModel entityModel) {
        super(entityModel);
    }


    @Override
    protected AnimationGroup getAnimationGroup() {
        AnimationGroup animationGroup = new AnimationGroup("explosion", getClass());
        animationGroup.put("explosion", AnimationCache.loadAnimation("animations/explosion.xml", getClass()));
        return animationGroup;
    }
}
