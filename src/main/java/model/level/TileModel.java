package main.java.model.level;

import java.util.ArrayList;
import java.util.List;

import main.java.model.ingame.MapComponentModel;

public class TileModel extends MapComponentModel {
    private TileType type;
    public static final List<TileContent> tileContents = new ArrayList<>();

    public TileModel(double x, double y, TileType type) {
        super(x, y);
        this.type = type;
    }
    
    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }
    
}
