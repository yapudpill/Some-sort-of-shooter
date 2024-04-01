package gui.ingame.footprints;

import gui.ScaleLayout;
import gui.ScaleSupplier;
import model.ingame.GameModel;
import model.ingame.IUpdateable;
import model.ingame.entity.ICombatEntity;
import model.ingame.entity.IEntity;
import util.SetToMapSynchronisator;

import javax.swing.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class FootprintsLayer extends JPanel implements IUpdateable {
    GameModel gameModel;
    ScaleLayout scaleLayout;
    private final Map<ICombatEntity, FootprintManager> combatEntitiesFootprintMap = new ConcurrentHashMap<>();

    public FootprintsLayer(GameModel gameModel, ScaleSupplier scaleSupplier) {
        super();
        this.gameModel = gameModel;
        this.setOpaque(false);
        this.scaleLayout = new ScaleLayout(scaleSupplier);
        setLayout(scaleLayout);
    }

    @Override
    public void update() {
        Set<ICombatEntity> combatEntities = new HashSet<>();
        for (IEntity iEntity : gameModel.getEntitySet()) {
            if (iEntity instanceof ICombatEntity iCombatEntity) combatEntities.add(iCombatEntity);
        }

        SetToMapSynchronisator.synchroniseCollectionToMap(combatEntities,
                combatEntitiesFootprintMap,
                this::addFootprintSpawner,
                // Don't immediately remove the footprints, they will fade out thanks to the FootprintManager
                this::stopFootprintSpawner);


        for (IUpdateable footprintManager : combatEntitiesFootprintMap.values()) {
            footprintManager.update();
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
        if (footprintManager != null) footprintManager.stop();
    }

    public void removeFootprintSpawner(ICombatEntity iCombatEntity) {
        combatEntitiesFootprintMap.remove(iCombatEntity);
    }
}
