package gui.launcher;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gui.ImageCache;
import gui.MainFrame;
import util.Resource;

public class ResourceSelector extends JPanel {
    private final Class<?> resourceBase;
    private final String filePattern;
    protected final JComboBox<String> defaultBox;
    protected final JButton openButton, clearButton;
    protected final JLabel loadLabel;
    protected File loadedFile;

    public ResourceSelector(String resourceName, String index, String filePattern, Class<?> resourceBase) {
        this.resourceBase = resourceBase;
        this.filePattern = filePattern;

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1;
        constraints.weighty = 1;

        // Labels (col 0)
        constraints.gridx = 0;

        constraints.gridy = 0;
        add(new JLabel("Default " + resourceName + ":"), constraints);

        constraints.gridy = 1;
        add(new JLabel("Load custom " + resourceName + ":"), constraints);

        // Insets for the comboBox and buttons
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Default selector (row 0, col 1 <-> 2)
        constraints.gridy = 0;
        constraints.gridx = 1;

        constraints.gridwidth = 2;

        InputStream in = getClass().getResourceAsStream(index);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        defaultBox = new JComboBox<>(reader.lines().toArray(String[]::new));
        add(defaultBox, constraints);

        constraints.gridwidth = 1;

        // Custom loading buttons (row 1, cols 1/2)
        constraints.gridy = 1;

        constraints.gridx = 1;
        Image openIcon = ImageCache.loadImage("laf/icon/directory.png", MainFrame.class);
        openButton = new JButton("Open", new ImageIcon(openIcon));
        openButton.addActionListener(e -> open());
        add(openButton, constraints);

        constraints.gridx = 2;
        Image clearIcon = ImageCache.loadImage("laf/icon/cancel.png", MainFrame.class);
        clearButton = new JButton("Clear", new ImageIcon(clearIcon));
        clearButton.addActionListener(e -> clear());
        add(clearButton, constraints);

        // No insets for the las label
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(0, 0, 0, 0);

        // Loaded file label (row 2, cols 1 <-> 2)
        constraints.gridy = 2;
        constraints.gridx = 1;

        constraints.gridwidth = 2;
        loadLabel = new JLabel("No file selected");
        add(loadLabel, constraints);
    }

    private void open() {
        int returnCode = MainFrame.fileChooser.showOpenDialog(this);
        if (returnCode != JFileChooser.APPROVE_OPTION) return;

        File f = MainFrame.fileChooser.getSelectedFile();
        if (!f.exists()) {
            JOptionPane.showMessageDialog(
                this,
                "File does not exist.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        loadedFile = f;
        defaultBox.setEnabled(false);
        loadLabel.setText("Loaded file \"" + loadedFile.getName() + "\"");
    }

    private void clear() {
        loadedFile = null;
        defaultBox.setEnabled(true);
        loadLabel.setText("No file selected");
    }

    public Resource getSelectedResource() {
        if (loadedFile != null) {
            return new Resource(loadedFile);
        }

        String path = String.format(filePattern, defaultBox.getSelectedItem());
        return new Resource(path, resourceBase::getResourceAsStream);
    }
}
