package gui.ingame.entity;

import gui.IScalableComponent;
import model.ingame.entity.IEntity;
import util.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

/**
 * Abstract class for rendering entities, providing the size and position of the **displayed** entity **before** being scaled.
 */
public abstract class AbstractEntityRenderer extends JComponent implements IScalableComponent {
    protected final IEntity entity;
    private final Map<Predicate<IEntity>, BufferedImage> conditionsToRendering = new ConcurrentHashMap<>();

    public AbstractEntityRenderer(IEntity entity) {
        this.entity = entity;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Predicate<IEntity> condition : conditionsToRendering.keySet()){
            if(condition.test(entity)){
                g.drawImage(conditionsToRendering.get(condition), 0, 0, getWidth(), getHeight(), null);
                return;
            }
        }
    }

    @Override
    public Coordinates getOriginalSize() {
        return new Coordinates(entity.getWidth(), entity.getHeight());
    }

    @Override
    public Coordinates getOriginalPosition() {
        // We translate the position so that the entity sprite is centered around its true position
        return entity.getPos().add(getOriginalSize().multiply(-0.5));
    }

    public void addRenderingCondition(Predicate<IEntity> condition, BufferedImage image) {
        conditionsToRendering.put(condition, image);
    }
}
