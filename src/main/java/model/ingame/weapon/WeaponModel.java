package model.ingame.weapon;

import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.entity.ICombatEntity;
import util.Coordinates;

/**
 * Abstract weapon model. All weapons inherit from this class.
 */
public abstract class WeaponModel {
    protected final String name, identifier;
    protected ICombatEntity owner;
    protected ModelTimer coolDownTimer;
    protected Coordinates directionVector;
    protected GameModel gameModel;
    protected double coolDownDelay;

    public WeaponModel(String name, String identifier,  ICombatEntity owner, GameModel gameModel,double coolDown) {
        this.name = name;
        this.gameModel = gameModel;
        this.owner = owner;
        this.identifier = identifier;
        coolDownTimer = new ModelTimer(coolDown, false, () -> {}, gameModel);
        coolDownDelay =  coolDown;
    }

    public abstract boolean attack();

    public ICombatEntity getOwner() {
        return owner;
    }

    public void setOwner(ICombatEntity owner) {
        this.owner = owner;
    }

    public boolean isCoolingDown() {
        return coolDownTimer.isRunning();
    }

    public boolean usesDirectionVector() {
        return true;
    }

    public Coordinates getDirectionVector() {
        return directionVector;
    }

    public void setDirectionVector(Coordinates directionVector) {
        this.directionVector = directionVector;
    }

    public String getName() {
        return name;
    }

    public String getIdentifier() {
        return identifier;
    }

    public double getTimeLeft() {
        return coolDownTimer.getTimeLeft();
    }

    public double getCoolDownDelay() {
        return coolDownDelay;
    }

}
