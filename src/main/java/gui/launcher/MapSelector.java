package gui.launcher;

import model.level.MapModel;

/**
 * A selector for maps. Can select a built-in map or a custom map from an external XML file.
 */
public class MapSelector extends ResourceSelector {

    public MapSelector() {
        super("map", "mapIndex", "maps/%s", MapModel.class);
    }
}
