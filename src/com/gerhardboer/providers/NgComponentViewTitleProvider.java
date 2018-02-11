package com.gerhardboer.providers;

import com.intellij.openapi.fileEditor.FileEditorProvider;
import com.intellij.openapi.fileEditor.ex.FileEditorProviderManager;
import com.intellij.openapi.fileEditor.impl.EditorTabTitleProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;

public class NgComponentViewTitleProvider implements EditorTabTitleProvider {

    @Nullable
    @Override
    public String getEditorTabTitle(Project project, VirtualFile virtualFile) {
        FileEditorProvider[] editors = FileEditorProviderManager.getInstance().getProviders(project, virtualFile);

        if (accept(editors)) {
            return virtualFile.getParent().getName();
        }

        return virtualFile.getName();
    }

    private boolean accept(FileEditorProvider[] editors ) {
        return editors.length > 0 && editors[0] instanceof NgComponentViewFileEditorProvider;
    }
}
