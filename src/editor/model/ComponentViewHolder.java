package editor.model;

import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ComponentViewHolder {


    private Project project;

    public JComponent component;
    public JComponent template;
    public JComponent styling;


    public ComponentViewHolder(Project project, VirtualFile componentDirectory) {
        this.project = project;
        this.initComponentParts(componentDirectory);
    }

    public int numberOfFiles() {
        int count = 0;

        count += fileExist(this.component);
        count += fileExist(this.template);
        count += fileExist(this.styling);

        return count;
    }

    private int fileExist(JComponent component) {
        return component == null
                ? 0
                : 1;
    }

    private void initComponentParts(VirtualFile componentDirectory) {
        List<VirtualFile> files = Arrays.asList(componentDirectory.getChildren());

        VirtualFile component = getComponentFiles(files, ".ts");
        VirtualFile template = getComponentFiles(files, ".html");
        VirtualFile styling = getComponentFiles(files, ".css");


        this.component = createEditor(component);
        this.template = createEditor(template);
        this.styling = createEditor(styling);
    }

    private VirtualFile getComponentFiles(List<VirtualFile> files, String extension) {
        List<VirtualFile> file = files.stream()
                .filter((VirtualFile f) -> f.getName().contains(".component."))
                .filter((VirtualFile f) -> f.getName().endsWith(extension))
                .collect(Collectors.toList());

        return file.size() == 1
                ? file.get(0)
                : null;
    }

    private JComponent createEditor(VirtualFile f) {
        return f != null
                ? TextEditorProvider.getInstance().createEditor(this.project, f).getComponent()
                : null;
    }
}
