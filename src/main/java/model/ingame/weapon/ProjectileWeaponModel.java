package model.ingame.weapon;

import java.util.List;

import javax.swing.Timer;

import model.ingame.entity.EntityModel;

public abstract class ProjectileWeaponModel {
    protected Timer shootingTimer;
    protected int range;
    protected EntityModel currentHolder;
    protected List<ProjectileModel> shotProjectiles;
}
