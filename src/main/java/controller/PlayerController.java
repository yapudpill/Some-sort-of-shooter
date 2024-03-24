package controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Map;

import gui.ingame.GameMainArea;
import model.ingame.Coordinates;
import model.ingame.entity.PlayerModel;
import model.ingame.weapon.WeaponModel;

/**
 * A controller for a player using the WASD keys. It uses Swing's KeyListener to listen for key presses and releases.
 * To use it, add the KeyListener and MouseListener returned by getKeyListener() and getMouseListener() to a JContainer
 */
public class PlayerController {
    // Key bindings for player 1 using WASD keys
    // warning: these are for AZERTY keyboards only
    private static final Map<Integer, Coordinates> player1KeyDirectionMap = Map.of(
            KeyEvent.VK_Z, Coordinates.UP,
            KeyEvent.VK_S, Coordinates.DOWN,
            KeyEvent.VK_D, Coordinates.RIGHT,
            KeyEvent.VK_Q, Coordinates.LEFT
    );
    private final PlayerModel controlledPlayerModel;
    private final GameMainArea gameMainArea;
    ArrayList<Integer> heldKeys = new ArrayList<>();

    public PlayerController(PlayerModel controlledPlayerModel, GameMainArea gameMainArea) {
        this.controlledPlayerModel = controlledPlayerModel;
        this.gameMainArea = gameMainArea;
    }

    private static Map<Integer, PlayerModel.PlayerAction> getPlayer1KeyActionMap(PlayerModel playerModel) {
        return Map.of(MouseEvent.BUTTON1, playerModel::attack, MouseEvent.BUTTON3, playerModel::dash);
    }

    /**
     * @return a key listener that updates the held keys set when a key is pressed or released
     */
    public KeyListener getKeyListener() {
        return new KeyAdapter() {


            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if(heldKeys.contains(keyCode)) return;

                // Update direction:
                if (player1KeyDirectionMap.containsKey(keyCode)) {
                    Coordinates addedVelocityVector = player1KeyDirectionMap.get(keyCode);
                    Coordinates oldVelocityVector = controlledPlayerModel.getMovementHandler().getDirectionVector();
                    oldVelocityVector.add(addedVelocityVector);
                }
                heldKeys.add(keyCode);

            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if(!heldKeys.contains(keyCode)) return;

                // Update direction:
                if (player1KeyDirectionMap.containsKey(keyCode)) {
                     Coordinates addedVelocityVector = player1KeyDirectionMap.get(keyCode).opposite();
                     Coordinates oldVelocityVector = controlledPlayerModel.getMovementHandler().getDirectionVector();
                     oldVelocityVector.add(addedVelocityVector);
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
                if (getPlayer1KeyActionMap(controlledPlayerModel).containsKey(e.getButton())) {
                    WeaponModel weapon = controlledPlayerModel.getWeapon();
                    if(weapon != null) weapon.setDirectionVector(new Coordinates(e.getX()/gameMainArea.getScale() - controlledPlayerModel.getPos().x, e.getY()/gameMainArea.getScale() - controlledPlayerModel.getPos().y));
                    getPlayer1KeyActionMap(controlledPlayerModel).get(e.getButton()).performAction();
                }
            }
        };
    }

    public void clearHeldKeys() {
        heldKeys.clear();
    }
}
