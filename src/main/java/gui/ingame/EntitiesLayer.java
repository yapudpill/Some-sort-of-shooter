package gui.ingame;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JPanel;

import gui.ScaleLayout;
import gui.ScaleSupplier;
import gui.ingame.entity.AbstractEntityRenderer;
import gui.ingame.entity.EntityRendererFactory;
import util.IUpdateable;
import model.ingame.entity.IEntity;
import util.SetToMapSynchronisator;


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

        // Repaint and do layout
        repaint();
        doLayout();
    }
}
