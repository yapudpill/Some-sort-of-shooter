package gui.ingame;

import javax.swing.*;
import java.awt.*;

public class EffectsPaneLayer extends JPanel {
    public EffectsPaneLayer() {
        super();
        this.setLayout(null);
        this.setOpaque(false);
        JLabel testLabel = new JLabel("Swoosh");
        testLabel.setForeground(Color.BLACK);
        testLabel.setBounds(20, 20, 100, 100);
        this.add(testLabel);
    }
}
