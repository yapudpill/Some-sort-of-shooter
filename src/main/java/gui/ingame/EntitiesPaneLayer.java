package gui.ingame;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import gui.ScaleLayout;
import gui.ScaleSupplier;
import gui.ingame.entity.AbstractEntityRenderer;
import gui.ingame.entity.EntityRendererFactory;
import model.ingame.IUpdateable;
import model.ingame.entity.IEntity;
import util.SetToMapSynchronisator;


public class EntitiesPaneLayer implements IUpdateable {
    private final JPanel entitiesPanel;

    private final Set<IEntity> entityModelSet;
    private final ScaleLayout scaleLayout;

    private final Map<IEntity, AbstractEntityRenderer> entityModelRendererMap = new ConcurrentHashMap<>();

    public EntitiesPaneLayer(Set<IEntity> entityModelSet, ScaleSupplier scaleSupplier) {
        this.entityModelSet = entityModelSet;
        this.entitiesPanel = new JPanel();
        this.scaleLayout = new ScaleLayout(scaleSupplier);
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
        if (entityRenderer != null) {
            entityModelRendererMap.put(entityModel, entityRenderer);
            entitiesPanel.add(entityRenderer);
        }
    }

    private void removeRendererOfEntity(IEntity entityModel) {
        AbstractEntityRenderer removedEntityRenderer = entityModelRendererMap.remove(entityModel);
        if (removedEntityRenderer != null) entitiesPanel.remove(removedEntityRenderer);
    }
    @Override
    public void update() {
        // Synchronize the sets and maps
        SetToMapSynchronisator.synchroniseSetToMap(entityModelSet,
                entityModelRendererMap,
                this::addRendererForEntity,
                this::removeRendererOfEntity);

        // Repaint and do layout
        entitiesPanel.repaint();
        entitiesPanel.doLayout();
    }
}
