package gui.animations;

import gui.ImageCache;
import util.EndReachedBehaviour;
import util.IntervalMap;

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
