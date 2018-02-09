package editor;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.injected.editor.EditorWindowImpl;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.fileEditor.impl.EditorWindow;
import com.intellij.openapi.fileEditor.impl.EditorWindowHolder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.impl.source.CodeFragmentElement;
import com.intellij.ui.EditorTextField;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentViewFileEditor implements FileEditor {

    private JComponent editorPanel;

    private List<VirtualFile> componentParts;


    ComponentViewFileEditor(Project project, VirtualFile virtualFile) {
        initComponentParts(virtualFile);
        initView(virtualFile);
    }

    private void initView(VirtualFile virtualFile) {
        this.editorPanel = new JPanel();
        this.editorPanel.setLayout(new BorderLayout());


        this.editorPanel.add(createTitle(), BorderLayout.PAGE_START);


        this.editorPanel.add(createEditor(componentParts.get(0)), BorderLayout.LINE_START);
        this.editorPanel.add(createEditor(componentParts.get(1)), BorderLayout.CENTER);
        this.editorPanel.add(createEditor(componentParts.get(2)), BorderLayout.LINE_END);
    }

    private JTextField createTitle() {

        JTextField title = new JTextField();

        String fileNames = componentParts.stream()
                .map(VirtualFile::getName)
                .collect(Collectors.joining(", "));

        title.setText(fileNames);

        return title;
    }

    private JTextArea createEditor(VirtualFile f) {
        JTextArea editor = new JTextArea();
        editor.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        editor.setText(FileDocumentManager.getInstance().getDocument(f).getText());
        return editor;
    }

    private void initComponentParts(VirtualFile virtualFile) {
        this.componentParts = Arrays.asList(virtualFile.getParent().getChildren());
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
        return "component-view";
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
