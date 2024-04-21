package gui.animations;

import gui.ImageCache;
import util.EndReachedBehaviour;
import util.IntervalMap;

class Animation extends IntervalMap<Double, String> {
    private final String id;

    Animation(EndReachedBehaviour endReachedBehaviour, String id) {
        super(0., (a, b) -> a % b, endReachedBehaviour);
        this.id = id;
    }

    void preloadImages(Class<?> resourceBase) {
        for (String imageName : values()) {
            ImageCache.loadImage(imageName, resourceBase);
        }
    }

    String getId() {
        return id;
    }
}
