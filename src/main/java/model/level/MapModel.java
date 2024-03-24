package model.level;


import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IEntity;
import model.level.tiles.StandardTileModel;
import model.level.tiles.VoidTileModel;
import model.level.tiles.WaterTileModel;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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

    public List<ICollisionEntity> getAllCollidablesAround(int x, int y) {
        // Get all the entities that could be colliding with the given entity, i.e the entities in the 3x3 grid around the given entity.
        List<ICollisionEntity> involvedEntities = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isOutOfBounds(x + i, y + j))
                    continue;
                involvedEntities.addAll(tiles[y + j][x + i].getCollidables());
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

    public boolean obstaclesBetween(Coordinates pos1, Coordinates pos2){
        // Bresenham's algorithm

        int startX = (int) pos1.x;
        int startY = (int) pos1.y;
        int endX = (int) pos2.x;
        int endY = (int) pos2.y;

        int dx = Math.abs(endX - startX);
        int dy = Math.abs(endY - startY);
        int x = startX;
        int y = startY;
        int n = 1 + dx + dy;
        int x_inc = (endX > startX) ? 1 : -1;
        int y_inc = (endY > startY) ? 1 : -1;
        int error = dx - dy;
        dx *= 2;
        dy *= 2;

        for (; n > 0; n--) {
            if (!isWalkableAt(x, y)) {
                return true; // Obstacle found
            }
            if (error > 0) {
                x += x_inc;
                error -= dy;
            } else {
                y += y_inc;
                error += dx;
            }
        }
        return false; // No obstacles found between the two positions
    }

}