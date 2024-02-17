package gui;

import model.ingame.Coordinates;

public interface ScalableComponent {
    Coordinates getOriginalPosition();
    Coordinates getOriginalSize();
}
