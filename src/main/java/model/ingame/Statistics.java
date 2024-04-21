package model.ingame;

import util.Resource;

public class Statistics {
    public final Resource map;
    public int killedEnemies, nbAttacks;
    public double survivedTime;

    Statistics(Resource map) {
        this.map = map;
    }
}
