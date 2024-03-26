package gui.editor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import controller.MainController;

public class EditorMenu extends JPanel {
    private static final int DEFAULT_ROWS = 20;
    private static final int DEFAULT_COLS = 20;
    private static final JFileChooser fileChooser = new JFileChooser();

    private final EditorModel model;

    public EditorMenu(MainController mainController) {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Global constraints
        constraints.weightx = 1;

        // Title (row 0)
        constraints.gridy = 0;
        constraints.gridwidth = 4;

        constraints.gridx = 0;
        add(new JLabel("INTERACTIVE EDITOR"), constraints);

        // Size labels (row 1)
        constraints.gridy = 1;
        constraints.gridwidth = 2;

        constraints.gridx = 0;
        add(new JLabel("Number of rows"), constraints);

        constraints.gridx = 2;
        add(new JLabel("Number of columns"), constraints);

        // Size selection (row 2)
        constraints.gridy = 2;
        constraints.gridwidth = 2;

        constraints.gridx = 0;
        JSpinner rows = new JSpinner(new SpinnerNumberModel(DEFAULT_ROWS, 5, 25, 1));
        add(rows, constraints);

        constraints.gridx = 2;
        JSpinner cols = new JSpinner(new SpinnerNumberModel(DEFAULT_COLS, 5, 25, 1));
        add(cols, constraints);


        constraints.fill = GridBagConstraints.BOTH;

        // Interactive grid (row 3)
        constraints.gridy = 3;
        constraints.gridwidth = 4;

        constraints.gridx = 0;
        constraints.weighty = 1;

        model = new EditorModel(DEFAULT_ROWS, DEFAULT_COLS);
        EditorGrid grid = new EditorGrid(model);
        JPanel gridContainer = new JPanel();
        gridContainer.add(grid);
        add(gridContainer, constraints);

        constraints.weighty = 0;

        rows.addChangeListener(e -> {
            model.setRows((int) rows.getValue());
            grid.reset();
        });
        cols.addChangeListener(e -> {
            model.setCols((int) cols.getValue());
            grid.reset();
        });

        // Buttons (row 4)
        constraints.gridy = 4;
        constraints.gridwidth = 1;

        constraints.gridx = 0;
        JButton menu = new JButton("Home menu");
        menu.addActionListener(e -> mainController.loadHomeMenu());
        add(menu, constraints);

        constraints.gridx = 1;
        JButton clear = new JButton("Clear");
        clear.addActionListener(e -> {
            model.reset();
            grid.reset();
        });
        add(clear, constraints);

        constraints.gridx = 2;
        JButton open = new JButton("Open");
        add(open, constraints);

        constraints.gridx = 3;
        JButton save = new JButton("Save");
        save.addActionListener(e -> save());
        add(save, constraints);
    }

    private void save() {
        int returnCode = fileChooser.showSaveDialog(this);
        if (returnCode != JFileChooser.APPROVE_OPTION) return;

        File f = fileChooser.getSelectedFile();
        if (f.exists()) {
            int response = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to overwrite the contents of this file?",
                "Override file",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            if (response != JOptionPane.YES_OPTION) return;
        }

        try {
            model.writeFile(f);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                this,
                "Something went wrong.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}