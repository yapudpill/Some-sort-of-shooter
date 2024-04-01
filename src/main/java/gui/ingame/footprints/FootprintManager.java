package gui.ingame.footprints;

import model.ingame.Coordinates;
import model.ingame.IUpdateable;
import model.ingame.entity.ICombatEntity;

import java.util.LinkedList;
import java.util.List;

public class FootprintManager implements IUpdateable {
    private static final double FOOTPRINT_DISTANCE = 0.7;
    private final ICombatEntity entity;
    private final FootprintsLayer footprintsLayer;
    private final List<FootprintRenderer> placedFootprints = new LinkedList<>(); // LinkedList since we only act on the first and last elements
    private boolean stopped = false;
    private Coordinates lastPos;

    public FootprintManager(FootprintsLayer footprintsLayer, ICombatEntity entity) {
        this.footprintsLayer = footprintsLayer;
        this.entity = entity;
        this.lastPos = entity.getPos();
    }

    private void spawnFootprint() {
        System.out.println("spawning footprint");
        FootprintRenderer footprintRenderer = new FootprintRenderer(entity.getMovementHandler().getDirectionVector(), entity.getPos());
        placedFootprints.add(footprintRenderer);
        footprintsLayer.add(footprintRenderer);
    }

    private void removeOldFootprints() {
        if (!placedFootprints.isEmpty()) {
            FootprintRenderer oldestFootprint = placedFootprints.getFirst();
            if (oldestFootprint.hasFaded()) {
                footprintsLayer.remove(oldestFootprint);
                placedFootprints.remove(oldestFootprint);
            }
        }
    }

    public void stop() {
        stopped = true;
    }

    private void removeSelf() {
        footprintsLayer.removeFootprintSpawner(entity);
    }

    @Override
    public void update() {
        for (FootprintRenderer footprint : placedFootprints) footprint.update();
        if (!stopped) {
            if (lastPos.distance(entity.getPos()) >= FOOTPRINT_DISTANCE) {
                spawnFootprint();
                lastPos = entity.getPos();
            }
        }
        removeOldFootprints();
        if (stopped && placedFootprints.isEmpty()) removeSelf();
    }
}
