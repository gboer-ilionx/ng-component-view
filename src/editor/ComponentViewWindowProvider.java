package editor;

import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class ComponentViewWindowProvider implements FileEditorProvider {


    public static final String COMPONENT_VIEW_EDITOR_TYPE_ID = "ng-component-view";

    @Override
    public boolean accept(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        return virtualFile.getName().contains(".component.") && !virtualFile.getName().contains(".spec.");
    }

    @NotNull
    @Override
    public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile virtualFile) {
        return new ComponentViewFileEditor(project, virtualFile);
    }

    @NotNull
    @Override
    public String getEditorTypeId() {
        return COMPONENT_VIEW_EDITOR_TYPE_ID;
    }

    @NotNull
    @Override
    public FileEditorPolicy getPolicy() {
        return FileEditorPolicy.HIDE_DEFAULT_EDITOR;
    }

}
