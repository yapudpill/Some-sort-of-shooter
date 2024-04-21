package gui.ingame.footprints;

import util.Coordinates;
import util.IUpdateable;
import model.ingame.entity.ICombatEntity;

import java.util.LinkedList;
import java.util.List;

class FootprintManager implements IUpdateable {
    private static final double FOOTPRINT_DISTANCE = 0.7;

    private final List<FootprintRenderer> placedFootprints = new LinkedList<>(); // LinkedList since we only act on the first and last elements
    private final FootprintsLayer footprintsLayer;
    private final ICombatEntity entity;
    private Coordinates lastPos;
    private boolean stopped = false;

    FootprintManager(FootprintsLayer footprintsLayer, ICombatEntity entity) {
        this.footprintsLayer = footprintsLayer;
        this.entity = entity;
        this.lastPos = entity.getPos();
    }

    private void spawnFootprint() {
        FootprintRenderer footprintRenderer = new FootprintRenderer(
            entity.getMovementHandler().getDirectionVector(),
            entity.getPos()
        );
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

    void stop() {
        stopped = true;
    }

    private void removeSelf() {
        footprintsLayer.removeFootprintSpawner(entity);
    }

    @Override
    public void update(double delta) {
        for (FootprintRenderer footprint : placedFootprints) {
            footprint.update(delta);
        }
        removeOldFootprints();

        if (!stopped && lastPos.distance(entity.getPos()) >= FOOTPRINT_DISTANCE) {
            spawnFootprint();
            lastPos = entity.getPos();
        }

        if (stopped && placedFootprints.isEmpty()) {
            removeSelf();
        }
    }
}
