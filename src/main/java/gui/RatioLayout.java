package gui;

import java.awt.*;

/**
 * A layout manager that can center the components it manages and make them fill the parent as much as possible, while
 * keeping a given width/height ratio.
 * <p>
 * It is designed to be used with one child. Using it with multiple children will make them stack on each other.
 */
public class RatioLayout implements LayoutManager {
    private final double widthHeightRatio;

    public RatioLayout(double widthHeightRatio) {
        this.widthHeightRatio = widthHeightRatio;
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return new Dimension(0, 0);
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(0, 0);
    }

    @Override
    public void layoutContainer(Container parent) {
        for (Component component : parent.getComponents()) {
                double parentRatio = (double) parent.getWidth() / parent.getHeight();
                if (parentRatio > widthHeightRatio) {
                    int width = (int) (parent.getHeight() * widthHeightRatio);
                    int x = (parent.getWidth() - width) / 2;
                    component.setBounds(x, 0, width, parent.getHeight());
                } else {
                    int height = (int) (parent.getWidth() / widthHeightRatio);
                    int y = (parent.getHeight() - height) / 2;
                    component.setBounds(0, y, parent.getWidth(), height);
                }
        }
    }
}