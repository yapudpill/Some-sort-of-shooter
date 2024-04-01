package gui.ingame.effects;

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

public class EffectsPaneLayer extends JPanel implements IUpdateable {
    GameModel gameModel;
    ScaleLayout scaleLayout;
    private final Map<ICombatEntity, AimArrow> combatEntitiesAimMap = new ConcurrentHashMap<>();

    public EffectsPaneLayer(GameModel gameModel, ScaleSupplier scaleSupplier) {
        super();
        this.gameModel = gameModel;
        this.setOpaque(false);
        this.scaleLayout = new ScaleLayout(scaleSupplier);
        setLayout(scaleLayout);

        JLabel testLabel = new JLabel("Swoosh");
        this.add(testLabel);
    }

    private void addArrowForCombat(ICombatEntity entity) {
        AimArrow aimArrow = new AimArrow(entity);
        combatEntitiesAimMap.put(entity, aimArrow);
        this.add(aimArrow);
    }

    private void removeArrowForCombat(ICombatEntity entity) {
        AimArrow aimArrow = combatEntitiesAimMap.remove(entity);
        if (aimArrow != null) this.remove(aimArrow);
    }

    @Override
    public void update() {
        Set<ICombatEntity> combatEntities = new HashSet<>();
        for (IEntity iEntity : gameModel.getEntitySet()) {
            if (iEntity instanceof ICombatEntity iCombatEntity) combatEntities.add(iCombatEntity);
        }
        // Synchronize the sets and maps
        SetToMapSynchronisator.synchroniseCollectionToMap(combatEntities,
                combatEntitiesAimMap,
                this::addArrowForCombat,
                this::removeArrowForCombat);

        doLayout();
        repaint();
    }
}
