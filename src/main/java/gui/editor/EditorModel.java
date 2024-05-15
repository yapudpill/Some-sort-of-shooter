package gui.editor;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import model.ingame.GameModel;
import model.ingame.Statistics;
import model.ingame.entity.EntityConstructor;
import model.ingame.entity.IEntity;
import model.level.InvalidMapException;
import model.level.MapModel;
import model.level.TileModel;
import model.level.scenario.Scenario;
import model.level.tiles.SpawnTileModel;
import util.Coordinates;
import util.Pair;
import util.Resource;

public class EditorModel {
    // The first character is considered to be the default tile
    private static final char[] possibleChars = { ' ', '#', 'V', '/' };
    private static final Map<Character, Integer> indexes = new HashMap<>();
    static {
        for (int i = 0; i < possibleChars.length; i++) {
            indexes.put(possibleChars[i], i);
        }
        indexes.put('S', -1);
    }


    // A dummy GameModel to instantiate the entities returned by convertChar
    private final GameModel dummyModel;

    // Pair rather than Coordinates because x and y in Coordinates are double
    private int rows, cols;
    private Pair<Integer, Integer> spawn;
    private char[][] charGrid;
    private TileModel[][] tileGrid;
    private IEntity[][] entityGrid;

    public EditorModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        Statistics dummyStatistics = new Statistics(null, null);
        Scenario dummyScenario = new Scenario(null);
        MapModel dummyMap = null;
        try {
            dummyMap = new MapModel(new Resource("dummyMap", getClass()::getResourceAsStream));
        } catch (InvalidMapException e) {
            // It is impossible to end up here, dummyMap is a valid map
            e.printStackTrace();
            System.exit(1);
        }
        dummyModel = new GameModel(dummyMap, dummyStatistics, dummyScenario);

        reset();
    }

    public void reset() {
        spawn = null;
        charGrid = new char[rows][cols];
        tileGrid = new TileModel[rows][cols];
        entityGrid = new IEntity[rows][cols];

        char defaultChar = possibleChars[0];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                updateSquare(x, y, defaultChar);
            }
        }
    }

    public void nextType(int x, int y) {
        if (spawn != null && spawn.equals(x, y)) {
            spawn = null;
        }
        int newIndex = (indexes.get(charGrid[y][x]) + 1) % possibleChars.length;
        updateSquare(x, y, possibleChars[newIndex]);
    }

    public void prevType(int x, int y) {
        if (spawn != null && spawn.equals(x, y)) {
            spawn = null;
        }
        int newIndex = Math.floorMod(indexes.get(charGrid[y][x]) - 1, possibleChars.length);
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

        Pair<TileModel, EntityConstructor> pair = MapModel.convertChar(newChar);
        tileGrid[y][x] = pair.first();
        if (pair.second() != null) {
            entityGrid[y][x] = pair.second().makeEntity(Coordinates.ZERO, dummyModel);
        } else {
            entityGrid[y][x] = null;
        }
    }

    public void readFile(Resource map) throws InvalidMapException {
        charGrid = MapModel.parseMap(map);
        rows = charGrid.length;
        cols = charGrid[0].length;

        tileGrid = new TileModel[rows][cols];
        entityGrid = new IEntity[rows][cols];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                updateSquare(x, y, charGrid[y][x]);

                if (tileGrid[y][x] instanceof SpawnTileModel) {
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

    public IEntity getEntity(int x, int y) {
        return entityGrid[y][x];
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
