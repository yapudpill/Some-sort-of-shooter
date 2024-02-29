package model.ingame.entity;

public interface IDamagingEntity extends IEntity {
    void inflictDamage(IVulnerableEntity target);
}
