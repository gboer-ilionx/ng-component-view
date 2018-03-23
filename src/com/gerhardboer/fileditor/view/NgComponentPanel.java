package com.gerhardboer.fileditor.view;

import com.gerhardboer.fileditor.FileType;
import com.gerhardboer.fileditor.model.NgComponentEditor;
import com.gerhardboer.fileditor.model.NgComponentEditorHolder;
import com.gerhardboer.fileditor.state.NgComponentFileState;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NgComponentPanel extends JPanel {

  private JPanel main;
  private NgComponentEditorHolder editors;

  private NgComponentFileState fileState;

  public NgComponentPanel(NgComponentEditorHolder editors,
                          NgComponentFileState fileState) {
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

    box.setSelected(this.fileState.get(fileName));

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
        this.fileState.put(fileType, newState)
    );
  }

  private void recalculateContent() {
    this.main.removeAll();
    setGrid(this.main);
  }

}
