package gui.ingame;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

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
