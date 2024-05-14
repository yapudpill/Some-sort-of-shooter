package model.level.scenario;

import model.ingame.GameModel;
import model.ingame.ModelTimer;
import model.ingame.entity.IEntity.IEntityFactory;
import model.ingame.weapon.WeaponModel.IWeaponFactory;
import util.MathTools;
import util.ZipToMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.stream.IntStream;

import static model.ingame.entity.IEnemy.IEnemyFactory;

public class MarathonCursor implements IScenarioCursor {
    private static final int MAX_DIFFICULTY = 30;
    MarathonScenario scenario;
    boolean isCoolingDown = true;
    private int cooldownDuration;
    private int waveDuration;
    private ModelTimer cooldownTimer;
    private ModelTimer waveTimer;
    private int waveNumber = 0;
    private int rawDifficulty;
    private ModelTimer updateDifficultyTimer;
    private int time;
    private Random rng = new Random();
    private GameModel gameModel;

    private WeightedRandomElementGenerator<IEnemyFactory> enemyGenerator;
    private WeightedRandomElementGenerator<IWeaponFactory> weaponGenerator;

    private ModelTimer spawnTimer;

    private Queue<IWeaponFactory> weaponsQueue = new LinkedList<>();
    private Queue<IEnemyFactory> enemiesQueue = new LinkedList<>();

    private Queue<IEntityFactory> miscEntitiesQueue = new LinkedList<>();


    public MarathonCursor(MarathonScenario scenario, GameModel gameModel) {
        this.scenario = scenario;
        this.gameModel = gameModel;
        this.updateDifficultyTimer = new ModelTimer(1, true, this::updateDifficulty, gameModel);
        updateDifficultyTimer.start();
        this.cooldownDuration = scenario.initialWaveCooldown();
        this.waveDuration = scenario.initialWaveDuration();

        this.enemyGenerator = new EnemyGenerator();
        this.weaponGenerator = new WeaponGenerator();
        this.spawnTimer = new ModelTimer(1, true, this::spawnStuff, gameModel);
        spawnTimer.start();


        startCooldown();
    }

    private void spawnStuff() {
        if (!isCoolingDown) {
            enemiesQueue.addAll(enemyGenerator.nextElements(1));
        }
        else {
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
        System.out.println("Difficulty increased by " + difficultyIncrease);
        rawDifficulty = Math.max(0, rawDifficulty + difficultyIncrease);
        System.out.println("Raw difficulty: " + rawDifficulty);
        System.out.println("Adjusted difficulty: " + adjustedDifficulty());
        if (difficultyIncrease != 0) {
            double enemyGeneratorP = (adjustedDifficulty() / MAX_DIFFICULTY) / 2;
            double weaponGeneratorP = (1 - enemyGeneratorP);
            enemyGenerator.setElementRates(ZipToMap.zipToMap(scenario.enemiesByDifficulty(), getBinomialProbabilties(scenario.enemiesByDifficulty().size(), enemyGeneratorP)));
            weaponGenerator.setElementRates(ZipToMap.zipToMap(scenario.weaponsByPower(), getBinomialProbabilties(scenario.weaponsByPower().size(), weaponGeneratorP)));
        }
    }

    @Override
    public void update(double delta) {
        time += delta;
    }

    private double adjustedDifficulty() {
        return 30 * Math.log(rawDifficulty + 1);
    }

    private void startWave() {
        this.isCoolingDown = false;
        waveNumber++;
        System.out.println("Wave " + waveNumber + " started");
        waveTimer = new ModelTimer(waveDuration, false, this::startCooldown, gameModel);
        waveTimer.start();
    }

    private void startCooldown() {
        this.isCoolingDown = true;
        for (int i=0; i<5; i++) { // Spawn 5 helper entities at the end of each wave
            miscEntitiesQueue.addAll(scenario.helperEntities());
        }
        System.out.println("Cooldown started");
        cooldownTimer = new ModelTimer(cooldownDuration, false, this::startWave, gameModel);
        cooldownTimer.start();
    }

    // TODO: optimize this, we compute similar things every time
    private List<Double> getBinomialProbabilties(int n, double p) {
        return (IntStream.range(0, n).mapToDouble(k -> Math.pow(p, k) * Math.pow(1 - p, n - k) * MathTools.nCR(n, k))).boxed().toList();
    }

    @Override
    public IWeaponFactory nextWeapon() {
        return weaponsQueue.poll();
    }

    @Override
    public IEnemyFactory nextEnemy() {
        if (isCoolingDown) {
            return null;
        }

        return enemiesQueue.poll();
    }

    @Override
    public IEntityFactory nextMiscEntity() {
        return miscEntitiesQueue.poll();
    }

    @Override
    public boolean isGameFinished() {
        return false;
    }
}
