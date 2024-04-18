package gui.animations;

import gui.ImageCache;
import util.TimeIntervalMappings;

public class Animation extends TimeIntervalMappings<String> {
    private final String animationId;

    public Animation(EndReachedBehaviour endReachedBehaviour, String animationId) {
        super(endReachedBehaviour);
        this.animationId = animationId;
    }

    public String getId() {
        return animationId;
    }

    public void loadImages(Class<?> resourceBase) {
        for (String imageName : this.values()) {
            ImageCache.loadImage(imageName, resourceBase);
        }
    }
}
