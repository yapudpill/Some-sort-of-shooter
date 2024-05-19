package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.entity.CombatEntityModel;
import model.ingame.entity.ICombatEntity;
import model.ingame.entity.KnifeZoneEntity;

/**
 * A model for the knife weapon. The knife weapon is a melee weapon that deals damage in a small area around the attacker.
 */
public class KnifeWeapon extends WeaponModel {
    private static final double DMG_ZONE_ATTACKER_CENTER_SHIFT = 0.8;
    private static final double DMG_ZONE_WIDTH = 1.6;
    private static final double DMG_ZONE_HEIGHT = 1.6;
    private static final double ATTACK_DURATION = 0.4;
    private static final double KNIFE_COOLDOWN = 0.41;
    private static final int DAMAGE = 1;
    private static final int DOT = 5;

    private final ModelTimer attackDurationTimer;

    private KnifeZoneEntity damageZone = null;

    public KnifeWeapon(ICombatEntity owner, GameModel gameModel) {
        super("Knife", "knife", owner, gameModel, KNIFE_COOLDOWN);
        attackDurationTimer = new ModelTimer(
            ATTACK_DURATION,
            false,
            this::attackCallback,
            gameModel
        );
    }

    private void attackCallback() {
        damageZone.despawn();
    }

    @Override
    public boolean attack() {
        if (coolDownTimer.isRunning()) {
            return false;
        }

        coolDownTimer.start();
        attackDurationTimer.start();

        this.damageZone = new KnifeZoneEntity(
            owner.getPos(),
            DMG_ZONE_WIDTH,
            DMG_ZONE_HEIGHT,
            DMG_ZONE_ATTACKER_CENTER_SHIFT,
            gameModel,
            (CombatEntityModel) owner,
            DAMAGE,
            DOT
        );
        gameModel.addEntity(damageZone);
        gameModel.attachAsUpdateable(damageZone);
        return true;
    }
}
