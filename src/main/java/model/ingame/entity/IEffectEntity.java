package model.ingame.entity;

/**
 * Interface for entities that can apply effects (i.e. reduce HP) to other entities.
 */
public interface IEffectEntity extends IEntity {
    boolean canApplyEffect(IVulnerableEntity target);
}
