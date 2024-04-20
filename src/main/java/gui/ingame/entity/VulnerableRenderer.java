package gui.ingame.entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Map;

import gui.ImageCache;
import model.ingame.entity.BreakableBarrier;
import model.ingame.entity.ExplodingEnemy;
import model.ingame.entity.IVulnerableEntity;
import model.ingame.entity.PlayerModel;
import model.ingame.entity.SmartEnemyModel;
import model.ingame.entity.WalkingEnemyModel;

public class VulnerableRenderer extends AbstractEntityRenderer {
    private static final Map<Class<? extends IVulnerableEntity>, String> sprites = Map.of (
        PlayerModel.class,       "sprites/player1/playerRightShoot.png",
        WalkingEnemyModel.class, "sprites/EyeBallEnemy.png",
        SmartEnemyModel.class,   "sprites/Brain_of_Cthulhu.png",
        ExplodingEnemy.class,    "sprites/bombman.png",
        BreakableBarrier.class,  "sprites/breakablebarrier.png"
    );

    private final Image image;

    public VulnerableRenderer(IVulnerableEntity entity) {
        super(entity);
        String path = sprites.get(entity.getClass());
        if (path == null) {
            throw new IllegalArgumentException("No sprite set for " + entity.getClass().getName());
        }
        image = ImageCache.loadImage(path, getClass());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);

        // show health bar
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), 5);

        g.setColor(Color.GREEN);
        IVulnerableEntity vul = (IVulnerableEntity) entity;
        int greenWidth = (int) (getWidth() * ((double) vul.getHealth() / vul.getMaxHealth()));
        g.fillRect(0, 0, greenWidth, 5);
    }
}
