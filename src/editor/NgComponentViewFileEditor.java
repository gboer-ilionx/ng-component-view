package editor;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import editor.model.NgComponentEditor;
import editor.model.NgComponentEditorHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.List;

public class NgComponentViewFileEditor implements FileEditor {

    private JComponent editorPanel;

    private NgComponentEditorHolder editors;

    private VirtualFile componentDirectory;


    NgComponentViewFileEditor(Project project, VirtualFile virtualFile) {
        this.componentDirectory = virtualFile.getParent();
        this.editors = new NgComponentEditorHolder(project, componentDirectory);

        initView();
    }

    private void initView() {
        this.editorPanel = new JPanel(new BorderLayout());

        JPanel options = new JPanel(new FlowLayout());

        for (NgComponentEditor editor : this.editors.all()) {
            JCheckBox box = new JCheckBox("test");
            box.setActionCommand(editor.name);
            box.setSelected(true);
            box.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String actionCommand = e.getActionCommand();

                }
            });
            options.add(box);
        }

        this.editorPanel.add(options, BorderLayout.NORTH);

        JPanel content = new JPanel();
        gridlayout(content);
        this.editorPanel.add(content, BorderLayout.CENTER);
    }

    private void splitGrid(JComponent content) {
        content.setLayout(new GridLayout(1, 1));
        JPanel splits = new JPanel();

        splitpane(splits);

        content.add(splits);
    }


    private void gridlayout(JComponent panel) {
        List<NgComponentEditor> editors = this.editors.all();
        panel.setLayout(new GridLayout(1, editors.size()));

        for (NgComponentEditor editor : editors) {
            panel.add(editor.view);
        }
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
