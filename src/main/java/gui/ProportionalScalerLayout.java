package gui;

import model.ingame.Coordinates;

import java.awt.*;

public class ProportionalScalerLayout implements LayoutManager {
    private final ScaleSupplier scaleSupplier;

    public ProportionalScalerLayout(ScaleSupplier scaleSupplier) {
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
