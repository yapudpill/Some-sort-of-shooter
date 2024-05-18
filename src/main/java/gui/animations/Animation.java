package gui.animations;

import gui.ImageCache;
import util.EndReachedBehaviour;
import util.IntervalMap;

/**
 * Represents an animation, i.e. a map from time intervals to image paths. The animation may loop, be infinite (i.e.
 * stays on the last image when the end is reached) or stop when the end is reached, depending on the endReachedBehaviour.
 */
public class Animation extends IntervalMap<Double, String> {
    private final String id;

    public Animation(EndReachedBehaviour endReachedBehaviour, String id) {
        super(0., (a, b) -> a % b, endReachedBehaviour);
        this.id = id;
    }

    public void preloadImages(Class<?> resourceBase) {
        for (String imageName : values()) {
            ImageCache.loadImage(imageName, resourceBase);
        }
    }

    public String getId() {
        return id;
    }
}
