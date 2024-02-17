package gui;

import java.awt.*;

public class SquareGridLayout implements LayoutManager {
    private final int squareCountH;

    private final ScaleSupplier squareWidthSupplier;


    public SquareGridLayout(int squareCountH, ScaleSupplier squareWidthSupplier) {
        this.squareWidthSupplier = squareWidthSupplier;
        this.squareCountH = squareCountH;
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
        int oneSquareWidth = squareWidthSupplier.getScale();
        Component[] components = parent.getComponents();
        for (int i = 0; i < components.length; i++) {
            int x = i % squareCountH;
            int y = i / squareCountH;
            components[i].setBounds(x * oneSquareWidth, y * oneSquareWidth, oneSquareWidth, oneSquareWidth);
        }
    }
}
