package gui.ingame.entity;

import javax.swing.JComponent;

import gui.IScalableComponent;
import model.ingame.entity.IEntity;
import util.Coordinates;

/**
 * Abstract class for rendering entities, providing the size and position of the **displayed** entity **before** being scaled.
 */
public class EntityRenderer extends JComponent implements IScalableComponent {
    protected final IEntity entity;

    public EntityRenderer(IEntity entity) {
        this.entity = entity;
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
}
