package model.ingame;

import util.Resource;

/**
 * A class that holds statistics about a game session.
 */
public class Statistics {
    public final Resource mapResource;
    public final Resource scenarioResource;
    public int killedEnemies, nbAttacks;
    public double survivedTime;

    public Statistics(Resource mapResource, Resource scenarioResource) {
        this.mapResource = mapResource;
        this.scenarioResource = scenarioResource;
    }
}
