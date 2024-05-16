package model.level.scenario;

import model.ingame.entity.EntityConstructor;
import model.ingame.entity.FirstAidKit;

import java.util.Map;

public class MiscEntityParser {
    public static final Map<String, EntityConstructor> AVAILABLE_MISC_ENTITIES = Map.of(
            "Bandages", FirstAidKit::new
    );

    public static EntityConstructor parseMiscEntity(String name) {
        return AVAILABLE_MISC_ENTITIES.get(name);
    }
}
