package gui.ingame.tile;

import java.awt.Graphics;
import java.awt.Graphics2D;

import gui.animations.AnimationCache;
import gui.animations.AnimationGroup;
import gui.animations.AnimationManager;
import util.IUpdateable;

public class AnimatedTileRenderer extends TileRenderer implements IUpdateable {
    protected final AnimationManager animationManager;

    public AnimatedTileRenderer(String path) {
        AnimationGroup group = AnimationCache.loadAnimationGroup(path, getClass());
        this.animationManager = new AnimationManager(group, getClass());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2D = (Graphics2D) g.create();
        g2D.drawImage(animationManager.getCurrentImage(), 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    public void update(double delta) {
        animationManager.update(delta);
    }
}
