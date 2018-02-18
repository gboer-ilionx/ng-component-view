package com.gerhardboer.fileditor.providers.treestructure;

import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class NgComponentViewTreeStructureProvider implements TreeStructureProvider {

    @NotNull
    @Override
    public Collection<AbstractTreeNode> modify(@NotNull AbstractTreeNode parent,
                                               @NotNull Collection<AbstractTreeNode> children,
                                               ViewSettings settings) {
        Project project = ProjectManager.getInstance().getOpenProjects()[0];

        ArrayList<AbstractTreeNode> nodes = new ArrayList<AbstractTreeNode>();
        for (AbstractTreeNode child : children) {
            if (child instanceof PsiFileNode) {
                boolean isDirectory = ((PsiFileNode) child).getVirtualFile().isDirectory();

                if (isDirectory) {
                    boolean isComponentDirectory = Arrays.stream(((PsiFileNode) child).getVirtualFile().getChildren())
                            .anyMatch((VirtualFile f) -> f.getName().contains(".component."));

                    if (isComponentDirectory) {
                        nodes.add(new NgComponentViewFileNode(project, null, null));
                    }
                }
            }
            nodes.add(child);
        }
        return nodes;
    }

    @Nullable
    @Override
    public Object getData(Collection<AbstractTreeNode> collection, String s) {
        return null;
    }
}