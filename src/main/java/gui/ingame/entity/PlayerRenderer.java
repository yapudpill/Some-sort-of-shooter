package gui.ingame.entity;

import gui.ImageCache;
import model.ingame.entity.PlayerModel;
import model.ingame.weapon.ContinuousFireWeapon;
import model.ingame.weapon.WeaponModel;
import util.ImageLoader;

import java.awt.*;

/**
 * Renderer for the player entity. Switches animations based on the player's movement.
 */
public class PlayerRenderer extends VulnerableAnimatedRenderer {
    private ImageLoader reg = ImageCache::loadImage;
    private ImageLoader neg = ImageCache::loadNegativeImage;

    public PlayerRenderer(PlayerModel entityModel) {
        super(entityModel, "animations/player1.xml");
    }

    private void drawVerticalBar(Graphics g, double percentage, Color color) {
        double width = getWidth() * 0.1;
        double height = getHeight() * 0.8 * percentage;
        double x = getWidth() - width;
        double y = (getHeight() - height);
        g.setColor(color);
        g.fillRect((int) x, (int) y, (int) width, (int) height);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        WeaponModel weaponModel = ((PlayerModel) entity).getWeapon();
        if (weaponModel != null) {
            if (weaponModel instanceof ContinuousFireWeapon) {
                drawVerticalBar(g, (double) ((ContinuousFireWeapon) weaponModel).getHeat() / ((ContinuousFireWeapon) weaponModel).getMaxheat(), Color.RED);
            }
            else {
                drawVerticalBar(g, weaponModel.getTimeLeft() / weaponModel.getCoolDownDelay(), Color.BLACK);
            }
        }


    }

    @Override
    public void update(double deltaT) {
        super.update(deltaT);

        PlayerModel player = (PlayerModel) entity;
        if (player.getMovementHandler().isMoving()) {
            if(player.isDashing()){
                if(animationManager.imageLoaderIs(reg)) animationManager.setImageLoader(neg);
            }else if(!animationManager.imageLoaderIs(reg)) {
                animationManager.setImageLoader(reg);
            }
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
                    case "walk_up"    -> "idle_up";
                    case "walk_down"  -> "idle_down";
                    case "walk_left"  -> "idle_left";
                    case "walk_right" -> "idle_right";
                    default           -> null;
                }
            );
        }
    }
}
