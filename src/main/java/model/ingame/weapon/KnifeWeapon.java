package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.entity.CombatEntityModel;
import model.ingame.entity.ICombatEntity;
import model.ingame.entity.KnifeZoneEntity;

public class KnifeWeapon extends WeaponModel {
    private static final double DMG_ZONE_ATTACKER_CENTER_SHIFT = 0.5;
    private static final double DMG_ZONE_WIDTH = 1;
    private static final double DMG_ZONE_HEIGHT = 1;
    private static final double ATTACK_DURATION = 0.4;
    private static final double KNIFE_COOLDOWN = 0.3;
    private static final int DAMAGE = 10;

    private final ModelTimer attackDurationTimer;

    private KnifeZoneEntity damageZone = null;

    public KnifeWeapon(ICombatEntity owner, GameModel gameModel) {
        super("Knife", "knife", gameModel, owner, KNIFE_COOLDOWN);
        attackDurationTimer = new ModelTimer(
            ATTACK_DURATION,
            false,
            this::attackCallback,
            gameModel
        );
    }

    private void attackCallback() {
        gameModel.removeEntity(damageZone);
        gameModel.detachAsUpdateable(damageZone);
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
            DAMAGE
        );
        gameModel.addEntity(damageZone);
        gameModel.attachAsUpdateable(damageZone);
        return true;
    }
}
