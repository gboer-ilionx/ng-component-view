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

    private void initComponentParts(VirtualFile componentDirectory) {
        List<VirtualFile> files = Arrays.asList(componentDirectory.getChildren());

        VirtualFile component = getComponentFiles(files, ".ts");
        VirtualFile template = getComponentFiles(files, ".html");
//        VirtualFile styling = getComponentFiles(files, ".css");


        this.component = createEditor(component);
        this.template = createEditor(template);
//        this.styling = createEditor(styling);
    }

    private VirtualFile getComponentFiles(List<VirtualFile> files, String extension) {
        return files.stream()
                .filter((VirtualFile f) -> f.getName().contains(".component."))
                .filter((VirtualFile f) -> f.getName().endsWith(extension))
                .collect(Collectors.toList())
                .get(0);
    }

    private JComponent createEditor(VirtualFile f) {
        return TextEditorProvider.getInstance().createEditor(this.project, f).getComponent();
    }
}
