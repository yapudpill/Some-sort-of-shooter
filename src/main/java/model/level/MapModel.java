package model.level;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import model.ingame.entity.ICollisionEntity;
import model.ingame.entity.IEntity;
import model.level.tiles.SpawnTileModel;
import model.level.tiles.StandardTileModel;
import model.level.tiles.VoidTileModel;
import model.level.tiles.WaterTileModel;
import util.Coordinates;
import util.Resource;

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
                if (tiles[i][j] instanceof SpawnTileModel) {
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

        int height = lines.length / 2;
        int width = (lines[0].length()) / 4;
        char[][] arr = new char[height][width];
        boolean foundSpawn = false;

        for (int i = 0; i < height; i++) {
            String current = lines[2*i + 1];
            for (int j = 0; j < width; j++) {
                try {
                    arr[i][j] = current.charAt(4*j + 2);
                } catch (StringIndexOutOfBoundsException e) {
                    throw new InvalidMapException("Malformed map");
                }

                if (arr[i][j] == 'S') {
                    if (foundSpawn) {
                        throw new InvalidMapException("Map has two spawn points");
                    } else {
                        foundSpawn = true;
                    }
                }
            }
        }

        if (!foundSpawn) {
            throw new InvalidMapException("Map has no spawn point");
        }

        return arr;
    }

    public static TileModel convertChar(char c) {
        return switch (c) {
            case '#' -> new WaterTileModel();
            case 'V' -> new VoidTileModel();
            case 'S' -> new SpawnTileModel();
            case ' ' -> new StandardTileModel();
            default  -> new StandardTileModel();
        };
    }

    public Coordinates getPlayerSpawn() {
        return playerSpawn;
    }

    public boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= tiles[0].length || y < 0 || y >= tiles.length;
    }

    public boolean isOutOfBounds(Coordinates pos) {
        return pos.x() < 0 || pos.y() < 0 || isOutOfBounds((int) pos.x(), (int) pos.y());
    }

    public Set<ICollisionEntity> getAllCollidablesAround(int x, int y) {
        // Get all the entities that could be colliding with the given entity, i.e the entities in the 3x3 grid around the given entity.
        Set<ICollisionEntity> involvedEntities = new CopyOnWriteArraySet<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isOutOfBounds(x + i, y + j)) continue;
                involvedEntities.addAll(tiles[y + j][x + i].getCollidablesSet());
            }
        }
        return involvedEntities;
    }

    public Set<ICollisionEntity> getAllCollidablesAround(Coordinates pos) {
        return getAllCollidablesAround((int) pos.x(), (int) pos.y());
    }

    public void addCollidableAt(ICollisionEntity entity, int x, int y) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        if (isOutOfBounds(x, y)) return;
        tiles[y][x].addCollidable(entity);
    }

    public void addCollidableAt(ICollisionEntity entity, Coordinates pos) {
        addCollidableAt(entity, (int) pos.x(), (int) pos.y());
    }

    public void removeCollidableAt(ICollisionEntity entity, int x, int y) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }
        if (isOutOfBounds(x, y)) return;
        tiles[y][x].removeCollidable(entity);
    }

    public void removeCollidableAt(ICollisionEntity entity, Coordinates pos) {
        removeCollidableAt(entity, (int) pos.x(), (int) pos.y());
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

    /**
     * Coordinates are floored via a casting from double to int
     */
    public TileModel getTile(Coordinates pos) {
        return getTile((int) pos.x(), (int) pos.y());
    }

    public void applyTileEnterEffect(IEntity entity, int x, int y) {
        getTile(x, y).applyEnterEffect(entity);
    }

    public boolean canEnterAround(IEntity entity, int x, int y) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (isOutOfBounds(x + i, y + j)) continue;
                if (!tiles[y + j][x + i].canEnter(entity)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canEnterAround(IEntity entity, Coordinates pos) {
        return canEnterAround(entity, (int) pos.x(), (int) pos.y());
    }

    public boolean obstaclesBetween(Coordinates pos1, Coordinates pos2, IEntity entity) {
        int x0 = (int) pos1.x();
        int y0 = (int) pos1.y();
        int x1 = (int) pos2.x();
        int y1 = (int) pos2.y();

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

        for (; n > 0; --n) {
            boolean notBeginningOrEnd = x != x0 || y != y0 && x != x1 || y != y1;
            if (!canEnterAround(entity, x, y) && notBeginningOrEnd) {
                return true;
            }

            if (error > 0) {
                x += x_inc;
                error -= dy;
            } else {
                y += y_inc;
                error += dx;
            }
        }
        return false;
    }

    // debug
    public void printCollideables() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                System.out.println("Tile at " + j + ", " + i + " : ");
                tiles[i][j].printCollidables();
            }
        }
    }
}