package gui.ingame;

import gui.ProportionalScalerLayout;
import gui.ScaleSupplier;
import gui.ingame.entity.AbstractEntityRenderer;
import gui.ingame.entity.EntityRendererFactory;
import model.ingame.IUpdateable;
import model.ingame.entity.ICompositeEntity;
import model.ingame.entity.IEntity;
import model.ingame.entity.PlayerModel;
import util.SetToMapSynchronisator;

import javax.swing.*;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;


public class EntitiesPaneLayer implements IUpdateable {
    private final JPanel entitiesPanel;

    private final Set<IEntity> entityModelSet;
    private final ProportionalScalerLayout scaleLayout;

    private final LinkedHashMap<IEntity, AbstractEntityRenderer> entityModelRendererMap = new LinkedHashMap<>();

    public EntitiesPaneLayer(Set<IEntity> entityModelSet, ScaleSupplier scaleSupplier) {
        this.entityModelSet = entityModelSet;
        this.entitiesPanel = new JPanel();
        this.scaleLayout = new ProportionalScalerLayout(scaleSupplier);
        this.entitiesPanel.setLayout(scaleLayout);
        entitiesPanel.setOpaque(false);
        // add button to the panel
        JButton button = new JButton("Button");
        entitiesPanel.add(button);
    }

    /**
     * @return the JComponent of this layer
     */
    public JComponent getJComponent() {
        return entitiesPanel;
    }

    private void addRendererForEntity(IEntity entityModel) {
        AbstractEntityRenderer entityRenderer = EntityRendererFactory.makeEntityRenderer(entityModel);
        entityModelRendererMap.put(entityModel, entityRenderer);
        entitiesPanel.add(entityRenderer);
    }

    private void removeRendererOfEntity(IEntity entityModel) {
        AbstractEntityRenderer removedEntityRenderer = entityModelRendererMap.remove(entityModel);
        if (removedEntityRenderer != null) entitiesPanel.remove(removedEntityRenderer);
    }
    @Override
    public void update() {
        // Create a copy of the existing entityModelSet
        Set<IEntity> entitySetCopy = new HashSet<>(entityModelSet);

        // Clear the existing entityModelSet and entityModelRendererMap
        entityModelSet.clear();

        // Add all entities to the entityModelSet
        for (IEntity entity : entitySetCopy) {
            addEntityAndChildren(entity);
        }

        // Synchronize the sets and maps
        SetToMapSynchronisator.synchroniseSetToMap(entityModelSet,
                entityModelRendererMap,
                this::addRendererForEntity,
                this::removeRendererOfEntity);

        // Repaint and do layout
        entitiesPanel.repaint();
        entitiesPanel.doLayout();
    }

    private void addEntityAndChildren(IEntity entity) {
        // Add the current entity to the set
        entityModelSet.add(entity);

        // Check if the entity is a composite entity
        if (entity instanceof ICompositeEntity) {
            // Recursively add children of the composite entity
            List<? extends IEntity> children = ((ICompositeEntity) entity).getChildren();
            for (IEntity child : children) {
                addEntityAndChildren(child);
            }
        }
    }
}
