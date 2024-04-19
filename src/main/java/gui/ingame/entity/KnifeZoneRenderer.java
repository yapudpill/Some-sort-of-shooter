package gui.ingame.entity;

import gui.animations.AnimationCache;
import gui.animations.AnimationGroup;
import model.ingame.Coordinates;
import model.ingame.entity.KnifeZoneEntity;

public class KnifeZoneRenderer extends AnimatedEntityRenderer {
    public KnifeZoneRenderer(KnifeZoneEntity entityModel) {
        super(entityModel);
    }

    @Override
    public Coordinates getOriginalSize() {
        return new Coordinates(entityModel.getHeight(), entityModel.getWidth());
    }

    @Override
    protected AnimationGroup getAnimationGroup() {
        return AnimationCache.loadAnimationGroup("animation_groups/knife_zone.xml", getClass());
    }

    @Override
    protected double getRotationAngle() {
        return ((KnifeZoneEntity) entityModel).getDirection().getAngle();
    }
}
