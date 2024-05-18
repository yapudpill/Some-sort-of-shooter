package gui.animations;

import java.awt.image.BufferedImage;
import util.IUpdateable;
import util.ImageLoader;
import util.RegularImageLoader;

public class AnimationManager implements IUpdateable {
    private final AnimationGroup animationGroup;
    private final Class<?> resourceBase;
    private Animation currentAnimation;
    private AnimationCursor cursor;
    private ImageLoader imageLoader = new RegularImageLoader();

    public AnimationManager(AnimationGroup animationGroup, Class<?> resourceBase) {
        this.animationGroup = animationGroup;
        this.resourceBase = resourceBase;
        switchAnimation(animationGroup.getDefaultId());
    }

    public void switchAnimation(String id) {
        if (id == null) return;
        if (currentAnimation == null || !currentAnimation.getId().equals(id)) {
            currentAnimation = animationGroup.get(id);
            if (currentAnimation == null) {
                throw new IllegalArgumentException("Animation not found: " + id);
            }
            cursor = new AnimationCursor(currentAnimation);
        }
    }

    public void setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
    }

    public boolean imageLoaderIs(ImageLoader imageLoader) {
        return this.imageLoader== imageLoader;
    }

    public BufferedImage getCurrentImage() {
        return imageLoader.load(cursor.getCurrentValue(), resourceBase);
    }

    public String getCurrentAnimationId() {
        return currentAnimation.getId();
    }

    @Override
    public void update(double delta) {
        cursor.advance(delta);
        if (getCurrentImage() == null) { // Ended animation
            switchAnimation(animationGroup.getDefaultId());
        }
    }
}
