package controller;

import gui.ingame.GameMainArea;
import model.ingame.entity.PlayerModel;
import model.ingame.weapon.ContinuousFireWeapon;
import model.ingame.weapon.ShotGun;
import model.ingame.weapon.WeaponModel;
import util.Coordinates;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Map;

/**
 * A controller for a player using the ZQSD keys. It uses Swing's KeyListener to
 * listen for key presses and releases. To use it, add the KeyListener and
 * MouseListener returned by getKeyListener() and getMouseListener() to a JContainer
 */
public class PlayerController implements KeyListener, MouseListener, MouseMotionListener {

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
                KeyEvent.VK_E, playerModel::pickWeapon,
                KeyEvent.VK_A, playerModel::swap
        );
    }

    private final PlayerModel controlledPlayerModel;
    private final GameMainArea gameMainArea;
    private final ArrayList<Integer> heldKeys = new ArrayList<>();

    public PlayerController(PlayerModel controlledPlayerModel, GameMainArea gameMainArea) {
        this.controlledPlayerModel = controlledPlayerModel;
        this.gameMainArea = gameMainArea;
    }

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

    @Override
    public void mousePressed(MouseEvent e) {
        WeaponModel weapon = controlledPlayerModel.getWeapon();
        if (getKeyActionMap(controlledPlayerModel).containsKey(e.getButton())) {
            if (weapon != null && weapon.usesDirectionVector()) {
                weapon.setDirectionVector(new Coordinates(
                        (double) e.getX() / gameMainArea.getScale() - controlledPlayerModel.getPos().x(),
                        (double) e.getY() / gameMainArea.getScale() - controlledPlayerModel.getPos().y()
                ));
            }
            getKeyActionMap(controlledPlayerModel).get(e.getButton()).run();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int button = e.getButton();
        if (getKeyActionMap(controlledPlayerModel).containsKey(button)) {
            WeaponModel weapon = controlledPlayerModel.getWeapon();

            if(weapon != null){
                if (weapon.usesDirectionVector()) {
                    weapon.setDirectionVector(new Coordinates(
                            (double) e.getX() / gameMainArea.getScale() - controlledPlayerModel.getPos().x(),
                            (double) e.getY() / gameMainArea.getScale() - controlledPlayerModel.getPos().y()
                    ));
                }
                switch (weapon){
                    case ShotGun ignored ->
                            getKeyActionMap(controlledPlayerModel).get(button).run();
                    case ContinuousFireWeapon ignored ->
                            getKeyActionMap(controlledPlayerModel).get(button).run();
                    default -> {}
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        WeaponModel weapon = controlledPlayerModel.getWeapon();
        if (weapon != null && weapon.usesDirectionVector()) {
            weapon.setDirectionVector(new Coordinates(
                (double) e.getX() / gameMainArea.getScale() - controlledPlayerModel.getPos().x(),
                (double) e.getY() / gameMainArea.getScale() - controlledPlayerModel.getPos().y()
            ));
        }
    }
    public void mouseDragged(MouseEvent e) {
        WeaponModel weapon = controlledPlayerModel.getWeapon();
        if (weapon != null && weapon.usesDirectionVector()) {
            weapon.setDirectionVector(new Coordinates(
                    (double) e.getX() / gameMainArea.getScale() - controlledPlayerModel.getPos().x(),
                    (double) e.getY() / gameMainArea.getScale() - controlledPlayerModel.getPos().y()
            ));
        }
    }


    @Override public void mouseClicked(MouseEvent arg0) {}
    @Override public void mouseEntered(MouseEvent arg0) {}
    @Override public void mouseExited(MouseEvent arg0) {}
    @Override public void keyTyped(KeyEvent arg0) {}
}
