package gui;

import model.ingame.Coordinates;

import java.awt.*;

/**
 * A layout manager that applies a uniform scaling/homothety to all ScalableComponents of a container, according to a
 * ScaleSupplier.
 */
public class ScaleLayout implements LayoutManager {
    private final ScaleSupplier scaleSupplier;

    public ScaleLayout(ScaleSupplier scaleSupplier) {
        this.scaleSupplier = scaleSupplier;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {

    }

    @Override
    public void removeLayoutComponent(Component comp) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return null;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return null;
    }

    @Override
    public void layoutContainer(Container parent) {
        for (Component component : parent.getComponents()) {
            if (component instanceof ScalableComponent scalableComponent) {
                Coordinates originalPosition = scalableComponent.getOriginalPosition();
                Coordinates originalSize = scalableComponent.getOriginalSize();
                int scale = scaleSupplier.getScale();
                int x = (int) (originalPosition.x * scale);
                int y = (int) (originalPosition.y * scale);
                int width = (int) (originalSize.x * scale);
                int height = (int) (originalSize.y * scale);
                component.setBounds(x, y, width, height);
            }
        }
    }
}
