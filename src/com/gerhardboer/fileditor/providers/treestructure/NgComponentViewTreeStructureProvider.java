package com.gerhardboer.fileditor.providers.treestructure;

import com.gerhardboer.fileditor.Constants;
import com.gerhardboer.fileditor.ShortName;
import com.intellij.ide.projectView.TreeStructureProvider;
import com.intellij.ide.projectView.ViewSettings;
import com.intellij.ide.projectView.impl.nodes.PsiFileNode;
import com.intellij.ide.util.treeView.AbstractTreeNode;
import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Objects;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;

public class NgComponentViewTreeStructureProvider implements TreeStructureProvider {

  @NotNull
  @Override
  public Collection<AbstractTreeNode> modify(@NotNull AbstractTreeNode parent,
                                             @NotNull Collection<AbstractTreeNode> children,
                                             ViewSettings settings) {

    return concat(
            children.stream(),
            children.stream()
                    .filter(node -> node instanceof PsiFileNode)
                    .map(node -> ((PsiFileNode) node).getVirtualFile())
                    .filter(Objects::nonNull)
                    .collect(groupingBy(ShortName::shortName))
                    .entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().size() > 1)
                    .map(entry -> createNgViewPsiNode(parent.getProject(), entry.getKey(), entry.getValue().get(0), settings)))
            .collect(toList());

  }

  private PsiFileNode createNgViewPsiNode(Project project, String shortName, VirtualFile originalFile, ViewSettings settings) {

    PsiFile psiFile = PsiFileFactory.getInstance(project).createFileFromText(
        shortName + "." + Constants.EXTENSION,
        Language.findLanguageByID("TEXT"),
        "", true, true, false, originalFile
    );

    return new PsiFileNode(project, psiFile, settings);
  }

  @Nullable
  @Override
  public Object getData(Collection<AbstractTreeNode> collection, String s) {
    return null;
  }
}