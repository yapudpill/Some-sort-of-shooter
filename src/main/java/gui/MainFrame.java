package gui;

import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * The main <code>JFrame</code> object of this project, where everything is
 * displayed.
 */
public class MainFrame extends JFrame {

    /**
     * Universal file chooser that is used everywhere. This is necessary because
     * file choosers remember the directory where they where last closed.
     */
    public static final JFileChooser fileChooser = new JFileChooser();

    /**
     * Creates a new <code>MainFrame</code>, with size to 900x900 that uses the
     * default look and feel. Then make it visible.
     */
    public MainFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            fileChooser.updateUI(); // make the file chooser use the look and feel
        } catch (Exception e) {}

        setSize(900,900);
        setTitle("Some sort of shooter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        EventQueue.invokeLater(() -> setVisible(true));
    }

    /**
     * Swap the <code>contentPane</code> of this frame to the specified menu.
     *
     * @param menu the menu that will be displayed
     */
    public void loadMenu(JPanel menu) {
        EventQueue.invokeLater(() -> {
            setContentPane(menu);
            validate();
        });
    }
}
