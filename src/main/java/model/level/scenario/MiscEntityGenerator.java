package model.level.scenario;

import model.ingame.entity.IEntity.IEntityFactory;

import java.util.Collection;

public class MiscEntityGenerator extends WeightedRandomElementGenerator<IEntityFactory> {
    @Override
    public Collection<IEntityFactory> getAllowedElements() {
        return MiscEntityParser.AVAILABLE_MISC_ENTITIES.values();
    }
}
