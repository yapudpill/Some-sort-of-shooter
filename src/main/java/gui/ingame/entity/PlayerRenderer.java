package gui.ingame.entity;

import java.awt.Color;
import java.awt.Graphics;

import gui.ImageCache;
import model.ingame.entity.PlayerModel;
import model.ingame.weapon.ContinuousFireWeapon;
import util.ImageLoader;

public class PlayerRenderer extends VulnerableAnimatedRenderer {
    private ImageLoader reg = ImageCache::loadImage;
    private ImageLoader neg = ImageCache::loadNegativeImage;

    public PlayerRenderer(PlayerModel entityModel) {
        super(entityModel, "animations/player1.xml");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw vertical bar representing the cool down of the weapon if there is one
        if (((PlayerModel) entity).getWeapon() != null) {
            double coolDown = ((PlayerModel) entity).getWeapon().getTimeLeft();
            double maxCoolDown = ((PlayerModel) entity).getWeapon().getCoolDownDelay();
            double width = getWidth() * 0.1;
            double height = getHeight() * 0.8 * (1-(coolDown / maxCoolDown));
            double x = getWidth() - width;
            double y = (getHeight() - height);
            g.setColor(Color.BLACK);
            g.fillRect((int) x, (int) y, (int) width, (int) height);
        }
        // draw vertical bar representing the heat of the weapon if there is one
        if (((PlayerModel) entity).getWeapon() instanceof ContinuousFireWeapon) {
            double coolDown = ((ContinuousFireWeapon) ((PlayerModel) entity).getWeapon()).getHeat();
            double maxCoolDown = ((ContinuousFireWeapon) ((PlayerModel) entity).getWeapon()).getMaxheat();
            double width = getWidth() * 0.1;
            double height = getHeight() * 0.8 * (coolDown / maxCoolDown);
            double x = getWidth() - width;
            double y = (getHeight() - height);
            g.setColor(Color.RED);
            g.fillRect((int) x, (int) y, (int) width, (int) height);
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
