package gui.animations;

import util.IntervalMapCursor;

/**
 * Cursor for animations. It is used to easily iterate over the frames of an animation.
 */
public class AnimationCursor extends IntervalMapCursor<Double, String> {

    public AnimationCursor(Animation animation) {
        super(animation, Double::sum);
    }
}
