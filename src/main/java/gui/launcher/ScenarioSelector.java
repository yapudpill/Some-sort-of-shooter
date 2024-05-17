package gui.launcher;

import java.awt.GridBagConstraints;

import javax.swing.JCheckBox;

import model.level.MapModel;
import util.Resource;

public class ScenarioSelector extends ResourceSelector {
    private final JCheckBox marathonCheckbox;

    public ScenarioSelector() {
        super("scenario", "scenarioIndex", "scenario/%s.xml", MapModel.class);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        constraints.weightx = 1;
        constraints.weighty = 1;

        marathonCheckbox = new JCheckBox("Marathon mode");
        marathonCheckbox.addItemListener(e -> toggleMarathon());
        marathonCheckbox.setSelected(true);
        add(marathonCheckbox, constraints);
    }

    private void toggleMarathon() {
        boolean notMarathon = !marathonCheckbox.isSelected();
        defaultBox.setEnabled(notMarathon && loadedFile == null);
        openButton.setEnabled(notMarathon);
        clearButton.setEnabled(notMarathon);
        loadLabel.setEnabled(notMarathon);
    }

    @Override
    public Resource getSelectedResource() {
        if (marathonCheckbox.isSelected()) {
            return null;
        }
        return super.getSelectedResource();
    }
}
