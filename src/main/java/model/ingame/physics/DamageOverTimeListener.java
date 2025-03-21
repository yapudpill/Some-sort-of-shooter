package model.ingame.physics;

import model.ingame.entity.IEffectEntity;
import model.ingame.entity.IVulnerableEntity;

import java.util.function.Consumer;

public class DamageOverTimeListener implements CollisionListener {
    private final int damage;
    private final Consumer<IVulnerableEntity> onHit;

    public DamageOverTimeListener(int damage, Consumer<IVulnerableEntity> onHit) {
        this.damage = damage;
        this.onHit = onHit;
    }

    public DamageOverTimeListener(int damage) {
        this.damage = damage;
        this.onHit = e -> {};
    }

    @Override
    public void onCollision(CollisionEvent e) {
        if (e.getSource() instanceof IEffectEntity eff) {
            e.getInvolvedEntitiesList().forEach(entity -> {
                if (entity instanceof IVulnerableEntity vul && eff.canApplyEffect(vul)) {
                    vul.takeDOT(damage);
                    onHit.accept(vul);
                }
            });
        }
    }
}
