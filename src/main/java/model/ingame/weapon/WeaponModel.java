package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.entity.IEntity;
import model.ingame.physics.PhysicsEngineModel;
import util.ModelTimer;

public abstract class WeaponModel {
    protected final PhysicsEngineModel physicsEngine;
    protected final String name;
    protected final String identifier;
    protected IEntity owner;
    protected int coolDown;
    protected ModelTimer coolDownTimer;
    protected Coordinates directionVector;
    protected GameModel gameModel;

    public WeaponModel(String name, String identifier, GameModel gameModel, IEntity owner, int coolDown) {
        this.name = name;
        this.physicsEngine = gameModel.getPhysicsEngine();
        this.gameModel = gameModel;
        this.owner = owner;
        this.identifier = identifier;
        coolDownTimer = new ModelTimer(coolDown, this::cooldownEndedCallback, gameModel);
        coolDownTimer.setRepeats(false);
    }

    public String getName() {
        return name;
    }

    public abstract void attack();

    public IEntity getOwner() {
        return owner;
    }

    public void setOwner(IEntity owner) {
        this.owner = owner;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public boolean isCoolingDown() {
        return coolDownTimer.isRunning();
    }

    private void cooldownEndedCallback() {}

    public Coordinates getDirectionVector() {
        return directionVector;
    }

    public void setDirectionVector(Coordinates directionVector) {
        this.directionVector = directionVector;
    }

    public String getIdentifier() {
        return identifier;
    }
}
