package gui.animations;

import util.IntervalMapCursor;

public class AnimationCursor extends IntervalMapCursor<Double, String> {

    public AnimationCursor(Animation animation) {
        super(animation, Double::sum);
    }
}
