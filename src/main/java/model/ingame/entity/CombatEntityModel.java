package model.ingame.entity;

import model.ingame.weapon.ProjectileWeaponModel;

public abstract class CombatEntityModel extends CreatureModel implements ICombatEntity{
    // TODO1: replace this with a more general interface that covers non projectile weapons
    protected ProjectileWeaponModel weapon;

    public CombatEntityModel(int maxHealth, double width, double height) {
        super(maxHealth, width, height);
    }

    @Override
    public void attack() {
        // TODO1: again, should be more general
        weapon.shoot(movementHandler.getDirectionVector());
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
        weapon.update();
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

}
