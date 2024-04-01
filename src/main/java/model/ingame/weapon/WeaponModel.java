package model.ingame.weapon;

import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.entity.ICombatEntity;
import model.ingame.physics.PhysicsEngineModel;
import util.ModelTimer;

public abstract class WeaponModel {
    protected final PhysicsEngineModel physicsEngine;
    protected final String name;
    protected final String identifier;
    protected ICombatEntity owner;
    protected int coolDown;
    protected ModelTimer coolDownTimer;
    protected Coordinates directionVector;
    protected GameModel gameModel;

    public WeaponModel(String name, String identifier, GameModel gameModel, ICombatEntity owner, int coolDown) {
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

    public abstract boolean attack();

    public ICombatEntity getOwner() {
        return owner;
    }

    public void setOwner(ICombatEntity owner) {
        this.owner = owner;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public boolean isCoolingDown() {
        return coolDownTimer.isRunning();
    }

    private void cooldownEndedCallback() {}

    public boolean usesDirectionVector() {
        return true;
    }

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
