package editor.view;

import editor.model.NgComponentEditor;
import editor.model.NgComponentEditorHolder;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NgComponentPanel extends JPanel {

    private JPanel main;
    private NgComponentEditorHolder editors;

    public NgComponentPanel(NgComponentEditorHolder editors) {
        this.editors = editors;
        this.main = new JPanel();

        this.setLayout(new BorderLayout());

        init();
    }

    private void init() {
        setGrid(main);
        add(main, BorderLayout.CENTER);

        addOptions();
    }

    private void addOptions() {
        JPanel options = new JPanel(new FlowLayout());

        for (NgComponentEditor editor : this.editors.all) {
            JCheckBox box = new JCheckBox(editor.name);
            box.setActionCommand(editor.name);
            box.setSelected(true);
            box.addActionListener(e -> {
                JCheckBox box1 = (JCheckBox) e.getSource();
                editor.active = box1.isSelected();
                recalculateContent();
            });
            options.add(box);
        }

        this.add(options, BorderLayout.NORTH);
    }

    private void recalculateContent() {
        this.main.removeAll();
        setGrid(this.main);
    }


    private void setGrid(JComponent panel) {
        List<NgComponentEditor> editors = this.editors.activeWindows();
        panel.setLayout(new GridLayout(1, editors.size()));

        for (NgComponentEditor editor : editors) {
            panel.add(editor.view);
        }

        panel.updateUI();
    }


    private void splitGrid(JComponent content) {
        content.setLayout(new GridLayout(1, 1));
        JPanel splits = new JPanel();

        splitpane(splits);

        content.add(splits);
    }

    private void splitpane(JComponent panel) {
        panel.setLayout(new FlowLayout());

        JSplitPane sp1 = createSplit(this.editors.component.view, this.editors.template.view);

        JSplitPane sp2 = createSplit(sp1, this.editors.styling.view);

        panel.add(sp2);
    }

    private JSplitPane createSplit(JComponent left, JComponent right) {
        return new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
    }

    private void gridbag(JComponent panel) {
        panel.setLayout(new GridBagLayout());

        GridBagConstraints componentLayout = new GridBagConstraints();
        componentLayout.fill = GridBagConstraints.HORIZONTAL;
        componentLayout.gridx = 0;
        componentLayout.gridy = 0;
        componentLayout.weightx = 0.5;
        panel.add(this.editors.component.view, componentLayout);

        GridBagConstraints templateLayout = new GridBagConstraints();
        templateLayout.fill = GridBagConstraints.VERTICAL;
        templateLayout.gridx = 0;
        templateLayout.gridy = 0;
        templateLayout.weightx = 0.5;
        panel.add(this.editors.template.view);

        GridBagConstraints stylingLayout = new GridBagConstraints();
        stylingLayout.fill = GridBagConstraints.VERTICAL;
        stylingLayout.gridx = 0;
        stylingLayout.gridy = 0;
        stylingLayout.weightx = 0.2;
        panel.add(this.editors.styling.view);
    }

}
