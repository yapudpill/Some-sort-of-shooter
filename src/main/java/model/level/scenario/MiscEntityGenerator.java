package model.level.scenario;

import model.ingame.entity.IEntity.EntityFactory;

import java.util.Collection;

public class MiscEntityGenerator extends WeightedRandomElementGenerator<EntityFactory> {
    public MiscEntityGenerator(double tickRate) {
        super(tickRate);
    }

    @Override
    public Collection<EntityFactory> getAllowedElements() {
        return MiscEntityParser.AVAILABLE_MISC_ENTITIES.values();
    }
}
