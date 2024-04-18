package gui.animations;

import gui.ImageCache;
import util.TimeIntervalMappingsCursor;

import java.awt.image.BufferedImage;

public class AnimationManager {
    private final AnimationGroup animationGroup;

    private TimeIntervalMappingsCursor<String> cursor = null;

    public AnimationManager(AnimationGroup animationGroup) {
        this.animationGroup = animationGroup;
        loadAnimation(animationGroup.getDefaultAnimationId());
    }

    public void preloadAnimations() {
        animationGroup.preloadAnimations();
    }

    public String getCurrentImageName() {
        return cursor.getCurrentValue();
    }

    public BufferedImage getCurrentImage() {
        return ImageCache.loadImage(getCurrentImageName(), animationGroup.getResourceBase());
    }

    public void loadAnimation(String animationName) {
        Animation animation = animationGroup.get(animationName);
        if (animation == null) throw new IllegalArgumentException("Animation not found: " + animationName);
        cursor = new TimeIntervalMappingsCursor<>(animation);
    }

    public BufferedImage nextImage() {
        cursor.advanceTime();
        if (getCurrentImage() == null) {// Ended animation
            loadAnimation(animationGroup.getDefaultAnimationId());
            return nextImage();
        }
        return getCurrentImage();
    }
}
