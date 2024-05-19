package gui;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * A layout that fills the parent container with a single component. Used for stacking up layers in a parent LayeredPane,
 * the other layouts would have made them stay next to each other instead of on top of each other.
 */
public class FillLayout implements LayoutManager {
    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return parent.getSize();
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return new Dimension(0, 0);
    }

    @Override
    public void layoutContainer(Container parent) {
        for (Component component : parent.getComponents()) {
            component.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        }
    }
}