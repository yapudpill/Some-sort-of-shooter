package gui.ingame.effects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JComponent;

import gui.IScalableComponent;
import model.ingame.Coordinates;
import model.ingame.entity.ICombatEntity;

public class AimArrow extends JComponent implements IScalableComponent {
    private final ICombatEntity combatEntity;

    public AimArrow(ICombatEntity combatEntity) {
        this.combatEntity = combatEntity;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        if (combatEntity.getWeapon() == null || combatEntity.getWeapon().getDirectionVector() == null) return;

        Graphics2D g2 = (Graphics2D) g;
        Coordinates center = new Coordinates((double) getBounds().width / 2, (double) getBounds().height / 2);

        g2.setColor(new Color(0xa7a7a7));
        Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                0, new float[]{3}, 0);
        double arrowLength = getBounds().getWidth() / 2;

        g2.setStroke(dashed);

        Coordinates tip = combatEntity.getWeapon().getDirectionVector().normalize().multiply(arrowLength);
        g2.drawLine((int) center.x, (int) center.y, (int) (center.x + tip.x), (int) (center.y + tip.y));
        Coordinates leftArrowHeadTip = tip.opposite().rotate(Math.PI / 4).normalize().multiply(arrowLength * 0.2);
        g2.drawLine((int) (center.x + tip.x),
                (int) (center.y + tip.y),
                (int) (center.x + tip.x + leftArrowHeadTip.x),
                (int) (center.y + tip.y + leftArrowHeadTip.y));

        Coordinates rightArrowHeadTip = tip.opposite().rotate(-Math.PI / 4).normalize().multiply(arrowLength * 0.2);
        g2.drawLine((int) (center.x + tip.x),
                (int) (center.y + tip.y),
                (int) (center.x + tip.x + rightArrowHeadTip.x),
                (int) (center.y + tip.y + rightArrowHeadTip.y));
    }

    @Override
    public Coordinates getOriginalPosition() {
        Coordinates translation = getOriginalSize().multiply(0.5);
        return new Coordinates(combatEntity.getPos().x - translation.x, combatEntity.getPos().y - translation.y);
    }

    @Override
    public Coordinates getOriginalSize() {
        double maxDimension = Math.max(combatEntity.getWidth(), combatEntity.getHeight());
        return new Coordinates(maxDimension + 3, maxDimension + 3);
    }
}
