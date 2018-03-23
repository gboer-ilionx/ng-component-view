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
import com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.util.Map;

import static com.gerhardboer.fileditor.ShortName.shortName;

public class NgComponentViewFileEditor implements FileEditor {

    private JComponent editorPanel;
    private String shortName;

    public NgComponentViewFileEditor(Project project, VirtualFile virtualFile) {

        NgComponentViewState state = NgComponentViewState.getInstance(project);
        VirtualFile componentDirectory = ((LightVirtualFile) virtualFile).getOriginalFile().getParent();
        this.shortName = shortName(virtualFile);

        Map<FileType, Boolean> fileState = state.getFileState(this.shortName);

        NgComponentEditorHolder editorHolder = new NgComponentEditorHolder(
                project, componentDirectory, shortName, fileState
        );

        initView(editorHolder, fileState);
    }

    private void initView(NgComponentEditorHolder editorHolder, Map<FileType, Boolean> fileState) {
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
        return this.shortName;
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
        return true;
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
