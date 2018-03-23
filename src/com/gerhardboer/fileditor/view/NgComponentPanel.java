package com.gerhardboer.fileditor.view;

import com.gerhardboer.fileditor.FileType;
import com.gerhardboer.fileditor.model.NgComponentEditor;
import com.gerhardboer.fileditor.model.NgComponentEditorHolder;

import org.jdesktop.swingx.JXMultiSplitPane;
import org.jdesktop.swingx.MultiSplitLayout.Split;
import org.jdesktop.swingx.MultiSplitLayout.Node;
import org.jdesktop.swingx.MultiSplitLayout.Divider;
import org.jdesktop.swingx.MultiSplitLayout.Leaf;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Map;

public class NgComponentPanel extends JPanel {

    private JPanel main;
    private NgComponentEditorHolder editors;

    private Map<FileType, Boolean> fileState;

    public NgComponentPanel(NgComponentEditorHolder editors,
                            Map<FileType, Boolean> fileState) {
        this.editors = editors;
        this.main = new JPanel();
        this.fileState = fileState;

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
        setView(main);
        add(this.main, BorderLayout.CENTER);
    }

    private void setView(JComponent panel) {
        List<NgComponentEditor> editors = this.editors.activeWindows();
        panel.setLayout(new BorderLayout());

        // When there is just one editor we don't need MultiSplit
        if (editors.size() == 1) {
            panel.add(handleOneSizeList(editors));
        } else {
            panel.add(handleMultipleSizeList(editors));
        }

        panel.updateUI();
    }

    private void addOptions() {
        JPanel options = new JPanel();
        options.setLayout(new FlowLayout());
        for (NgComponentEditor editor : this.editors.all) {
            JCheckBox box = createHideShow(editor);
            options.add(box);
        }

        this.add(options, BorderLayout.SOUTH);
    }

    private JCheckBox createHideShow(NgComponentEditor editor) {

        JCheckBox box = new JCheckBox(editor.type);

        String fileName = editor.fileName;
        box.setActionCommand(fileName);
        box.setSelected(this.fileState.getOrDefault(fileName, true));

        box.addActionListener(e -> {
            JCheckBox box1 = (JCheckBox) e.getSource();
            editor.active = box1.isSelected();
            updateState(fileName, editor.active);

            recalculateContent();
        });

        return box;
    }

    private void updateState(String name, boolean newState) {
        FileType.forFileName(name).map(fileType ->
                this.fileState.put(fileType, newState));
    }

    private void recalculateContent() {
        this.main.removeAll();
        setView(this.main);
    }

    private JComponent handleOneSizeList(List<NgComponentEditor> editors) {
        return editors.get(0).view;
    }

    // Source for the idea of multisplitpane
    // https://stackoverflow.com/questions/6117826/jxmultisplitpane-how-to-use
    // NOTE setWeight does not work

    private JXMultiSplitPane handleMultipleSizeList(List<NgComponentEditor> editors) {
        JXMultiSplitPane sp = new JXMultiSplitPane();

        Split split = new Split();
        sp.setModel(split);
        // Keep track of the leafs in a List, to set later on on the split
        List<Node> nodeList = new ArrayList<>();
        // This calculates the width (weight) of the leaf. 100 / 4 / 100 = 0.25 (25%)
        double preferredSize = (double) 100 / editors.size() / 100;

        for (int i = 0; i < editors.size(); i++) {
            sp.add(editors.get(i).view, editors.get(i).fileName);
            Leaf leaf = new Leaf(editors.get(i).fileName);
            // Set the weight for the leaf
            leaf.setWeight(preferredSize);
            nodeList.add(leaf);
            // After the last leaf is added, a divider is no longer needed
            if (i != (editors.size() - 1)) {
                nodeList.add(new Divider());
            }
        }

        split.setChildren(nodeList);
        return sp;
    }
}