package gui.ingame;

import gui.ProportionalScalerLayout;
import gui.ScaleSupplier;
import gui.ingame.entity.AbstractEntityRenderer;
import gui.ingame.entity.EntityRendererFactory;
import model.ingame.IUpdateable;
import model.ingame.entity.IEntity;
import model.ingame.entity.PlayerModel;
import util.SetToMapSynchronisator;

import javax.swing.*;

import java.util.HashSet;
import java.util.LinkedHashMap;
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
        // temporary code, should be replaced with a composite pattern to unpack entities and render them
        HashSet<IEntity> newEntityModelSet = new HashSet<>();
        PlayerModel player = null;
        for(IEntity entity : entityModelSet) {
            if(entity instanceof PlayerModel p) player = p;
            newEntityModelSet.add(entity);
        }
        if (player.getWeapon() != null) {
            for (IEntity entity : player.getWeapon().getProjectiles()) {
                newEntityModelSet.add(entity);
            }
        }
        entityModelSet.clear();
        entityModelSet.addAll(newEntityModelSet);
        SetToMapSynchronisator.synchroniseSetToMap(entityModelSet,
                entityModelRendererMap,
                this::addRendererForEntity,
                this::removeRendererOfEntity);
        entitiesPanel.repaint();
        entitiesPanel.doLayout();
    }
}
