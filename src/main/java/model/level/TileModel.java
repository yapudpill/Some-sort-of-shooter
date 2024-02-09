package model.level;

import java.util.ArrayList;
import java.util.List;

import model.ingame.Coordinates;
import model.ingame.MapComponentModel;
import model.ingame.entity.EntityModel;

public class TileModel extends MapComponentModel {
    private final List<TileContent> tileContents = new ArrayList<>();
    private final List<EntityModel> entitiesOnTile = new ArrayList<>(); 

    public TileModel(Coordinates pos) {
        super(pos);
        //TODO: implement projectile
    }
    
}
