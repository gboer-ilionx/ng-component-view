package com.gerhardboer.fileditor.model;

import com.gerhardboer.fileditor.state.NgComponentViewState;
import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.gerhardboer.fileditor.Constants.COMPONENT_DELIMITER;
import static com.gerhardboer.fileditor.Constants.SPEC_DELIMITER;

public class NgComponentEditorHolder {

    private Project project;
    public List<NgComponentEditor> all;

    private NgComponentEditor component;
    private NgComponentEditor template;
    private NgComponentEditor styling;

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
        VirtualFile component = getComponentFiles(files, ".ts");
        VirtualFile template = getComponentFiles(files, ".html");
        VirtualFile styling = getComponentFiles(files, ".css");


        this.component = createNgComponentEditor(component);
        this.template = createNgComponentEditor(template);
        this.styling = createNgComponentEditor(styling);
    }

    private void initComponentList() {
        NgComponentEditor[] components = new NgComponentEditor[]{
                this.component, this.template, this.styling
        };

        this.all = Arrays.stream(components)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private VirtualFile getComponentFiles(List<VirtualFile> files, String extension) {
        List<VirtualFile> file = files.stream()
                .filter((VirtualFile f) -> f.getName().contains(COMPONENT_DELIMITER))
                .filter((VirtualFile f) -> !f.getName().contains(SPEC_DELIMITER))
                .filter((VirtualFile f) -> f.getName().endsWith(extension))
                .collect(Collectors.toList());

        return file.size() == 1
                ? file.get(0)
                : null;
    }

    private NgComponentEditor createNgComponentEditor(VirtualFile f) {
        return f != null
                ? createComponentEditorWithState(f)
                : null;
    }

    private NgComponentEditor createComponentEditorWithState(VirtualFile f) {
        boolean isActive = this.state.get(f.getName());

        return new NgComponentEditor(createEditor(f), f.getName(), isActive);
    }


    private JComponent createEditor(VirtualFile f) {
        return TextEditorProvider.getInstance().createEditor(this.project, f).getComponent();
    }
}
