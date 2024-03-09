package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * A layout manager that can center the components it manages and make them fill the parent as much as possible, while
 * keeping a given width/height ratio.
 * <p>
 * It makes the components overlap, thus it is intended to be used with layers.
 */
public class RatioLayout implements LayoutManager {
    private double widthHeightRatio;
    /**
     * Sets the width/height ratio that the components should keep.
     * @param widthHeightRatio the width/height ratio that the components should keep
     */
    public void setWidthHeightRatio(double widthHeightRatio) {
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