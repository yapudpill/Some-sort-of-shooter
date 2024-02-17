package gui;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class CenterFillRatioLayout implements LayoutManager {
    private double widthHeightRatio;
    private final Set<Component> centeredComponents = new HashSet<>();

    public void setComponentCentering(Component component, boolean centered) {
        if (centered) centeredComponents.add(component);
        else centeredComponents.remove(component);
    }

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
            if (centeredComponents.contains(component)) {
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
            } else {
                component.setBounds(0, 0, parent.getWidth(), parent.getHeight());
            }
        }
    }
}