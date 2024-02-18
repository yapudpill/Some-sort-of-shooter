package model.level;

import model.ingame.entity.EntityModel;
import model.ingame.entity.ICollisionEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapModel {
    private TileModel[][] tiles;

    public MapModel(String path) {
        loadMap(path);
    }

    public MapModel(int width, int height, TileModel[][] tiles) {
        this.tiles = tiles;
    }

    public static void loadMap(String path) {
        // Load map from file
    }

    public boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= tiles[0].length || y < 0 || y >= tiles.length;
    }

    public boolean isWalkableAt(int x, int y) {
        if (isOutOfBounds(x, y))
            return false;
        return tiles[y][x].isWalkable();
    }

    public List<Iterator<ICollisionEntity>> getAllCollidableIteratorsAround(int x, int y) {
        List<Iterator<ICollisionEntity>> iterators = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newX = x + i;
                int newY = y + j;
                if (!isOutOfBounds(newX, newY)) {
                    iterators.add(tiles[newY][newX].getCollidablesIterator());
                }
            }
        }
        return iterators;
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
}