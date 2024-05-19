package gui.ingame;

import gui.IScalableComponent;
import gui.ImageCache;
import gui.ScaleLayout;
import gui.ingame.entity.PlayerRenderer;
import model.ingame.GameModel;
import model.ingame.entity.PlayerModel;
import model.ingame.entity.WeaponEntity;
import util.Coordinates;
import util.IUpdateable;

import javax.swing.*;
import java.awt.*;
import java.util.function.IntSupplier;

/**
 * A simple HUD layer that displays the current weapon of the player, and a message if the player is standing on a weapon.
 */
public class HUDLayer extends JPanel implements IUpdateable {
    private String currentMessString = "";
    private static int labelNumber = 0;
    private static Coordinates positionLabel(){
        labelNumber++;
        if (labelNumber>6) labelNumber = 1;
        return switch (labelNumber){
            case 1 -> new Coordinates(0.5, 0.5);
            case 2 -> new Coordinates(0.5, 1.5);
            case 3 -> new Coordinates(15, 0.3);
            case 4 -> new Coordinates(15, 1.3);
            case 5 -> new Coordinates(17, 0.3);
            case 6 -> new Coordinates(17, 1.3);
            default -> new Coordinates(5, 5);
        };
    }

    private static class HUDLabel extends JLabel implements IScalableComponent {
        @Override
        public Coordinates getOriginalPosition() {
            return positionLabel();
        }

        @Override
        public Coordinates getOriginalSize() {
            return new Coordinates(3, 2);
        }
    }

    private class MessageLabel extends JLabel implements IScalableComponent {
        @Override
        public Coordinates getOriginalPosition() {
            return playerModel.getPos().add(new Coordinates(-4, 0));
        }

        @Override
        public Coordinates getOriginalSize() {
            return new Coordinates(12, 1);
        }
    }

    private final JLabel weaponLabel = new HUDLabel();
    private final JLabel otherWeaponLabel = new HUDLabel();
    private final JLabel speedLabel = new HUDLabel();
    private final JLabel damageLabel = new HUDLabel();
    private final JLabel healthLabel = new HUDLabel();
    private final JLabel regenLabel = new HUDLabel();

    private final JLabel messageLabel = new MessageLabel();

    private final PlayerModel playerModel;
    private final GameModel gameModel;

    public HUDLayer(PlayerModel playerModel, IntSupplier scaleSupplier, GameModel gameModel) {
        this.setLayout(new ScaleLayout(scaleSupplier));
        this.setOpaque(false);
        this.playerModel = playerModel;
        this.gameModel = gameModel;
        this.weaponLabel.setForeground(Color.BLACK);
        this.add(weaponLabel);
        this.otherWeaponLabel.setForeground(Color.BLACK);
        this.add(otherWeaponLabel);
        this.speedLabel.setForeground(Color.BLACK);
        this.add(speedLabel);
        this.damageLabel.setForeground(Color.BLACK);
        this.add(damageLabel);
        this.healthLabel.setForeground(Color.BLACK);
        this.add(healthLabel);
        this.regenLabel.setForeground(Color.BLACK);
        this.add(regenLabel);
        // position in the top center
        this.add(messageLabel);
        messageLabel.setForeground(Color.BLACK);
    }

    @Override
    public void update(double delta) {
        if(playerModel.getWeapon()!=null) {
            weaponLabel.setIcon(new ImageIcon(ImageCache.loadImage(String.format("sprites/weapon/%s.png", playerModel.getWeapon().getIdentifier()), PlayerRenderer.class)));
        }
        else weaponLabel.setIcon(null);

        if(playerModel.getOtherWeapon()!=null){
            otherWeaponLabel.setIcon(new ImageIcon(ImageCache.loadImage(String.format("sprites/weapon/%s.png", playerModel.getOtherWeapon().getIdentifier()),PlayerRenderer.class)));
        }
        else otherWeaponLabel.setIcon(null);

        speedLabel.setIcon(ImageCache.loadIcon("speed"));
        speedLabel.setText(String.format(": %d", gameModel.stats.nbSpeedUpgrade));

        damageLabel.setIcon(ImageCache.loadIcon("damage"));
        damageLabel.setText(String.format(": %d", gameModel.stats.nbDamageUpgrade));

        healthLabel.setIcon(ImageCache.loadIcon("health"));
        healthLabel.setText(String.format(": %d", gameModel.stats.nbHealthUpgrade));

        regenLabel.setIcon(ImageCache.loadIcon("regen"));
        regenLabel.setText(String.format(": %d", gameModel.stats.nbRegenUpgrade));

        if(playerModel.isCurrentlyCollidingWith(e -> e instanceof WeaponEntity)) {
            currentMessString = "Press E to pick up weapon";
        } else if (playerModel.shouldPickWeapons()) {
            currentMessString = "Press A to swap weapon";
        } else {
            currentMessString = "";
        }
        messageLabel.setText(currentMessString);
    }
}
