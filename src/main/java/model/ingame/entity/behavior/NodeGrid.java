package model.ingame.entity.behavior;

import model.ingame.Coordinates;
import model.level.MapModel;

public class NodeGrid {
    private final Node[][] nodes;

    public NodeGrid(MapModel map){
        nodes = new Node[map.getWidth()][map.getHeight()];
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                nodes[x][y] = new Node(new Coordinates(x+0.5,y+0.5), -1);
            }
        }
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
