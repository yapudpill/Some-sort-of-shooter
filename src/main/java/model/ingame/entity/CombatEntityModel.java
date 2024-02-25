package model.ingame.entity;

import model.ingame.weapon.ProjectileWeaponModel;

public abstract class CombatEntityModel extends CreatureModel implements ICombatEntity{
    // TODO1: replace this with a more general interface that covers non projectile weapons
    protected ProjectileWeaponModel weapon;

    public CombatEntityModel(int maxHealth, double width, double height) {
        super(maxHealth, width, height);
        addCollisionListener(e -> {
            if (e.getSource() == this) {
               for (IEntity entity : e.getInvolvedEntitiesList()) {
                    if (entity instanceof WeaponEntity weaponEntity) {
                        if (weaponEntity.getWeapon() != null) {
                            setWeapon(weaponEntity.getWeapon());
                            weapon.setOwner(this);
                            // TODO: make the WeaponEntity disappear
                        }
                    }
                }
            }
        });
    }

    @Override
    public void attack() {
        // TODO1: again, should be more general
        if (weapon != null) weapon.shoot(movementHandler.getDirectionVector());
    }

    @Override
    public ProjectileWeaponModel getWeapon() {
        return weapon;
    }

    @Override
    public void setWeapon(ProjectileWeaponModel weapon) {
        this.weapon = weapon;
    }

    @Override
    public void update(){
        super.update();
        if (weapon != null) weapon.update();
    }

}
