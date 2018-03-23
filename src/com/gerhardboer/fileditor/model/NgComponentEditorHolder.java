package com.gerhardboer.fileditor.model;

import com.gerhardboer.fileditor.state.NgComponentViewState;
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.gerhardboer.fileditor.Constants.COMPONENT_DELIMITER;

public class NgComponentEditorHolder {

  private Project project;
  public List<NgComponentEditor> all;

  private NgComponentEditor component;
  private NgComponentEditor template;
  private NgComponentEditor styling;
  private NgComponentEditor spec;

  private NgComponentViewState.NgEditorOpenFileState state;

  public NgComponentEditorHolder(Project project,
                                 VirtualFile componentDirectory,
                                 NgComponentViewState.NgEditorOpenFileState state) {
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
    VirtualFile component = getComponentFiles(files, hasExtension(".ts").and(hasExtension(".spec.ts").negate()));
    VirtualFile template = getComponentFiles(files, hasExtension(".html"));
    VirtualFile styling = getComponentFiles(files, hasExtension(".css", ".scss", ".less"));
    VirtualFile spec = getComponentFiles(files, hasExtension(".spec.ts"));

    this.component = createNgComponentEditor(component, "component");
    this.template = createNgComponentEditor(template, "template");
    this.styling = createNgComponentEditor(styling, "style");
    this.spec = createNgComponentEditor(spec, "spec");
  }

  private void initComponentList() {
    NgComponentEditor[] components = new NgComponentEditor[]{
        this.component, this.template, this.styling, this.spec
    };

    this.all = Arrays.stream(components)
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

  private Predicate<VirtualFile> hasExtension(String... extensions) {
    return (VirtualFile f) -> Arrays.stream(extensions)
        .anyMatch(
            (extension -> f.getName().endsWith(extension))
        );
  }

  private NgComponentEditor createNgComponentEditor(VirtualFile f, String type) {
    return f != null
        ? createComponentEditorWithState(f, type)
        : null;
  }

  private NgComponentEditor createComponentEditorWithState(VirtualFile f, String type) {
    boolean isActive = this.state.get(f.getName());

    return new NgComponentEditor(createEditor(f), f.getName(), isActive, type);
  }


  private JComponent createEditor(VirtualFile f) {
    return TextEditorProvider.getInstance().createEditor(this.project, f).getComponent();
  }
}
