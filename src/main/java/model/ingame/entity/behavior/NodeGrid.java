package model.ingame.entity.behavior;

import java.util.ArrayList;
import java.util.List;

import model.ingame.Coordinates;
import model.level.MapModel;

public class NodeGrid {
    private final Node[][] nodes;

    public NodeGrid(MapModel map){
        nodes = new Node[map.getWidth()][map.getHeight()];
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                nodes[x][y] = new Node(new Coordinates(x+0.5,y+0.5), -1);
                nodes[x][y].setWalkable(map.isWalkableAt(x, y));
            }
        }
        print();
    }

    public Node getNode(int x, int y) {
        return nodes[x][y];
    }

    public void setNode(int x, int y, Node node) {
        nodes[x][y] = node;
    }

    public int getWidth() {
        return nodes.length;
    }

    public int getHeight() {
        return nodes[0].length;
    }

    public List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int x = (int) node.getCoordinates().x;
        int y = (int) node.getCoordinates().y;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue;
                if (isValidCoordinate(x + i, y + j)) neighbors.add(nodes[x + i][y + j]);
            }
        }
        return neighbors;
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < nodes.length && y >= 0 && y < nodes[0].length;
    }

    public void reset() {
        for (int x = 0; x < nodes.length; x++) {
            for (int y = 0; y < nodes[0].length; y++) {
                nodes[x][y].setValue(-1);
            }
        }
    }

    public void print(){
        for (int y = 0; y < nodes[0].length; y++) {
            for (int x = 0; x < nodes.length; x++) {
                System.out.print(nodes[x][y].getValue() + " ");
            }
            System.out.println();
        }
    }
}
