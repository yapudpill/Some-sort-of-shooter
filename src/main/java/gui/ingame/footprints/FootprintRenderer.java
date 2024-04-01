package gui.ingame.footprints;

import gui.ImageCache;
import gui.ScalableComponent;
import model.ingame.Coordinates;
import model.ingame.IUpdateable;

import javax.swing.JComponent;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class FootprintRenderer extends JComponent implements ScalableComponent, IUpdateable {
    private static final double FOOTPRINT_SIZE = 0.5;
    private static final double FOOTPRINT_LIFETIME = 60;
    private final double angle;
    private final Coordinates pos;
    private long age;

    public FootprintRenderer(Coordinates entityDirection, Coordinates entityPos) {
        this.angle = entityDirection.getAngle();
        Coordinates shiftDirection = new Coordinates(-FOOTPRINT_SIZE/2,- FOOTPRINT_SIZE/2);
        this.pos = entityPos.add(shiftDirection);
    }

    public boolean hasFaded() {
        return age >= FOOTPRINT_LIFETIME;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        Graphics2D g2d = (Graphics2D) g.create();

        BufferedImage image = ImageCache.loadImage("footprint.png", getClass());
        double mid = (double) getWidth() / 2;
        g2d.rotate(angle, mid, mid);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (1 - age / FOOTPRINT_LIFETIME)));
        g2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

    @Override
    public Coordinates getOriginalPosition() {
        return pos;
    }

    @Override
    public Coordinates getOriginalSize() {
        return new Coordinates(FOOTPRINT_SIZE, FOOTPRINT_SIZE);
    }

    @Override
    public void update() {
        age++;
    }
}
