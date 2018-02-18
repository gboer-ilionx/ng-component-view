package com.gerhardboer.fileditor.providers.treestructure;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.projectView.ProjectViewNode;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class NgComponentViewFileNode extends ProjectViewNode<VirtualFile> {

    public NgComponentViewFileNode(Project project, VirtualFile value, ViewSettings viewSettings) {
        super(project, value, viewSettings);
    }

    @Override
    public boolean contains(@NotNull VirtualFile virtualFile) {
        return false;
    }

    @NotNull
    @Override
    public Collection<? extends AbstractTreeNode> getChildren() {
        return null;
    }

    @Override
    protected void update(PresentationData presentationData) {

    }
}
