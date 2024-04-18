package gui.animations;

import java.util.HashMap;

public class AnimationGroup extends HashMap<String, Animation> {
    private final String defaultAnimationId;
    private final Class<?> resourceBase;

    public AnimationGroup(String defaultAnimationId, Class<?> resourceBase) {
        super();
        this.defaultAnimationId = defaultAnimationId;
        this.resourceBase = resourceBase;
    }

    public void preloadAnimations() {
        for (Animation animation : this.values()) {
            animation.loadImages(resourceBase);
        }
    }

    public String getDefaultAnimationId() {
        return defaultAnimationId;
    }

    public Class<?> getResourceBase() {
        return resourceBase;
    }
}
