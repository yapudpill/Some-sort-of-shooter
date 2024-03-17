package model.ingame.entity;

import java.util.Random;

import model.ingame.Coordinates;
import model.ingame.physics.DamageListener;
import model.ingame.GameModel;
import model.ingame.physics.MovementHandlerModel;
import model.ingame.weapon.KnifeWeapon;

public class WalkingEnemyModel extends CombatEntityModel implements IEffectEntity {
    private final PlayerModel player;
    Random rng = new Random();

    public WalkingEnemyModel(Coordinates pos, GameModel gameModel) {
        super(50, 0.8, 0.8, gameModel);
        this.player = gameModel.getPlayer();
        this.pos = pos;
        movementHandler = new MovementHandlerModel<WalkingEnemyModel>(this, gameModel.getPhysicsEngine());
        movementHandler.setSpeed(0.01);
        //addCollisionListener(new DamageListener(10)); //TODO: damages should not be hard coded
        addCollisionListener(e -> {
            if (e.getSource() instanceof PlayerModel) {
                attack();
            }
        });
        setWeapon(new KnifeWeapon(this, gameModel));
    }

    @Override
    public boolean canApplyEffect(IVulnerableEntity target) {
        return target instanceof PlayerModel;
    }

    @Override
    public void update() {
        Coordinates pPos = player.getPos();
        movementHandler.setDirectionVector(new Coordinates(pPos.x - pos.x   , pPos.y - pos.y ));
        super.update();
    }
}
