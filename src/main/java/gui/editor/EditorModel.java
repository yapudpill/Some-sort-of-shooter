package gui.editor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import model.level.InvalidMapException;
import model.level.MapModel;
import model.level.TileModel;
import util.Pair;
import util.Resource;

public class EditorModel {
    // The first character is considered to be the default tile
    private static final char[] possibleChars = { ' ', '#', 'V' };
    private static final Map<Character, Integer> indexes = new HashMap<>();
    static {
        for (int i = 0; i < possibleChars.length; i++) {
            char c = possibleChars[i];
            indexes.put(c, i);
        }
        indexes.put('S', -1);
    }

    // Pair rather than Coordinates because x and y in Coordinates are double
    private Pair<Integer, Integer> spawn;
    private int rows, cols;
    private char[][] charGrid;
    private TileModel[][] tileGrid;

    public EditorModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        reset();
    }

    public void reset() {
        spawn = null;
        charGrid = new char[rows][cols];
        tileGrid = new TileModel[rows][cols];

        char defaultChar = possibleChars[0];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                updateSquare(x, y, defaultChar);
            }
        }
    }

    public void nextType(int x, int y) {
        char c = charGrid[y][x];
        if (c == 'S') {
            spawn = null;
        }
        int newIndex = (indexes.get(c) + 1) % possibleChars.length;
        updateSquare(x, y, possibleChars[newIndex]);
    }

    public void prevType(int x, int y) {
        char c = charGrid[y][x];
        if (c == 'S') {
            spawn = null;
        }
        int newIndex = Math.floorMod(indexes.get(c) - 1, possibleChars.length);
        updateSquare(x, y, possibleChars[newIndex]);
    }

    public void setSpawn(int x, int y) {
        if (spawn != null) {
            nextType(spawn.first(), spawn.second());
        }
        spawn = new Pair<>(x, y);
        updateSquare(x, y, 'S');
    }

    private void updateSquare(int x, int y, char newChar) {
        charGrid[y][x] = newChar;
        tileGrid[y][x] = MapModel.convertChar(newChar);
    }

    public void readFile(Resource map) throws InvalidMapException {
        charGrid = MapModel.parseMap(map);
        rows = charGrid.length;
        cols = charGrid[0].length;

        tileGrid = new TileModel[rows][cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                tileGrid[y][x] = MapModel.convertChar(charGrid[y][x]);

                if (charGrid[y][x] == 'S') {
                    spawn = new Pair<>(x, y);
                }
            }
        }
    }

    public void writeFile(File f) throws IOException {
        if (!f.exists()) {
            f.createNewFile();
        }

        PrintWriter out = new PrintWriter(f);
        String fullLine = "+---".repeat(cols) + "+";
        String sepLine = "+   ".repeat(cols) + "+";

        for (int y = 0; y < rows; y++) {
            out.println(y == 0 ? fullLine : sepLine);
            out.printf("| %c ", charGrid[y][0]);
            for (int x = 1; x < cols; x++) {
                out.printf("  %c ", charGrid[y][x]);
            }
            out.println("|");
        }
        out.print(fullLine);

        out.close();
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

    public Pair<Integer, Integer> getSpawn() {
        return spawn;
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
