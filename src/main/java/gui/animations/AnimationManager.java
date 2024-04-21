package gui.animations;

import gui.ImageCache;
import util.IUpdateable;

import java.awt.image.BufferedImage;

public class AnimationManager implements IUpdateable {
    private final AnimationGroup animationGroup;
    private final Class<?> resourceBase;
    private Animation currentAnimation;
    private AnimationCursor cursor;

    public AnimationManager(AnimationGroup animationGroup, Class<?> resourceBase) {
        this.animationGroup = animationGroup;
        this.resourceBase = resourceBase;
        switchAnimation(animationGroup.getDefaultId());
    }

    public void switchAnimation(String id) {
        if (currentAnimation == null || !currentAnimation.getId().equals(id)) {
            currentAnimation = animationGroup.get(id);
            if (currentAnimation == null) {
                throw new IllegalArgumentException("Animation not found: " + id);
            }
            cursor = new AnimationCursor(currentAnimation);
        }
    }

    public BufferedImage getCurrentImage() {
        return ImageCache.loadImage(cursor.getCurrentValue(), resourceBase);
    }

    public String getCurrentAnimationId() {
        return currentAnimation.getId();
    }

    @Override
    public void update(double delta) {
        cursor.advance(delta);
        if (getCurrentImage() == null) { // Ended animation
            switchAnimation(animationGroup.getDefaultId());
        }
    }
}
