package gui.ingame.entity;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Map;
import java.util.function.Predicate;

import model.ingame.entity.BreakableBarrier;
import model.ingame.entity.IEntity;

/**
 * Renderer for the BreakableBarrier entity. Changes the displayed animation based on the health of the wall.
 */
public class ConditionalRenderer extends EntityRenderer {
    private final Map<Predicate<IEntity>, Image> conditionsToRendering;

    public ConditionalRenderer(BreakableBarrier entity, Map<Predicate<IEntity>, Image> conditionsToRendering) {
        super(entity);
        this.conditionsToRendering = conditionsToRendering;
    }

    @Override
    protected void paintComponent(Graphics g) {
        for(Predicate<IEntity> condition : conditionsToRendering.keySet()){
            if(condition.test(entity)){
                g.drawImage(conditionsToRendering.get(condition), 0, 0, getWidth(), getHeight(), null);
                return;
            }
        }
    }
}
