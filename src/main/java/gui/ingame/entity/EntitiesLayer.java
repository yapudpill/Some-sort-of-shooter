package gui.ingame.entity;

import gui.ScaleLayout;
import model.ingame.entity.IEntity;
import util.IUpdateable;
import util.SetToMapSynchronisator;

import javax.swing.*;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntSupplier;

/**
 * The layer that renders all the entities in the game. They are scaled using a {@link ScaleLayout}.
 * The entity renderers are synced with the model on each update.
 */
public class EntitiesLayer extends JPanel implements IUpdateable {
    private final Map<IEntity, AbstractEntityRenderer> modelRendererMap = new ConcurrentHashMap<>();
    private final Set<IEntity> entityModelSet;

    public EntitiesLayer(Set<IEntity> entityModelSet, IntSupplier scaleSupplier) {
        this.entityModelSet = entityModelSet;
        setLayout(new ScaleLayout(scaleSupplier));
        setOpaque(false);
    }

    private void addEntityRenderer(IEntity entityModel) {
        AbstractEntityRenderer renderer = EntityRendererFactory.make(entityModel);
        modelRendererMap.put(entityModel, renderer);
        add(renderer);
    }

    private void removeEntityRenderer(IEntity entityModel) {
        AbstractEntityRenderer removedRenderer = modelRendererMap.remove(entityModel);
        if (removedRenderer != null) {
            remove(removedRenderer);
        }
    }

    @Override
    public void update(double delta) {
        // Creates a renderer for each entity that doesn't have one yet, and removes the renderer for each entity that doesn't exist anymore
        SetToMapSynchronisator.synchronise(entityModelSet,
                modelRendererMap,
                this::addEntityRenderer,
                this::removeEntityRenderer);
        for (AbstractEntityRenderer renderer : modelRendererMap.values()) {
            if (renderer instanceof IUpdateable updateableRenderer) updateableRenderer.update(delta);
        }

        // Repaint and do layout
        repaint();
        doLayout();
    }
}
