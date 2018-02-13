package com.gerhardboer.fileditor;

import com.gerhardboer.fileditor.model.NgComponentEditorHolder;
import com.gerhardboer.fileditor.state.NgComponentViewState;
import com.gerhardboer.fileditor.view.NgComponentPanel;
import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.beans.PropertyChangeListener;

public class NgComponentViewFileEditor implements FileEditor {

    private JComponent editorPanel;

    private VirtualFile componentDirectory;

    public NgComponentViewFileEditor(Project project, VirtualFile virtualFile) {
        NgComponentViewState state = NgComponentViewState.getInstance(project);
        this.componentDirectory = virtualFile.getParent();

        NgComponentViewState.NgEditorOpenFileState fileState =
                state.getFileState(componentDirectory.getName());

        NgComponentEditorHolder editorHolder = new NgComponentEditorHolder(
                project, componentDirectory, fileState
        );

        initView(editorHolder, fileState);
    }

    private void initView(NgComponentEditorHolder editorHolder, NgComponentViewState.NgEditorOpenFileState fileState) {
        this.editorPanel = new NgComponentPanel(editorHolder, fileState);
    }

    @NotNull
    @Override
    public JComponent getComponent() {
        return editorPanel;
    }

    @Nullable
    @Override
    public JComponent getPreferredFocusedComponent() {
        return editorPanel;
    }

    @NotNull
    @Override
    public String getName() {
        return componentDirectory.getName();
    }

    @Override
    public void setState(@NotNull FileEditorState fileEditorState) {

    }

    @Override
    public boolean isModified() {
        return false;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void selectNotify() {

    }

    @Override
    public void deselectNotify() {

    }

    @Override
    public void addPropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {

    }

    @Override
    public void removePropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {

    }

    @Nullable
    @Override
    public BackgroundEditorHighlighter getBackgroundHighlighter() {
        return null;
    }

    @Nullable
    @Override
    public FileEditorLocation getCurrentLocation() {
        return null;
    }

    @Override
    public void dispose() {

    }

    @Nullable
    @Override
    public <T> T getUserData(@NotNull Key<T> key) {
        return null;
    }

    @Override
    public <T> void putUserData(@NotNull Key<T> key, @Nullable T t) {

    }

}
