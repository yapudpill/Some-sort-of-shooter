package gui;

import java.awt.*;
import java.util.function.Supplier;

public class SquareGridLayout implements LayoutManager {
    private final int squareCountH;

    private final Supplier<Integer> squareWidthSupplier;


    public SquareGridLayout(int squareCountH, Supplier<Integer> squareWidthSupplier) {
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
        int oneSquareWidth = squareWidthSupplier.get();
        Component[] components = parent.getComponents();
        for (int i = 0; i < components.length; i++) {
            int x = i % squareCountH;
            int y = i / squareCountH;
            components[i].setBounds(x * oneSquareWidth, y * oneSquareWidth, oneSquareWidth, oneSquareWidth);
        }
    }
}
