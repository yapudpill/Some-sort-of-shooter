package gui;

import javax.swing.*;
import javax.swing.plaf.synth.SynthLookAndFeel;
import java.awt.*;
import java.io.InputStream;

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
        setupLookAndFeel();

        setSize(900, 900);
        setTitle("Some sort of shooter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        EventQueue.invokeLater(() -> setVisible(true));
    }

    private void setupLookAndFeel() {
        try {
            // Make the custom font available for the look and feel
            InputStream fontStream = getClass().getResourceAsStream("laf/determination.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
            env.registerFont(font);


            SynthLookAndFeel laf = new SynthLookAndFeel();
            laf.load(getClass().getResourceAsStream("laf/lookAndFeel.xml"), getClass());
            UIManager.setLookAndFeel(laf);
            fileChooser.updateUI(); // make the file chooser use the look and feel
        } catch (Exception e) {
            e.printStackTrace();
        }
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
