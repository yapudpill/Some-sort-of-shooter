package model.level;

import model.ingame.entity.EntityModel;
import model.ingame.entity.ICollisionEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapModel {
    private TileModel[][] tiles;
    private int width;
    private int height;

    public MapModel(String path) {
        loadMap(path);
    }

    public MapModel(int width, int height, TileModel[][] tiles) {
        this.width = width;
        this.height = height;
        this.tiles = tiles;
    }

    public static void loadMap(String path) {
        // Load map from file
    }

    public boolean isOutOfBounds(int x, int y) {
        return x < 0 || x >= width || y < 0 || y >= height;
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
                    iterators.add(tiles[newX][newY].getCollidablesIterator());
                }
            }
        }
        return iterators;
    }

    public void addCollidableAt(ICollisionEntity entity, int x, int y) {
        if (entity == null)
            throw new IllegalArgumentException("Entity cannot be null");
        if (isOutOfBounds(x, y))
            throw new IllegalArgumentException("Coordinates out of bounds");
        tiles[y][x].addCollidable(entity);
    }

    public void removeCollidableAt(ICollisionEntity entity, int x, int y) {
        if (entity == null)
            throw new IllegalArgumentException("Entity cannot be null");
        if (isOutOfBounds(x, y))
            throw new IllegalArgumentException("Coordinates out of bounds");
        tiles[y][x].removeCollidable(entity);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public TileModel getTile(int x, int y) {
        return tiles[y][x];
    }
}