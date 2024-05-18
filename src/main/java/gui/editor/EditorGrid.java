package gui.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import gui.ingame.entity.AbstractEntityRenderer;
import gui.ingame.entity.EntityRendererFactory;
import gui.ingame.tile.TileRenderer;
import gui.ingame.tile.TileRendererFactory;
import model.ingame.entity.IEntity;
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
                add(makeRenderer(x, y));
            }
        }

        revalidate();
    }

    private void updateCell(int x, int y) {
        int pos = y * model.getCols() + x;
        remove(pos);
        add(makeRenderer(x, y), pos);
        validate();
    }

    private TileRenderer makeRenderer(int x, int y) {
        TileModel tile = model.getTile(x, y);
        IEntity entity = model.getEntity(x, y);

        TileRenderer tileRenderer = TileRendererFactory.make(tile);
        if (entity != null) {
            tileRenderer.setLayout(new BorderLayout());
            AbstractEntityRenderer entityRenderer = EntityRendererFactory.make(entity);
            tileRenderer.add(entityRenderer);
        }

        tileRenderer.addMouseListener(new MouseAdapter() {
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
        return tileRenderer;
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