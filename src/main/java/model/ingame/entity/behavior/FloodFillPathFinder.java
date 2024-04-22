package model.ingame.entity.behavior;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Predicate;

import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.entity.IEntity;
import model.ingame.entity.IMobileEntity;
import model.ingame.physics.MovementHandler;
import util.Coordinates;

public class FloodFillPathFinder {
    private IEntity entityFinder;
    private NodeGrid nodeGrid;
    private List<Coordinates> targets;
    private ModelTimer updateTimer;
    private Predicate<Coordinates> shouldAvoid;
    private boolean reachTarget = true;
    private GameModel gameModel;

    public FloodFillPathFinder(GameModel gameModel, double updateDelay, IEntity entityFinder) {
        this.nodeGrid = new NodeGrid(gameModel.getMapModel());
        this.updateTimer = new ModelTimer(updateDelay, true, () -> fill(), gameModel);
        this.entityFinder = entityFinder;
        this.gameModel = gameModel;
    }

    public void fill() {
        nodeGrid.reset();
        Queue<Coordinates> queue = new LinkedList<>();
        for (Coordinates pos : targets) {
            queue.add(pos);
        }
        int currentValue = reachTarget ? 1 : 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Coordinates currentPos = queue.poll();
                int x = (int) currentPos.x();
                int y = (int) currentPos.y();

                if (nodeGrid.getNode(x, y).getValue() != -1) {
                    continue;
                }

                // Set the value of the current node
                if (shouldAvoid != null && shouldAvoid.test(currentPos)) {
                    nodeGrid.getNode(x, y).setValue(1000);
                } else {
                    nodeGrid.getNode(x, y).setValue(currentValue);
                }

                // Add adjacent nodes to the queue
                addAdjacentNodes(queue, x, y);
            }
            currentValue++;
        }
    }

    private void addAdjacentNodes(Queue<Coordinates> queue, int x, int y) {
        // Add adjacent nodes to the queue if they are within bounds and have not been visited
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                if (isValidCoordinate(x + i, y + j)) {
                    queue.add(new Coordinates(x + i, y + j));
                }
            }
        }
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < nodeGrid.getWidth() && y >= 0 && y < nodeGrid.getHeight()
                && gameModel.getMapModel().getTile(x, y).canEnter(entityFinder);
    }

    public Coordinates getLowestNodeAround(int x, int y) {
        int lowestValue = Integer.MAX_VALUE;
        Coordinates lowestPos = new Coordinates(x, y);
        // use for loop
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (Math.abs(i) == Math.abs(j)) continue;
                int newX = x + i;
                int newY = y + j;
                if (isValidCoordinate(newX, newY) && nodeGrid.getNode(newX, newY).getValue() < lowestValue
                && nodeGrid.getNode(newX, newY).getValue() != 0) {
                    lowestValue = nodeGrid.getNode(newX, newY).getValue();
                    lowestPos = nodeGrid.getNode(newX, newY).getCoordinates();
                }
            }
        }
        return lowestPos;
    }

    public void handlePathFindingUpdate(IMobileEntity entity, Coordinates target){
            this.setTarget(target);
            Coordinates pos = entity.getPos();
            MovementHandler movementHandler = entity.getMovementHandler();
            if (!this.isRunning()) this.start();
            Coordinates lowestCoord = this.getLowestNodeAround((int) pos.x(), (int) pos.y());
            if (pos.isInCenter() || !movementHandler.isMoving()) movementHandler.setDirectionVector(new Coordinates(lowestCoord.x() - pos.x(), lowestCoord.y() - pos.y()));
    }

    public void setTargets(List<Coordinates> targets) {
        this.targets = targets;
    }

    public void setTarget(Coordinates target) {
        this.targets = List.of(target);
    }

    public List<Coordinates> getTargets() {
        return targets;
    }

    public void start() {
        updateTimer.start();
    }

    public void stop() {
        updateTimer.stop();
    }

    public boolean isRunning() {
        return updateTimer.isRunning();
    }

    public void reachTarget(boolean reachTarget) {
        this.reachTarget = reachTarget;
    }

    public boolean doesReachTarget() {
        return reachTarget;
    }

    public void setAvoidPredicate(Predicate<Coordinates> shouldAvoid) {
        this.shouldAvoid = shouldAvoid;
    }
}
