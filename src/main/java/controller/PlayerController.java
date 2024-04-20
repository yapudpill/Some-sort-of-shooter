package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Map;

import gui.ingame.GameMainArea;
import model.ingame.entity.PlayerModel;
import model.ingame.weapon.WeaponModel;
import util.Coordinates;

/**
 * A controller for a player using the ZQSD keys. It uses Swing's KeyListener to
 * listen for key presses and releases. To use it, add the KeyListener and
 * MouseListener returned by getKeyListener() and getMouseListener() to a JContainer
 */
public class PlayerController {

    // warning: Key bindings using ZQSD keys these are for AZERTY keyboards only
    private static final Map<Integer, Coordinates> keyDirectionMap = Map.of(
            KeyEvent.VK_Z, Coordinates.UP,
            KeyEvent.VK_S, Coordinates.DOWN,
            KeyEvent.VK_D, Coordinates.RIGHT,
            KeyEvent.VK_Q, Coordinates.LEFT
    );

    private static Map<Integer, Runnable> getKeyActionMap(PlayerModel playerModel) {
        return Map.of(
                MouseEvent.BUTTON1, playerModel::attack,
                MouseEvent.BUTTON3, playerModel::dash,
                KeyEvent.VK_E, playerModel::pickWeapon
        );
    }

    private final PlayerModel controlledPlayerModel;
    private final GameMainArea gameMainArea;
    private final ArrayList<Integer> heldKeys = new ArrayList<>();

    public PlayerController(PlayerModel controlledPlayerModel, GameMainArea gameMainArea) {
        this.controlledPlayerModel = controlledPlayerModel;
        this.gameMainArea = gameMainArea;
    }

    /**
     * @return a key listener that updates the held keys set when a key is pressed or released
     */
    public KeyListener getKeyListener() {
        return new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (heldKeys.contains(keyCode)) return;

                // Update direction:
                if (keyDirectionMap.containsKey(keyCode)) {
                    Coordinates addedVelocityVector = keyDirectionMap.get(keyCode);
                    Coordinates oldVelocityVector = controlledPlayerModel.getMovementHandler().getDirectionVector();
                    controlledPlayerModel.getMovementHandler().setDirectionVector(oldVelocityVector.add(addedVelocityVector));
                }

                Runnable action = getKeyActionMap(controlledPlayerModel).get(e.getKeyCode());
                if (action != null) {
                    action.run();
                }
                heldKeys.add(keyCode);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (!heldKeys.contains(keyCode)) return;

                // Update direction:
                if (keyDirectionMap.containsKey(keyCode)) {
                    Coordinates addedVelocityVector = keyDirectionMap.get(keyCode).opposite();
                    Coordinates oldVelocityVector = controlledPlayerModel.getMovementHandler().getDirectionVector();
                    controlledPlayerModel.getMovementHandler().setDirectionVector(oldVelocityVector.add(addedVelocityVector));
                }
                heldKeys.remove((Integer) keyCode);
            }
        };
    }

    /**
     * @return a mouse listener that updates the held mouse buttons set when a mouse button is pressed or released
     */
    public MouseListener getMouseListener() {
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (getKeyActionMap(controlledPlayerModel).containsKey(e.getButton())) {
                    WeaponModel weapon = controlledPlayerModel.getWeapon();
                    if (weapon != null) {
                        weapon.setDirectionVector(new Coordinates(
                            (double) e.getX() / gameMainArea.getScale() - controlledPlayerModel.getPos().x(),
                            (double) e.getY() / gameMainArea.getScale() - controlledPlayerModel.getPos().y()
                        ));
                    }
                    getKeyActionMap(controlledPlayerModel).get(e.getButton()).run();
                }
            }
        };
    }

    public MouseMotionListener getMouseMotionListener() {
        return new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                WeaponModel weapon = controlledPlayerModel.getWeapon();
                if (weapon != null && weapon.usesDirectionVector()) {
                    weapon.setDirectionVector(new Coordinates(
                        (double) e.getX() / gameMainArea.getScale() - controlledPlayerModel.getPos().x(),
                        (double) e.getY() / gameMainArea.getScale() - controlledPlayerModel.getPos().y()
                    ));
                }
            }
        };
    }
}
