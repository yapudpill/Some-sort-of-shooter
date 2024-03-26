package gui.editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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

    public void readFile(File f) throws FileNotFoundException {
        InputStream in = new FileInputStream(f);
        charGrid = MapModel.parseMap(in);
        rows = charGrid.length;
        cols = charGrid[0].length;

        tileGrid = new TileModel[rows][cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                tileGrid[y][x] = models.get(charGrid[y][x]);
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
