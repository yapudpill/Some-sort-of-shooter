package model.level;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import model.ingame.Coordinates;
import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IEntity;
import model.level.tiles.StandardTileModel;
import model.level.tiles.VoidTileModel;
import model.level.tiles.WaterTileModel;

public class MapModel {
    private TileModel[][] tiles;

    public MapModel(String mapName){
        tiles = loadMap("maps/" + mapName);
    }

    /**
     * The tiles attribute can be loaded from a file in resources/model/level/maps
     * Here is an example of what a map file could look like
     * <pre>
     * +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+
     * |         #                                                                     |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         #                                                       b   b         |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         #       r                                               b   b         |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         #               #   #   #   #                               b         |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         #               #   #           s   s                                 |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         #   #   #   #   #           s   s                                     |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                             #   #   #   #   #   #   #         |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                         #   #                                 |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         /   /   /                       #   #                                 |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         /   /   /                       #                   r                 |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |         /   /                                                                 |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                                                               |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                                                               |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                                                               |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                                                               |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                                                               |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                                                               |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                                                               |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                                                               |
     * +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +   +
     * |                                                                               |
     * +---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+---+
     * </pre>
     * the first and last lines and the first and last column are the limits of the map
     * the different tiles are separated by + at every corner
     * in this file, the characters are :
     * <ul>
     *      <li> '+' is at every corner, it's used to show the grid  </li>
     *      <li> '---' is a horizontal border of the map </li>
     *      <li> '|' (pipe) is a vertical border of the map </li>
     *      <li> ' ' indicates the default empty tile (no effect + walkable) </li>
     *      <li> '#' indicates the default wall tile (no effect + non walkable) </li>
     *      <li> 'b', '/', 'r' and 's' are just for the show, no actual meaning for now </li>
     * </ul>
     */

    /**
     *
     * @param lines String corresponding to each line of the map file
     * @return array containing the character corresponding to the content of each tile
     * parseMap ignores everything except the center of each square, so it ignores 1/2 lines and 3/4 columns
     */
    public static char[][] parseMap(String[] lines) {
        int height = lines.length/2;
        int width = (lines[0].length())/4;
        String current;
        char[][] arr = new char[height][width];
        for (int i = 0; i < height; i++) {
            current = lines[i*2+1];
            for (int j = 0; j < width; j++) {
                arr[i][j] = current.charAt(j*4+2);
            }
        }
        return arr;
    }

    /**
     *
     * @param path is the path of the map.txt (located in the maps directory in the model.level package of resources)
     * @return a TileModel[][] containing the tiles corresponding to the loaded map.txt
     */
    public static TileModel[][] loadMap(String path){
        InputStream in = MapModel.class.getResourceAsStream(path);
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String[] tab = reader.lines().toArray(String[]::new);
            char[][] parsedMap = parseMap(tab);

            TileModel[][] tiles = new TileModel[parsedMap.length][parsedMap[0].length];
            for (int i = 0; i < parsedMap.length; i++) {
                for (int j = 0; j < parsedMap[0].length; j++) {
                    switch (parsedMap[i][j]) {
                        case '#' -> tiles[i][j] = new WaterTileModel();
                        case 'V' -> tiles[i][j] = new VoidTileModel();
                        default -> tiles[i][j] = new StandardTileModel();
                    }
                }
            }
            return tiles;
        }
        catch (NullPointerException e) {
            System.out.println("invalid path");
        }
        return null;
    }

    public boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= tiles[0].length || y < 0 || y >= tiles.length;
    }

    public Set<ICollisionEntity> getAllCollidablesAround(int x, int y) {
        // Get all the entities that could be colliding with the given entity, i.e the entities in the 3x3 grid around the given entity.
        Set<ICollisionEntity> involvedEntities = new CopyOnWriteArraySet<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isOutOfBounds(x + i, y + j))
                    continue;
                involvedEntities.addAll(tiles[y + j][x + i].getCollidablesSet());
            }
        }
        return involvedEntities;
    }

    public void addCollidableAt(ICollisionEntity entity, int x, int y) {
        if (entity == null)
            throw new IllegalArgumentException("Entity cannot be null");
        if (isOutOfBounds(x, y))
            return;
        tiles[y][x].addCollidable(entity);
    }

    public void removeCollidableAt(ICollisionEntity entity, int x, int y) {
        if (entity == null)
            throw new IllegalArgumentException("Entity cannot be null");
        if (isOutOfBounds(x, y))
            return;
        tiles[y][x].removeCollidable(entity);
    }

    public int getWidth() {
        return tiles[0].length;
    }

    public int getHeight() {
        return tiles.length;
    }

    public TileModel getTile(int x, int y) {
        return tiles[y][x];
    }

    public void applyTileEnterEffect(IEntity entity, int x, int y) {
        getTile(x, y).applyEnterEffect(entity);
    }

    public void reset(){
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                tiles[i][j].reset();
            }
        }
    }

    public boolean unwalkableAround(int x, int y) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isOutOfBounds(x + i, y + j))
                    continue;
                if (!tiles[y + j][x + i].isWalkable())
                    return true;
            }
        }
        return false;
    }

    public boolean isWalkableAt(int x, int y) {
        if (isOutOfBounds(x, y))
            return false;
        return tiles[y][x].isWalkable();
    }

    public boolean canEnterAround(IEntity entity, int x, int y) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isOutOfBounds(x + i, y + j))
                    continue;
                if (!tiles[y + j][x + i].canEnter(entity))
                    return false;
            }
        }
        return true;
    }

    public boolean canEnterAt(IEntity entity, int x, int y) {
        if (isOutOfBounds(x, y))
            return false;
        return tiles[y][x].canEnter(entity);
    }

    public boolean obstaclesBetween(Coordinates pos1, Coordinates pos2, IEntity entity){
        int x0 = (int) pos1.x;
        int y0 = (int) pos1.y;
        int x1 = (int) pos2.x;
        int y1 = (int) pos2.y;

        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int x = x0;
        int y = y0;
        int n = 1 + dx + dy;
        int x_inc = (x1 > x0) ? 1 : -1;
        int y_inc = (y1 > y0) ? 1 : -1;
        int error = dx - dy;
        dx *= 2;
        dy *= 2;

        for (; n > 0; --n)
        {
            boolean notBeginnigOrEnd = x != x0 || y != y0 && x != x1 || y != y1;
            if(!canEnterAround(entity, x, y) && notBeginnigOrEnd) return true;

            if (error > 0)
            {
                x += x_inc;
                error -= dy;
            }
            else
            {
                y += y_inc;
                error += dx;
            }
        }
        return false;
    }

    public void printCollideables() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                System.out.println("Tile at " + j + ", " + i + " : ");
                tiles[i][j].printCollidables();
            }
        }
    }
}