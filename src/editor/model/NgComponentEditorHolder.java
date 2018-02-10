package editor.model;

import com.intellij.openapi.fileEditor.impl.text.TextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NgComponentEditorHolder {


    private Project project;

    public NgComponentEditor component;
    public NgComponentEditor template;
    public NgComponentEditor styling;


    public NgComponentEditorHolder(Project project, VirtualFile componentDirectory) {
        this.project = project;
        this.initComponentParts(componentDirectory);
    }


    public List<NgComponentEditor> all() {
        NgComponentEditor[] components = new NgComponentEditor[]{
                this.component, this.template, this.styling
        };

        return Arrays.stream(components)
                .filter((NgComponentEditor e) -> e.view != null)
                .collect(Collectors.toList());
    }

    private void initComponentParts(VirtualFile componentDirectory) {
        List<VirtualFile> files = Arrays.asList(componentDirectory.getChildren());

        VirtualFile component = getComponentFiles(files, ".ts");
        VirtualFile template = getComponentFiles(files, ".html");
        VirtualFile styling = getComponentFiles(files, ".css");


        this.component = createNgComponentEditor(component);
        this.template = createNgComponentEditor(template);
        this.styling = createNgComponentEditor(styling);
    }

    private VirtualFile getComponentFiles(List<VirtualFile> files, String extension) {
        List<VirtualFile> file = files.stream()
                .filter((VirtualFile f) -> f.getName().contains(".view."))
                .filter((VirtualFile f) -> !f.getName().contains(".spec."))
                .filter((VirtualFile f) -> f.getName().endsWith(extension))
                .collect(Collectors.toList());

        return file.size() == 1
                ? file.get(0)
                : null;
    }

    private NgComponentEditor createNgComponentEditor(VirtualFile f) {
        return f != null
                ? new NgComponentEditor(createEditor(f), f.getName())
                : null;
    }

    private JComponent createEditor(VirtualFile f) {
        return TextEditorProvider.getInstance().createEditor(this.project, f).getComponent();
    }
}
