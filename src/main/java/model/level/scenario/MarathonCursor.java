package model.level.scenario;

import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.entity.EntityConstructor;
import model.ingame.weapon.WeaponConstructor;
import util.MathTools;
import util.WeightedRandomGenerator;
import util.ZipToMap;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Cursor for a marathon scenario. Keeps track of the current difficulty, updates it, and spawns enemies and weapons
 * accordingly.
 */
public class MarathonCursor implements IScenarioCursor {
    private static final int MAX_DIFFICULTY = 100;
    private static final Random rng = new Random();

    private final GameModel gameModel;
    private final MarathonScenario scenario;
    private boolean isCoolingDown = true;
    private int cooldownDuration;
    private int waveDuration;
    private ModelTimer cooldownTimer;
    private ModelTimer waveTimer;
    private int rawDifficulty;

    private final WeightedRandomGenerator<EntityConstructor> enemyGenerator;
    private final WeightedRandomGenerator<WeaponConstructor> weaponGenerator;

    private final Queue<WeaponConstructor> weaponsQueue = new LinkedList<>();
    private final Queue<EntityConstructor> enemiesQueue = new LinkedList<>();
    private final Queue<EntityConstructor> miscEntitiesQueue = new LinkedList<>();

    public MarathonCursor(MarathonScenario scenario, GameModel gameModel) {
        this.scenario = scenario;
        this.gameModel = gameModel;

        this.enemyGenerator = new WeightedRandomGenerator<>();
        this.weaponGenerator = new WeightedRandomGenerator<>();

        this.cooldownDuration = scenario.initialWaveCooldown();
        this.waveDuration = scenario.initialWaveDuration();

        ModelTimer updateDifficultyTimer = new ModelTimer(1, true, this::updateDifficulty, gameModel);
        updateDifficultyTimer.start();

        ModelTimer spawnTimer = new ModelTimer(1, true, this::spawnStuff, gameModel);
        spawnTimer.start();

        startCooldown();
    }

    private void spawnStuff() {
        if (!isCoolingDown) {
            enemiesQueue.addAll(enemyGenerator.nextElements(1));
        } else {
            weaponsQueue.addAll(weaponGenerator.nextElements(1));
        }
    }

    private void updateDifficulty() {
        int pick = rng.nextInt(12);
        int difficultyIncrease;
        if (pick < 6) {
            difficultyIncrease = 0;
        } else if (pick < 11) {
            difficultyIncrease = 1;
        } else {
            difficultyIncrease = -1;
        }

        rawDifficulty = Math.max(0, rawDifficulty + difficultyIncrease);

        if (difficultyIncrease != 0) {
            double enemyGeneratorP = adjustedDifficulty() / (2 * MAX_DIFFICULTY);


            Map<EntityConstructor, Double> nextEnemyRate = ZipToMap.zipToMap(
                    scenario.enemiesByDifficulty(),
                    MathTools.getBinomialProbabilities(scenario.enemiesByDifficulty().size(), enemyGeneratorP)
            );
            enemyGenerator.setElementRates(nextEnemyRate);

            double weaponsProba = 2. / scenario.weaponsByPower().size();
            weaponGenerator.setElementRates(scenario.weaponsByPower().stream().collect(Collectors.toMap(k -> k, k -> weaponsProba)));
        }
    }

    @Override
    public void update(double delta) {
    }

    private double adjustedDifficulty() {
        return Math.min(10 * Math.log(rawDifficulty + 1), MAX_DIFFICULTY);
    }

    private void startWave() {
        this.isCoolingDown = false;
        waveTimer = new ModelTimer(waveDuration, false, this::startCooldown, gameModel);
        waveTimer.start();
    }

    private void startCooldown() {
        this.isCoolingDown = true;
        for (int i = 0; i < 5; i++) { // Spawn 5 helper entities at the end of each wave
            miscEntitiesQueue.addAll(scenario.helperEntities());
        }
        cooldownTimer = new ModelTimer(cooldownDuration, false, this::startWave, gameModel);
        cooldownTimer.start();
    }


    @Override
    public WeaponConstructor nextWeapon() {
        return weaponsQueue.poll();
    }

    @Override
    public EntityConstructor nextEnemy() {
        if (isCoolingDown) {
            return null;
        }

        return enemiesQueue.poll();
    }

    @Override
    public EntityConstructor nextMiscEntity() {
        return miscEntitiesQueue.poll();
    }

    @Override
    public boolean isGameFinished() {
        return false;
    }
}
