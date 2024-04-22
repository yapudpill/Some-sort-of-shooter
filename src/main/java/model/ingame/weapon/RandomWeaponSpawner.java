package model.ingame.weapon;

import model.ingame.EntitySpawner;
import model.ingame.GameModel;
import model.ingame.entity.WeaponEntity;
import util.Coordinates;

import java.util.List;

public class RandomWeaponSpawner extends EntitySpawner {
    private static final List<WeaponConstructor> availableWeapons = List.of(
        PistolModel::new,
        KnifeWeapon::new,
        RocketLauncher::new,
        ShotGun::new,
        RubberWeapon::new,
        SimpleTrapPlacer::new
    );
    private static final int nbWeapons = availableWeapons.size();

    public RandomWeaponSpawner(GameModel gameModel, double cooldown) {
        super(gameModel, cooldown);
    }

    @Override
    protected WeaponEntity makeEntity(Coordinates pos) {
        WeaponConstructor constructor = availableWeapons.get(rng.nextInt(nbWeapons));
        WeaponModel weapon = constructor.createWeapon(null, gameModel);
        return new WeaponEntity(pos, weapon, gameModel);
    }

    @Override
    protected boolean validSpawn(double x, double y) {
        return mapModel.getTile((int) x, (int) y).canEnter(gameModel.getPlayer());
    }
}
