package editor;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import editor.model.ComponentViewHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;

public class ComponentViewFileEditor implements FileEditor {

    private JComponent editorPanel;

    private ComponentViewHolder editors;

    private VirtualFile componentDirectory;


    ComponentViewFileEditor(Project project, VirtualFile virtualFile) {

        this.componentDirectory = virtualFile.getParent();
        this.editors = new ComponentViewHolder(project, componentDirectory);

        initView();
    }

    private void initView() {
        this.editorPanel = new JPanel();

        gridlayout(this.editorPanel);
//        splitpane(this.editorPanel);
//        gridbag(this.editorPanel);
    }

    private void gridlayout(JComponent editorPanel) {
        editorPanel.setLayout(new GridLayout(1, this.editors.numberOfFiles()));

        safeAddEditor(this.editors.component);
        safeAddEditor(this.editors.template);
        safeAddEditor(this.editors.styling);
    }

    private void safeAddEditor(JComponent c) {
        if (c != null) {
            editorPanel.add(c);
        }
    }

    private void splitpane(JComponent editorPanel) {
        editorPanel.setLayout(new FlowLayout());

        JSplitPane sp1 = createSplit(this.editors.component, this.editors.template);

        JSplitPane sp2 = createSplit(sp1, this.editors.styling);

        editorPanel.add(sp2);
    }

    private void gridbag(JComponent editorPanel) {
        editorPanel.setLayout(new GridBagLayout());

        GridBagConstraints componentLayout = new GridBagConstraints();
        componentLayout.fill = GridBagConstraints.HORIZONTAL;
        componentLayout.gridx = 0;
        componentLayout.gridy = 0;
        componentLayout.weightx = 0.5;
        editorPanel.add(this.editors.component, componentLayout);

        GridBagConstraints templateLayout = new GridBagConstraints();
        templateLayout.fill = GridBagConstraints.VERTICAL;
        templateLayout.gridx = 0;
        templateLayout.gridy = 0;
        templateLayout.weightx = 0.5;
        editorPanel.add(this.editors.template);

        GridBagConstraints stylingLayout = new GridBagConstraints();
        stylingLayout.fill = GridBagConstraints.VERTICAL;
        stylingLayout.gridx = 0;
        stylingLayout.gridy = 0;
        stylingLayout.weightx = 0.2;
        editorPanel.add(this.editors.styling);
    }

    private JSplitPane createSplit(JComponent left, JComponent right) {
        return new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
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
        return this.componentDirectory.getName();
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
