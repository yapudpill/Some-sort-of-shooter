package gui.animations;

import java.util.HashMap;

public class AnimationGroup extends HashMap<String, Animation> {
    private final String defaultId;

    public AnimationGroup(String defaultId) {
        this.defaultId = defaultId;
    }

    public void preloadAnimations(Class<?> resourceBase) {
        for (Animation animation : values()) {
            animation.preloadImages(resourceBase);
        }
    }

    public String getDefaultId() {
        return defaultId;
    }
}
