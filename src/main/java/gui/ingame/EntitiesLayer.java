package gui.ingame;

import gui.ScaleLayout;
import gui.ScaleSupplier;
import gui.ingame.entity.AbstractEntityRenderer;
import gui.ingame.entity.EntityRendererFactory;
import model.ingame.entity.IEntity;
import util.IUpdateable;
import util.SetToMapSynchronisator;

import javax.swing.*;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class EntitiesLayer extends JPanel implements IUpdateable {
    private final Map<IEntity, AbstractEntityRenderer> modelRendererMap = new ConcurrentHashMap<>();
    private final Set<IEntity> entityModelSet;

    public EntitiesLayer(Set<IEntity> entityModelSet, ScaleSupplier scaleSupplier) {
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
        if (removedRenderer != null) remove(removedRenderer);
    }

    @Override
    public void update(double delta) {
        // Synchronize the sets and maps
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
