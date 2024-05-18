package gui.ingame.entity;

import gui.ImageCache;
import model.ingame.entity.BreakableBarrier;
import model.ingame.entity.IVulnerableEntity;

public class BreakableBarrierRenderer extends AbstractEntityRenderer{

    public BreakableBarrierRenderer(BreakableBarrier entity) {
        super(entity);
        addRenderingCondition(e -> ((IVulnerableEntity) e).getHealth() >= 60, ImageCache.loadImage("sprites/breakablebarrier.png", PlayerRenderer.class));
        addRenderingCondition(e -> ((IVulnerableEntity) e).getHealth() >= 30 && ((IVulnerableEntity) e).getHealth() <60
        , ImageCache.loadImage("sprites/breakablebarrier_2.png", PlayerRenderer.class));
        addRenderingCondition(e -> ((IVulnerableEntity) e).getHealth() >= 0 && ((IVulnerableEntity) e).getHealth() <30
        , ImageCache.loadImage("sprites/breakablebarrier_3.png", PlayerRenderer.class));
    }
}
