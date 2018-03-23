package com.gerhardboer.fileditor.model;

import com.gerhardboer.fileditor.FileType;
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.gerhardboer.fileditor.Constants.COMPONENT_DELIMITER;

public class NgComponentEditorHolder {

  private Project project;
  public List<NgComponentEditor> all;

  private Map<FileType, NgComponentEditor> editors = new TreeMap<>();
  private Map<FileType, Boolean> state;

  public NgComponentEditorHolder(Project project,
                                 VirtualFile componentDirectory,
                                 Map<FileType, Boolean> state) {
    this.project = project;
    this.state = state;

    this.init(componentDirectory);
  }

  public List<NgComponentEditor> activeWindows() {
    return this.all.stream()
        .filter(NgComponentEditor::isActive)
        .collect(Collectors.toList());
  }

  private void init(VirtualFile componentDirectory) {
    List<VirtualFile> files = Arrays.asList(componentDirectory.getChildren());

    initNgComponents(files);
    initComponentList();
  }

  private void initNgComponents(List<VirtualFile> files) {
    Arrays.stream(FileType.values()).forEach(fileType -> {
      VirtualFile virtualFile = getComponentFiles(files, fileType::appliesToVirtualFile);
      this.editors.put(fileType, createNgComponentEditor(virtualFile, fileType.getDisplayName()));
    });
    }

  private void initComponentList() {
    this.all = this.editors.values().stream()
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
  }

  private VirtualFile getComponentFiles(List<VirtualFile> files, Predicate<VirtualFile> predicate) {
    List<VirtualFile> file = files.stream()
        .filter((VirtualFile f) -> f.getName().contains(COMPONENT_DELIMITER))
        .filter(predicate)
                .collect(Collectors.toList());

    return file.size() == 1
        ? file.get(0)
        : null;
  }

  private NgComponentEditor createNgComponentEditor(VirtualFile f, String type) {
    return f != null
        ? createComponentEditorWithState(f, type)
        : null;
  }

  private NgComponentEditor createComponentEditorWithState(VirtualFile f, String type) {
    boolean isActive = this.state.getOrDefault(f.getName(), true);

    return new NgComponentEditor(createEditor(f), f.getName(), isActive, type);
  }


  private JComponent createEditor(VirtualFile f) {
    return TextEditorProvider.getInstance().createEditor(this.project, f).getComponent();
  }
}
