package model.ingame.entity.behavior;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.ModelTimer;

public class FloodFillPathFinder {
    private NodeGrid nodeGrid;
    private List<Coordinates> targets;
    private ModelTimer updateTimer;
    private boolean reachTarget = true;

    public FloodFillPathFinder(GameModel gameModel, double updateDelay) {
        this.nodeGrid = new NodeGrid(gameModel.getMapModel());
        this.updateTimer = new ModelTimer(updateDelay, true, this::fill, gameModel);
    }

    public void fill() {
        nodeGrid.reset();
        Queue<Coordinates> queue = new LinkedList<>();
        for (Coordinates pos : targets) {
            queue.add(pos);
        }
        int currentValue = 1;
        if(!reachTarget) currentValue = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Coordinates currentPos = queue.poll();
                int x = (int) currentPos.x;
                int y = (int) currentPos.y;

                if (nodeGrid.getNode(x, y).getValue() != -1) {
                    continue;
                }

                // Set the value of the current node
                nodeGrid.getNode(x, y).setValue(currentValue);

                // Add adjacent nodes to the queueq
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
                if (isValidCoordinate(x + i, y + j)) queue.add(new Coordinates(x + i, y + j));
            }
        }
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < nodeGrid.getWidth() && y >= 0 && y < nodeGrid.getHeight()
                && nodeGrid.getNode(x, y).isWalkable();
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
                if (isValidCoordinate(newX, newY) && nodeGrid.getNode(newX, newY).getValue() < lowestValue && nodeGrid.getNode(newX, newY).getValue() != 0) {
                    lowestValue = nodeGrid.getNode(newX, newY).getValue();
                    lowestPos = nodeGrid.getNode(newX, newY).getCoordinates();
                }
            }
        }
        return lowestPos;
    }

    public void setTargets(List<Coordinates> targets) {
        this.targets = targets;
    }

    public void setTarget(Coordinates target) {
        this.targets = List.of(target);
    }

    public List<Coordinates> getTargets(){
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

    public void reachTarget(boolean reachTarget){
        this.reachTarget = reachTarget;
    }

    public boolean doesReachTarget() {
        return reachTarget;
    }
}
