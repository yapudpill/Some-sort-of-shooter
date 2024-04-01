package gui.ingame.entity;

import javax.swing.JComponent;

import gui.ScalableComponent;
import model.ingame.Coordinates;
import model.ingame.IUpdateable;
import model.ingame.entity.EntityModel;

public abstract class AbstractEntityRenderer extends JComponent implements IUpdateable, ScalableComponent {
    protected final EntityModel entityModel;

    public AbstractEntityRenderer(EntityModel entityModel) {
        this.entityModel = entityModel;
    }

    @Override
    public void update() {
    }

    public Coordinates getOriginalSize() {
        return new Coordinates(entityModel.getWidth(), entityModel.getHeight());
    }

    @Override
    public Coordinates getOriginalPosition() {
        // We translate the position so that the entity sprite is centered around its true position
        return entityModel.getPos().translate(getOriginalSize().multiply(-0.5));
    }
}
