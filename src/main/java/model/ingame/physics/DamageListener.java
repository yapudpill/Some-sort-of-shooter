package model.ingame.physics;

import model.ingame.entity.IDamagingEntity;
import model.ingame.entity.IVulnerableEntity;

public class DamageListener implements CollisionListener {

    @Override
    public void onCollision(CollisionEvent e) {
        if (e.getSource() instanceof IDamagingEntity damaging) {
            e.getInvolvedEntitiesList().forEach(entity -> {
                if(entity instanceof IVulnerableEntity vul) {
                    damaging.inflictDamage(vul);
                }
            });
        }
    }
}
