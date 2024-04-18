package gui.animations;

import gui.ImageCache;
import util.TimeIntervalMappingsCursor;

import java.awt.image.BufferedImage;

public class AnimationManager {
    private final AnimationCollection animationCollection;

    private TimeIntervalMappingsCursor<String> cursor = null;

    public AnimationManager(AnimationCollection animationCollection) {
        this.animationCollection = animationCollection;
        loadAnimation(animationCollection.getDefaultAnimationId());
    }

    public void preloadAnimations() {
        animationCollection.preloadAnimations();
    }

    public String getCurrentImageName() {
        return cursor.getCurrentValue();
    }

    public BufferedImage getCurrentImage() {
        return ImageCache.loadImage(getCurrentImageName(), animationCollection.getResourceBase());
    }

    public void loadAnimation(String animationName) {
        Animation animation = animationCollection.get(animationName);
        if (animation == null) throw new IllegalArgumentException("Animation not found: " + animationName);
        cursor = new TimeIntervalMappingsCursor<>(animation);
    }

    public BufferedImage nextImage() {
        cursor.advanceTime();
        if (getCurrentImage() == null) {// Ended animation
            loadAnimation(animationCollection.getDefaultAnimationId());
            return nextImage();
        }
        return getCurrentImage();
    }
}
