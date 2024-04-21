package gui.animations;

import java.util.HashMap;

public class AnimationGroup extends HashMap<String, Animation> {
    private final String defaultId;

    AnimationGroup(String defaultId) {
        this.defaultId = defaultId;
    }

    void preloadAnimations(Class<?> resourceBase) {
        for (Animation animation : values()) {
            animation.preloadImages(resourceBase);
        }
    }

    String getDefaultId() {
        return defaultId;
    }
}
