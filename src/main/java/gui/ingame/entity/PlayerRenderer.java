package gui.ingame.entity;

import java.awt.Color;
import java.awt.Graphics;

import model.ingame.entity.IVulnerableEntity;
import model.ingame.entity.PlayerModel;

public class PlayerRenderer extends AnimatedEntityRenderer {
    public PlayerRenderer(PlayerModel entityModel) {
        super(entityModel, "animation_groups/player1.xml");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // show health bar
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), 5);
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, (int) (getWidth() * ((double) ((IVulnerableEntity) entityModel).getHealth() / ((IVulnerableEntity) entityModel).getMaxHealth())), 5);
    }

    @Override
    public void update(double deltaT) {
        super.update(deltaT);

        PlayerModel playerModel = (PlayerModel) entityModel;
        if (playerModel.getMovementHandler().isMoving()) {
            animationManager.switchAnimation(
                switch (playerModel.getMovementHandler().getDirectionVector().getCardinalDirection()) {
                    case UP -> "walk_up";
                    case DOWN -> "walk_down";
                    case LEFT -> "walk_left";
                    case RIGHT -> "walk_right";
                }
            );
        } else {
            animationManager.switchAnimation(
                switch (animationManager.getCurrentAnimationId()) {
                    case "walk_up" -> "idle_up";
                    case "walk_down" -> "idle_down";
                    case "walk_left"-> "idle_left";
                    default -> "idle_right";
                }
            );
        }
    }
}
