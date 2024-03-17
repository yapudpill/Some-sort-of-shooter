package model.ingame.physics;

import model.ingame.entity.IEffectEntity;
import model.ingame.entity.IVulnerableEntity;

public class DamageListener implements CollisionListener {
    private final int damage;

    public DamageListener(int damage) {
        this.damage = damage;
    }

    @Override
    public void onCollision(CollisionEvent e) {
        if (e.getSource() instanceof IEffectEntity eff) {
            e.getInvolvedEntitiesList().forEach(entity -> {
                if(entity instanceof IVulnerableEntity vul && eff.canApplyEffect(vul)) {
                    vul.takeDamage(damage);
                }
            });
        }
    }
}
