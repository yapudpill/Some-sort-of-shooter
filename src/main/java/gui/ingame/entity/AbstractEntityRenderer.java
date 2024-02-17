package gui.ingame.entity;

import gui.ScalableComponent;
import model.ingame.Coordinates;
import model.ingame.IUpdateable;
import model.ingame.entity.EntityModel;

import javax.swing.*;

public abstract class AbstractEntityRenderer extends JComponent implements IUpdateable, ScalableComponent {
    private final EntityModel entityModel;

    public AbstractEntityRenderer(EntityModel entityModel) {
        this.entityModel = entityModel;
    }

    @Override
    public void update() {
        // TODO
    }

    @Override
    public Coordinates getOriginalPosition() {
        // We translate the position so that the entity sprite is centered around its true position
        return entityModel.getPos().translate(getOriginalSize().multiply(-0.5));
    }
}
