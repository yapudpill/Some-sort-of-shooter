package model.level.scenario;

import model.ingame.entity.FirstAidKit;
import model.ingame.entity.IEntity;

import java.util.Map;

public class MiscEntityParser {
    public static final Map<String, IEntity.IEntityFactory> AVAILABLE_MISC_ENTITIES = Map.of(
            "Bandages", FirstAidKit::new
    );

    public static IEntity.IEntityFactory parseMiscEntity(String name) {
        return AVAILABLE_MISC_ENTITIES.get(name);
    }
}
