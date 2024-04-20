package gui.animations;

import gui.ImageCache;
import util.TimeIntervalMappingsCursor;

import java.awt.image.BufferedImage;

public class AnimationManager {
    private final AnimationGroup animationGroup;
    private Animation currentAnimation;

    private TimeIntervalMappingsCursor<String> cursor = null;

    public AnimationManager(AnimationGroup animationGroup) {
        this.animationGroup = animationGroup;
        switchToAnimation(animationGroup.getDefaultAnimationId());
    }

    public void preloadAnimations() {
        animationGroup.preloadAnimations();
    }

    private String getCurrentImageName() {
        return cursor.getCurrentValue();
    }

    public BufferedImage getCurrentImage() {
        return ImageCache.loadImage(getCurrentImageName(), animationGroup.getResourceBase());
    }

    public void switchToAnimation(String animationName) {
        if (currentAnimation == null || !currentAnimation.getId().equals(animationName)) {
            currentAnimation = animationGroup.get(animationName);
            if (currentAnimation == null) throw new IllegalArgumentException("Animation not found: " + animationName);
            cursor = new TimeIntervalMappingsCursor<>(currentAnimation);
        }
    }

    public String getCurrentAnimationId() {
        return currentAnimation.getId();
    }

    public BufferedImage nextImage(double deltaT) {
        cursor.advanceTime(deltaT);
        if (getCurrentImage() == null) {// Ended animation
            switchToAnimation(animationGroup.getDefaultAnimationId());
            return nextImage(0);
        }
        return getCurrentImage();
    }
}
