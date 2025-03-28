package gui.ingame.effects;

import gui.ScaleLayout;
import model.ingame.GameModel;
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
 * Layer that displays effects on top of the game entities. Currently, only displays the aim arrow.
 */
public class EffectsLayer extends JPanel implements IUpdateable {
    private final Map<ICombatEntity, AimArrow> combatEntitiesAimMap = new ConcurrentHashMap<>();
    private final GameModel gameModel;

    public EffectsLayer(GameModel gameModel, IntSupplier scaleSupplier) {
        this.gameModel = gameModel;
        this.setOpaque(false);
        setLayout(new ScaleLayout(scaleSupplier));
    }

    private void addArrowForCombat(ICombatEntity entity) {
        AimArrow aimArrow = new AimArrow(entity);
        combatEntitiesAimMap.put(entity, aimArrow);
        this.add(aimArrow);
    }

    private void removeArrowForCombat(ICombatEntity entity) {
        AimArrow aimArrow = combatEntitiesAimMap.remove(entity);
        if (aimArrow != null) {
            this.remove(aimArrow);
        }
    }

    @Override
    public void update(double delta) {
        Set<ICombatEntity> combatEntities = new HashSet<>();
        for (IEntity iEntity : gameModel.getEntitySet()) {
            if (iEntity instanceof ICombatEntity iCombatEntity) {
                combatEntities.add(iCombatEntity);
            }
        }
        // Synchronize the sets and maps
        SetToMapSynchronisator.synchronise(combatEntities,
                combatEntitiesAimMap,
                this::addArrowForCombat,
                this::removeArrowForCombat);

        doLayout();
        repaint();
    }
}
