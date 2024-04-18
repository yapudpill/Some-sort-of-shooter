package gui.ingame.entity;

import javax.swing.JComponent;

import gui.IScalableComponent;
import model.ingame.entity.EntityModel;
import util.Coordinates;

public abstract class AbstractEntityRenderer extends JComponent implements IScalableComponent {
    protected final EntityModel entityModel;

    public AbstractEntityRenderer(EntityModel entityModel) {
        this.entityModel = entityModel;
    }

    public Coordinates getOriginalSize() {
        return new Coordinates(entityModel.getWidth(), entityModel.getHeight());
    }

    @Override
    public Coordinates getOriginalPosition() {
        // We translate the position so that the entity sprite is centered around its true position
        return entityModel.getPos().add(getOriginalSize().multiply(-0.5));
    }
}
