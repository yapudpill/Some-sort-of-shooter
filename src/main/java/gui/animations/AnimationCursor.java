package gui.animations;

import util.IntervalMapCursor;

class AnimationCursor extends IntervalMapCursor<Double, String> {

    AnimationCursor(Animation animation) {
        super(animation, Double::sum);
    }
}
