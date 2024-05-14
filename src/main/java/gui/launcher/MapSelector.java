package gui.launcher;

import model.level.MapModel;

public class MapSelector extends ResourceSelector {

    public MapSelector() {
        super("map", "mapIndex", "maps/%s", MapModel.class);
    }
}
