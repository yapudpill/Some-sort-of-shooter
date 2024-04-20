package gui.editor;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import gui.ingame.tile.TileRendererFactory;
import gui.ingame.tile.AbstractTileRenderer;
import model.level.TileModel;
import util.Pair;

public class EditorGrid extends JPanel {
    private final EditorModel model;
    private final GridLayout layout;

    public EditorGrid(EditorModel model) {
        this.model = model;
        layout = new GridLayout(1, 1, 1, 1);
        setLayout(layout);
        reset();
    }

    public void reset() {
        int rows = model.getRows();
        int cols = model.getCols();

        // Note: setting columns is useless, see GridLayout documentation
        layout.setRows(rows);

        removeAll();
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                add(listeningRenderer(x, y, model.getTile(x, y)));
            }
        }

        revalidate();
    }

    private void updateCell(int x, int y) {
        int pos = y * model.getCols() + x;
        remove(pos);
        add(listeningRenderer(x, y, model.getTile(x, y)), pos);
        validate();
    }

    private AbstractTileRenderer listeningRenderer(int x, int y, TileModel t) {
        AbstractTileRenderer renderer = TileRendererFactory.make(t);
        renderer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1 -> {
                        model.nextType(x, y);
                        updateCell(x, y);
                    }
                    case MouseEvent.BUTTON3 -> {
                        model.prevType(x, y);
                        updateCell(x, y);
                    }
                    case MouseEvent.BUTTON2 -> {
                        Pair<Integer, Integer> oldSpawn = model.getSpawn();
                        model.setSpawn(x, y);
                        if (oldSpawn != null) {
                            updateCell(oldSpawn.first(), oldSpawn.second());
                        }
                        updateCell(x, y);
                    }
                }
            }
        });
        return renderer;
    }

    @Override
    public Dimension getPreferredSize() {
        int rows = model.getRows();
        int cols = model.getCols();

        Component parent = getParent();
        int pw = parent.getWidth();
        int ph = parent.getHeight();

        if (pw * rows <= ph * cols) {
            return new Dimension(pw, pw * rows / cols);
        }
        return new Dimension(ph * cols / rows, ph);
    }
}