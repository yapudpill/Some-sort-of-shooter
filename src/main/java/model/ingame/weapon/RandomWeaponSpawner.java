package model.ingame.weapon;

import model.ingame.EntitySpawner;
import model.ingame.GameModel;
import model.ingame.entity.WeaponEntity;
import util.Coordinates;
import util.IUpdateable;

import java.util.List;
import java.util.Random;

public class RandomWeaponSpawner extends EntitySpawner implements IUpdateable {
    public static final int WEAPON_SPAWN_COOLDOWN = 5;
    public static final List<WeaponFactory> availableWeaponsFactories = List.of(
        PistolModel::new,
        KnifeWeapon::new,
        RocketLauncher::new,
        ShotGun::new,
        RubberWeapon::new,
        SimpleTrapPlacer::new
        );

    private final Random rng = new Random();
    private double spawnCooldown = 0;

    public RandomWeaponSpawner(GameModel gameModel) {
        super(gameModel);
    }

    @Override
    public void update(double delta) {
        spawnCooldown -= delta;
        if (spawnCooldown <= 0) {
            double x = rng.nextDouble(gameModel.getMapModel().getWidth());
            double y = rng.nextDouble(gameModel.getMapModel().getHeight());
            // Move to the next walkable tile
            do {
                x++;
                if (x >= gameModel.getMapModel().getWidth()) {
                    x = 0;
                    y++;
                    if (y >= gameModel.getMapModel().getHeight()) {
                        y = 0;
                    }
                }
            } while (!gameModel.getMapModel().getTile((int) x, (int) y).canEnter(gameModel.getPlayer()));
            spawnEntity(x, y);
            spawnCooldown = WEAPON_SPAWN_COOLDOWN;
        }
    }

    @Override
    public WeaponEntity spawnEntity(double x, double y) {
       WeaponEntity entity = (WeaponEntity) super.spawnEntity(x, y);
        gameModel.addEntity(entity);
        return entity;
    }

    @Override
    protected WeaponEntity makeEntity(double x, double y) {
        WeaponModel weapon = availableWeaponsFactories.get(rng.nextInt(availableWeaponsFactories.size())).createWeapon(null, gameModel);
        return new WeaponEntity(new Coordinates(x, y), weapon, gameModel);
    }
}
