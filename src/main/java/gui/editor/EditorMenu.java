package gui.editor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import gui.MainFrame;
import gui.launcher.MapSelector;
import model.level.InvalidMapException;

public class EditorMenu extends JPanel {
    private static final int DEFAULT_ROWS = 20;
    private static final int DEFAULT_COLS = 20;

    private final EditorModel model;
    private final EditorGrid grid;
    private final JSpinner rows, cols;

    public EditorMenu(MainController mainController) {
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        // Global constraints
        constraints.weightx = 1;

        // Title (row 0)
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.gridwidth = 4;

        JLabel title = new JLabel("INTERACTIVE EDITOR");
        title.setName("titleLabel");
        add(title, constraints);

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
        rows = new JSpinner(new SpinnerNumberModel(DEFAULT_ROWS, 5, 25, 1));
        add(rows, constraints);

        constraints.gridx = 2;
        cols = new JSpinner(new SpinnerNumberModel(DEFAULT_COLS, 5, 25, 1));
        add(cols, constraints);

        // Insets for the rest of the components
        constraints.fill = GridBagConstraints.BOTH;
        constraints.insets = new Insets(10, 10, 10, 10);

        // Interactive grid (row 3)
        constraints.gridy = 3;
        constraints.gridx = 0;
        constraints.gridwidth = 4;
        constraints.weighty = 1;

        model = new EditorModel(DEFAULT_ROWS, DEFAULT_COLS);
        grid = new EditorGrid(model);
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
        clear.addActionListener(e -> { model.reset(); grid.reset(); });
        add(clear, constraints);

        constraints.gridx = 2;
        JButton open = new JButton("Open");
        open.addActionListener(e -> open());
        add(open, constraints);

        constraints.gridx = 3;
        JButton save = new JButton("Save");
        save.addActionListener(e -> save());
        add(save, constraints);
    }

    private void open() {
        MapSelector selector = new MapSelector();
        int response = JOptionPane.showConfirmDialog(
            this,
            selector,
            "Map selector",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        if (response != JOptionPane.OK_OPTION) return;

        try {
            model.readFile(selector.getSelectedResource());
            rows.setValue(model.getRows());
            cols.setValue(model.getCols());
            grid.reset();
        } catch (InvalidMapException e) {
            JOptionPane.showMessageDialog(
                this,
                "The selected file is not in the correct format.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void save() {
        if (model.getSpawn() == null) {
            JOptionPane.showMessageDialog(
                this,
                "No spawn point set.",
                "No spawn point",
                JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        int returnCode = MainFrame.fileChooser.showSaveDialog(this);
        if (returnCode != JFileChooser.APPROVE_OPTION) return;

        File f = MainFrame.fileChooser.getSelectedFile();
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