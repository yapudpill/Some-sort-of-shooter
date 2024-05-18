package gui.ingame.footprints;

import gui.ScaleLayout;
import model.ingame.entity.ICombatEntity;
import model.ingame.entity.IEntity;
import util.IUpdateable;
import util.SetToMapSynchronisator;

import javax.swing.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntSupplier;

/**
 * Panel layer for rendering footprints of entities.
 */
public class FootprintsLayer extends JPanel implements IUpdateable {
    private final Map<ICombatEntity, FootprintManager> combatEntitiesFootprintMap = new ConcurrentHashMap<>();
    private final Set<IEntity> entityModelSet;

    public FootprintsLayer(Set<IEntity> entityModelSet, IntSupplier scaleSupplier) {
        this.entityModelSet = entityModelSet;
        setLayout(new ScaleLayout(scaleSupplier));
        setOpaque(false);
    }

    @Override
    public void update(double delta) {
        Set<ICombatEntity> combatEntities = new HashSet<>();
        for (IEntity entity : entityModelSet) {
            if (entity instanceof ICombatEntity combatEntity) {
                combatEntities.add(combatEntity);
            }
        }

        // Creates a FootprintManager for each combat entity that doesn't have one yet, and removes the FootprintManager
        // for each combat entity that doesn't exist anymore
        SetToMapSynchronisator.synchronise(combatEntities,
            combatEntitiesFootprintMap,
            this::addFootprintSpawner,
            this::stopFootprintSpawner // Don't immediately remove the footprints, they will fade out thanks to the FootprintManager, so just stop them
        );

        for (IUpdateable footprintManager : combatEntitiesFootprintMap.values()) {
            footprintManager.update(delta);
        }

        doLayout();
        repaint();
    }

    private void addFootprintSpawner(ICombatEntity iCombatEntity) {
        FootprintManager footprintManager = new FootprintManager(this, iCombatEntity);
        combatEntitiesFootprintMap.put(iCombatEntity, footprintManager);
    }

    private void stopFootprintSpawner(ICombatEntity iCombatEntity) {
        FootprintManager footprintManager = combatEntitiesFootprintMap.get(iCombatEntity);
        if (footprintManager != null) {
            footprintManager.stop();
        }
    }

    public void removeFootprintSpawner(ICombatEntity iCombatEntity) {
        combatEntitiesFootprintMap.remove(iCombatEntity);
    }
}
