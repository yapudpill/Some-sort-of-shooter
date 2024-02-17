package gui;

import java.awt.*;

/**
 * A layout manager that arranges the components in a grid, with each cell being a square.
 * The number of squares in a row is given by the constructor, and the size of each square is given by a ScaleSupplier.
 * The components are laid out in the order they were added, from left to right, then from top to bottom.
 */
public class SquareGridLayout implements LayoutManager {
    private final int squareCountH;

    private final ScaleSupplier squareWidthSupplier;


    /**
     * Create a new SquareGridLayout
     * @param squareCountH The number of squares in a row
     * @param squareWidthSupplier A supplier giving the size of each square
     */
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
