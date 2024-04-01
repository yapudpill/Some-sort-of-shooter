package model.level;

import model.ingame.Coordinates;
import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IEntity;
import model.level.tiles.StandardTileModel;
import model.level.tiles.VoidTileModel;
import model.level.tiles.WaterTileModel;
import util.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapModel {
    private final TileModel[][] tiles;
    private Coordinates playerSpawn;

    public MapModel(Resource map) throws InvalidMapException {
        char[][] parsedMap = parseMap(map);

        tiles = new TileModel[parsedMap.length][parsedMap[0].length];
        for (int i = 0; i < parsedMap.length; i++) {
            for (int j = 0; j < parsedMap[0].length; j++) {
                tiles[i][j] = convertChar(parsedMap[i][j]);

                // The function parseMap guaranties that there is exactly one
                // spawn point
                if (parsedMap[i][j] == 'S') {
                    playerSpawn = new Coordinates(j + 0.5, i + 0.5);
                }
            }
        }
    }

    /**
     * Converts a map coming from a Resource into an array of characters.
     * This function ignores everything except the center of each square, so it
     * ignores 1/2 lines and 3/4 columns.
     *
     * @param map the Resource from where the map is read
     * @return array containing the character corresponding to the content of
     *         each tile
     * @throws InvalidMapException if the map is not square or if it doesn't
     *                             contain a spawn point
     */
    public static char[][] parseMap(Resource map) throws InvalidMapException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(map.toStream()));
        String[] lines = reader.lines().toArray(String[]::new);

        int height = lines.length/2;
        int width = (lines[0].length())/4;
        char[][] arr = new char[height][width];
        boolean foundSpawn = false;

        for (int i = 0; i < height; i++) {
            String current = lines[2*i + 1];
            for (int j = 0; j < width; j++) {
                try {
                    arr[i][j] = current.charAt(4*j + 2);
                } catch (StringIndexOutOfBoundsException e) {
                    throw new InvalidMapException();
                }

                if (arr[i][j] == 'S') {
                    if (foundSpawn) {
                        throw new InvalidMapException();
                    } else {
                        foundSpawn = true;
                    }
                }
            }
        }

        if (!foundSpawn) {
            throw new InvalidMapException();
        }

        return arr;
    }

    public static TileModel convertChar(char c) {
        return switch (c) {
            case '#' -> new WaterTileModel();
            case 'V' -> new VoidTileModel();
            case ' ', 'S' -> new StandardTileModel();
            default -> new StandardTileModel();
        };
    }

    public Coordinates getPlayerSpawn() {
        return playerSpawn;
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

    public boolean isWalkableAt(int x, int y) {
        if (isOutOfBounds(x, y))
            return false;
        return tiles[y][x].isWalkable();
    }

    public boolean obstaclesBetween(Coordinates pos1, Coordinates pos2) {
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