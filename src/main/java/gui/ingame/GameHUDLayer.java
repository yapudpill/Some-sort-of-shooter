package gui.ingame;

import gui.ScalableComponent;
import gui.ScaleLayout;
import gui.ScaleSupplier;
import model.ingame.Coordinates;
import model.ingame.GameModel;
import model.ingame.IUpdateable;
import model.ingame.entity.PlayerModel;

import javax.swing.*;
import java.awt.*;

public class GameHUDLayer extends JPanel implements IUpdateable {
    private static final String WEAPON_LABEL = "Current weapon: %s";

    private static class WeaponLabel extends JLabel implements ScalableComponent {
        @Override
        public Coordinates getOriginalPosition() {
            return new Coordinates(0, 1);
        }

        @Override
        public Coordinates getOriginalSize() {
            return new Coordinates(12, 1);
        }
    }

    private final JLabel weaponLabel = new WeaponLabel();

    private final PlayerModel playerModel;

    public GameHUDLayer(PlayerModel playerModel, ScaleSupplier scaleSupplier) {
        this.setLayout(new ScaleLayout(scaleSupplier));
        this.setOpaque(false);
        this.playerModel = playerModel;
        this.weaponLabel.setForeground(Color.BLACK);
        this.add(weaponLabel);
    }

    @Override
    public void update() {
        String weaponName = (playerModel.getWeapon() == null) ? "No weapon" : playerModel.getWeapon().getName();
        weaponLabel.setText(String.format(WEAPON_LABEL, weaponName));
        System.out.println(getWidth());
    }
}
