package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.entity.CombatEntityModel;
import model.ingame.entity.AttachedDamageZoneEntity;
import model.ingame.entity.IEntity;
import util.ModelTimer;

public class KnifeWeapon extends WeaponModel {
    private static final double DMG_ZONE_ATTACKER_CENTER_SHIFT = 0.5;
    private static final double DMG_ZONE_WIDTH = 1;
    private static final double DMG_ZONE_HEIGHT = 1;
    private static final int ATTACK_DURATION = 10;
    private static final int KNIFE_COOLDOWN = 60;
    private static final int DAMAGE = 10;

    private final ModelTimer attackDurationTimer;

    private AttachedDamageZoneEntity damageZone = null;


    public KnifeWeapon(IEntity owner, GameModel gameModel) {
        super("Knife", "knife", gameModel, owner, KNIFE_COOLDOWN);
        this.attackDurationTimer = new ModelTimer(ATTACK_DURATION, () -> {
            gameModel.getEntitySet().remove(damageZone);
            gameModel.detachAsUpdateable(damageZone);
        }, gameModel);
        attackDurationTimer.setRepeats(false);
    }

    @Override
    public boolean attack() {
        if (coolDownTimer.isRunning()) {
            System.out.println("Knife is still cooling down");
            return false;
        }
        coolDownTimer.start();
        attackDurationTimer.start();

        this.damageZone = new AttachedDamageZoneEntity(owner.getPos(), DMG_ZONE_WIDTH, DMG_ZONE_HEIGHT, DMG_ZONE_ATTACKER_CENTER_SHIFT, gameModel, (CombatEntityModel) owner, DAMAGE);
        gameModel.getEntitySet().add(damageZone);
        gameModel.attachAsUpdateable(damageZone);
        return true;
    }
}
