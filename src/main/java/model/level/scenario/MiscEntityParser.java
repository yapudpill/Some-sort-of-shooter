package model.level.scenario;

import model.ingame.entity.FirstAidKit;
import model.ingame.entity.IEntity;

import java.util.Map;

public class MiscEntityParser {
    public static final Map<String, IEntity.EntityFactory> AVAILABLE_MISC_ENTITIES = Map.of(
            "Bandages", FirstAidKit::new
    );
}
