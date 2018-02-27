package com.gerhardboer.fileditor.providers;

import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.fileEditor.ex.FileEditorProviderManager;
import com.intellij.openapi.fileEditor.impl.EditorTabTitleProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.LightVirtualFile;
import org.jetbrains.annotations.Nullable;

public class NgComponentViewTitleProvider implements EditorTabTitleProvider {

    @Nullable
    @Override
    public String getEditorTabTitle(Project project, VirtualFile virtualFile) {
        FileEditorProvider[] editors = FileEditorProviderManager.getInstance().getProviders(project, virtualFile);
        VirtualFile original = getFile(virtualFile);

        if (accept(editors)) {
            return original.getParent().getName();
        }

        return original.getName();
    }

    private VirtualFile getFile(VirtualFile virtualFile) {
        if (virtualFile instanceof LightVirtualFile) {
            return ((LightVirtualFile) virtualFile).getOriginalFile();

        }

        return virtualFile;
    }

    private boolean accept(FileEditorProvider[] editors) {
        return editors.length > 0 && editors[0] instanceof NgComponentViewFileEditorProvider;
    }
}
