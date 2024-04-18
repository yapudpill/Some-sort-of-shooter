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
        return AnimationCache.loadAnimationGroup("animation_groups/explosion_zone.xml", getClass());
    }
}
