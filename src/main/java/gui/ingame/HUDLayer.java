package gui.ingame;

import java.awt.Color;
import java.util.function.IntSupplier;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.IScalableComponent;
import gui.ScaleLayout;
import util.Coordinates;
import util.IUpdateable;
import model.ingame.entity.PlayerModel;

class HUDLayer extends JPanel implements IUpdateable {
    private static final String WEAPON_LABEL = "Current weapon: %s";

    private static class WeaponLabel extends JLabel implements IScalableComponent {
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

    HUDLayer(PlayerModel playerModel, IntSupplier scaleSupplier) {
        this.setLayout(new ScaleLayout(scaleSupplier));
        this.setOpaque(false);
        this.playerModel = playerModel;
        this.weaponLabel.setForeground(Color.BLACK);
        this.add(weaponLabel);
    }

    @Override
    public void update(double delta) {
        String weaponName = (playerModel.getWeapon() == null) ? "No weapon" : playerModel.getWeapon().getName();
        weaponLabel.setText(String.format(WEAPON_LABEL, weaponName));
    }
}
