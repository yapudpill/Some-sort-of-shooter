package model.ingame;

import util.Resource;

public class Statistics {
    public final Resource map;
    public int killedEnemies, nbAttacks;
    public long survivedFrames;

    public Statistics(Resource map) {
        this.map = map;
    }
}
