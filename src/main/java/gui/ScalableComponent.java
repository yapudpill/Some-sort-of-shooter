package gui;

import model.ingame.Coordinates;

/**
 * A component that can be scaled
 * It has an original position and size, that will be used to compute the displayed position and size
 * @see ScaleLayout
 */
public interface ScalableComponent {
    Coordinates getOriginalPosition();
    Coordinates getOriginalSize();
}
