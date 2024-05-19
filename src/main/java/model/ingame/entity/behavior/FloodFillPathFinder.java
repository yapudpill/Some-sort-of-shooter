package model.ingame.entity.behavior;

import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.entity.BreakableBarrier;
import model.ingame.entity.IEntity;
import model.ingame.entity.IMobileEntity;
import model.ingame.physics.MovementHandler;
import util.Coordinates;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Predicate;

/**
 * This class is used to find the shortest path to a target using a flood fill algorithm.
 */
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
                if (i == j || i == -j) continue;
                if (isValidCoordinate(x + i, y + j)) {
                    queue.add(new Coordinates(x + i, y + j));
                }
            }
        }
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < nodeGrid.getWidth() && y >= 0 && y < nodeGrid.getHeight()
                && (gameModel.getMapModel().getTile(x, y).canEnter(entityFinder)
                || gameModel.getMapModel().getTile(x, y).getCollidablesSet().stream().anyMatch(e -> e instanceof BreakableBarrier));
    }

    public Coordinates getNextNode(int x, int y, boolean chase) {
        int lowestValue = Integer.MAX_VALUE;
        Coordinates lowestPos = new Coordinates(x, y);
        int highestValue = Integer.MIN_VALUE;
        Coordinates highestPos = new Coordinates(x, y);
        int tmp;
        // use for loop
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newX = x + i;
                int newY = y + j;
                if (!isValidCoordinate(newX, newY)){
                    continue;
                }
                tmp = nodeGrid.getNode(newX, newY).getValue();
                if (tmp < lowestValue && tmp != 0) {
                    lowestValue = tmp;
                    lowestPos = nodeGrid.getNode(newX, newY).getCoordinates();
                }
                if (!chase && tmp > highestValue && tmp < 100) {
                    highestValue = tmp;
                    highestPos = nodeGrid.getNode(newX, newY).getCoordinates();
                }
            }
        }
        if (chase) return lowestPos;
        else return (lowestValue < 4)?lowestPos:highestPos;
    }

    public void handlePathFindingUpdate(IMobileEntity entity, Coordinates target, boolean chase){
            this.setTarget(target);
            Coordinates pos = entity.getPos();
            MovementHandler movementHandler = entity.getMovementHandler();
            if (!this.isRunning()) this.start();
            Coordinates nextCoord = this.getNextNode((int) pos.x(), (int) pos.y(), chase);
            if (pos.isInCenter() || !movementHandler.isMoving())
             movementHandler.setDirectionVector(new Coordinates(nextCoord.x() - pos.x(), nextCoord.y() - pos.y()));
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
