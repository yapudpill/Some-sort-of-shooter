package gui.ingame.entity;

import javax.swing.JComponent;

import gui.IScalableComponent;
import model.ingame.entity.IEntity;
import util.Coordinates;

public abstract class AbstractEntityRenderer extends JComponent implements IScalableComponent {
    protected final IEntity entity;

    public AbstractEntityRenderer(IEntity entity) {
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
