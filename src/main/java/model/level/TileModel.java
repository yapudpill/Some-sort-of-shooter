package model.level;

import java.util.ArrayList;
import java.util.List;


public abstract class TileModel {
    private static final List<TileContent> tileContents = new ArrayList<>();

    public abstract boolean isWalkable();
    public abstract void applyEffect();

}
