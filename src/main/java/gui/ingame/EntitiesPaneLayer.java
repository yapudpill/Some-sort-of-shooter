package gui.ingame;

import gui.ProportionalScalerLayout;
import gui.ScaleSupplier;
import gui.ingame.entity.AbstractEntityRenderer;
import gui.ingame.entity.EntityRendererFactory;
import model.ingame.IUpdateable;
import model.ingame.entity.EntityModel;
import util.SetToMapSynchronisator;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.Set;


public class EntitiesPaneLayer implements IUpdateable {
    private final JPanel entitiesPanel;

    private final Set<EntityModel> entityModelSet;

    private final LinkedHashMap<EntityModel, AbstractEntityRenderer> entityModelRendererMap = new LinkedHashMap<>();

    public EntitiesPaneLayer(Set<EntityModel> entityModelSet, ScaleSupplier scaleSupplier) {
        this.entityModelSet = entityModelSet;
        this.entitiesPanel = new JPanel() {
            @Override
            public void paintComponent(java.awt.Graphics g) {
                super.paintComponent(g);
            }
        };
        this.entitiesPanel.setLayout(new ProportionalScalerLayout(scaleSupplier));
        entitiesPanel.setOpaque(false);
    }

    /**
     * @return the JComponent rendering the game
     */
    public JComponent getJComponent() {
        return entitiesPanel;
    }

    private void addRendererForEntity(EntityModel entityModel) {
        AbstractEntityRenderer entityRenderer = EntityRendererFactory.makeEntityRenderer(entityModel);
        entityModelRendererMap.put(entityModel, entityRenderer);
        entitiesPanel.add(entityRenderer);
    }

    private void removeRendererOfEntity(EntityModel entityModel) {
        AbstractEntityRenderer removedEntityRenderer = entityModelRendererMap.remove(entityModel);
        if (removedEntityRenderer != null) entitiesPanel.remove(removedEntityRenderer);
    }

    @Override
    public void update() {
        SetToMapSynchronisator.synchroniseSetToMap(entityModelSet,
                entityModelRendererMap,
                this::addRendererForEntity,
                this::removeRendererOfEntity);
    }
}
