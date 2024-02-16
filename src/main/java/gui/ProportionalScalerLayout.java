package gui;

import java.awt.*;
import java.util.function.Supplier;

public class ProportionalScalerLayout implements LayoutManager {
    private final Supplier<Integer> scaleSupplier;

    public ProportionalScalerLayout(Supplier<Integer> scaleSupplier) {
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
                Point originalPosition = scalableComponent.getOriginalPosition();
                Point originalSize = scalableComponent.getOriginalSize();
                int scale = scaleSupplier.get();
                int x = originalPosition.x * scale;
                int y = originalPosition.y * scale;
                int width = originalSize.x * scale;
                int height = originalSize.y * scale;
                component.setBounds(x, y, width, height);
            }
        }
    }
}
