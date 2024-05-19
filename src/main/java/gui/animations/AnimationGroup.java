package gui.animations;

import java.util.HashMap;

/**
 * Represents a group of animations, i.e. a map from animation ids to Animations. The group has a default id, which is the
 * animation that should start playing first.
 */
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
