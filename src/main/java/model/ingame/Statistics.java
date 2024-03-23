package model.ingame;

public class Statistics {
    public final String mapName;
    public int killedEnemies, nbAttacks;
    public long survivedFrames;

    public Statistics(String mapName) {
        this.mapName = mapName;
    }
}
