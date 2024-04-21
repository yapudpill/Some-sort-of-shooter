package gui.ingame.entity;

import model.ingame.entity.PlayerModel;

public class PlayerRenderer extends VulnerableAnimatedRenderer {

    public PlayerRenderer(PlayerModel entityModel) {
        super(entityModel, "animation_groups/player1.xml");
    }

    @Override
    public void update(double deltaT) {
        super.update(deltaT);

        PlayerModel player = (PlayerModel) entity;
        if (player.getMovementHandler().isMoving()) {
            animationManager.switchAnimation(
                switch (player.getMovementHandler().getDirectionVector().getCardinalDirection()) {
                    case UP    -> "walk_up";
                    case DOWN  -> "walk_down";
                    case LEFT  -> "walk_left";
                    case RIGHT -> "walk_right";
                }
            );
        } else {
            animationManager.switchAnimation(
                switch (animationManager.getCurrentAnimationId()) {
                    case "walk_up"   -> "idle_up";
                    case "walk_down" -> "idle_down";
                    case "walk_left" -> "idle_left";
                    default          -> "idle_right";
                }
            );
        }
    }
}
