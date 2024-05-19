package model.ingame.weapon;


public interface ContinuousFireWeapon {
    void cooling();
    int getHeat();
    int getMaxheat();
}
