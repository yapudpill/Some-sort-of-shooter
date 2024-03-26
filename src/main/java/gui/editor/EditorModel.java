package gui.editor;

import java.util.HashMap;
import java.util.Map;

import model.level.MapModel;
import model.level.TileModel;

public class EditorModel {
    private static final char[] possibleChars = { ' ', '#', 'V' };
    private static final Map<Character, Integer> indexes = new HashMap<>();
    private static final Map<Character, TileModel> models = new HashMap<>();
    static {
        for (int i = 0; i < possibleChars.length; i++) {
            char c = possibleChars[i];
            indexes.put(c, i);
            models.put(c, MapModel.convertChar(c));
        }
    }

    private int rows, cols;
    private char[][] charGrid;
    private TileModel[][] tileGrid;

    public EditorModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        reset();
    }

    public void reset() {
        charGrid = new char[rows][cols];
        tileGrid = new TileModel[rows][cols];

        char defaultChar = possibleChars[0];
        TileModel defaultModel = models.get(defaultChar);

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                charGrid[y][x] = defaultChar;
                tileGrid[y][x] = defaultModel;
            }
        }
    }

    public void nextType(int x, int y) {
        int newIndex = (indexes.get(charGrid[y][x]) + 1)% possibleChars.length;
        charGrid[y][x] = possibleChars[newIndex];
        tileGrid[y][x] = models.get(charGrid[y][x]);
    }

    public void prevType(int x, int y) {
        int newIndex = Math.floorMod(indexes.get(charGrid[y][x]) - 1, possibleChars.length);
        charGrid[y][x] = possibleChars[newIndex];
        tileGrid[y][x] = models.get(charGrid[y][x]);
    }

    public TileModel getTile(int x, int y) {
        return tileGrid[y][x];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void setRows(int rows) {
        if (this.rows != rows) {
            this.rows = rows;
            reset();
        }
    }

    public void setCols(int cols) {
        if (this.cols != cols) {
            this.cols = cols;
            reset();
        }
    }
}
