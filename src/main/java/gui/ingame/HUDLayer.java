package gui.ingame;

import java.awt.Color;
import java.util.function.IntSupplier;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.IScalableComponent;
import gui.ImageCache;
import gui.ScaleLayout;
import gui.ingame.entity.PlayerRenderer;
import util.Coordinates;
import util.IUpdateable;
import model.ingame.entity.PlayerModel;
import model.ingame.entity.WeaponEntity;
import model.level.MapModel;

public class HUDLayer extends JPanel implements IUpdateable {
    private String currentMessString = "";

    private static class WeaponLabel extends JLabel implements IScalableComponent {
        @Override
        public Coordinates getOriginalPosition() {
            return new Coordinates(0.5, 0.5);
        }

        @Override
        public Coordinates getOriginalSize() {
            return new Coordinates(12, 1);
        }
    }

    private class MessageLabel extends JLabel implements IScalableComponent {
        @Override
        public Coordinates getOriginalPosition() {
            return new Coordinates((map.getWidth() / 2)-2.5, 1);
        }

        @Override
        public Coordinates getOriginalSize() {
            return new Coordinates(5, 5);
        }
    }

    private final JLabel weaponLabel = new WeaponLabel();
    private final JLabel messageLabel = new MessageLabel();

    private final PlayerModel playerModel;
    private final MapModel map;

    public HUDLayer(PlayerModel playerModel, IntSupplier scaleSupplier, MapModel map) {
        this.setLayout(new ScaleLayout(scaleSupplier));
        this.setOpaque(false);
        this.playerModel = playerModel;
        this.weaponLabel.setForeground(Color.BLACK);
        this.add(weaponLabel);
        // position in the top center
        this.add(messageLabel);
        messageLabel.setForeground(Color.BLACK);
        this.map = map;
    }

    @Override
    public void update(double delta) {
        weaponLabel.setIcon(new ImageIcon(ImageCache.loadImage(String.format("sprites/weapon/%s.png", playerModel.getWeapon().getIdentifier()),PlayerRenderer.class)));
        if(playerModel.isCurrentlyCollidingWith(e -> e instanceof WeaponEntity)) {
            currentMessString = "Press E to pick up weapon";
        } else {
            currentMessString = "";
        }
        messageLabel.setText(currentMessString);
    }
}
