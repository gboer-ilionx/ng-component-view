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

        init();
    }

    private void init() {
        setBorderLayout();

        addMain();
        addOptions();
    }

    private void setBorderLayout() {
        this.setLayout(new BorderLayout());
    }

    private void addMain() {
        setGrid(main);
        add(this.main, BorderLayout.CENTER);
    }

    private void setGrid(JComponent panel) {
        List<NgComponentEditor> editors = this.editors.activeWindows();
        panel.setLayout(new GridLayout(1, editors.size()));

        for (NgComponentEditor editor : editors) {
            panel.add(editor.view);
        }

        panel.updateUI();
    }

    private void addOptions() {
        JPanel options = new JPanel(new FlowLayout());

        for (NgComponentEditor editor : this.editors.all) {
            JCheckBox box = createHideShow(editor);
            options.add(box);
        }

        this.add(options, BorderLayout.NORTH);
    }

    private JCheckBox createHideShow(NgComponentEditor editor) {
        String name = editor.name;

        JCheckBox box = new JCheckBox(name);
        box.setActionCommand(name);
        box.setSelected(true);

        box.addActionListener(e -> {
            JCheckBox box1 = (JCheckBox) e.getSource();
            editor.active = box1.isSelected();
            recalculateContent();
        });

        return box;
    }

    private void recalculateContent() {
        this.main.removeAll();
        setGrid(this.main);
    }

}
