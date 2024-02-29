package model.ingame.entity;

public interface IEffectEntity extends IEntity {
    boolean canApplyEffect(IVulnerableEntity target);
}
