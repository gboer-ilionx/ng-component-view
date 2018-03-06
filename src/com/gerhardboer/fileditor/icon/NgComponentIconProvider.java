package com.gerhardboer.fileditor.icon;

import com.gerhardboer.fileditor.Constants;
import com.intellij.ide.IconProvider;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class NgComponentIconProvider extends IconProvider {
  @Nullable
  @Override
  public Icon getIcon(@NotNull PsiElement psiElement, int i) {
    PsiFile containingFile = psiElement.getContainingFile();
    if (containingFile == null) {
      return null;
    }

    VirtualFile file = containingFile.getVirtualFile();
    if (file == null) {
      return null;
    }

    boolean isNgComponentView = Constants.EXTENSION.equals(file.getExtension());
    return isNgComponentView
        ? IconLoader.getIcon("/assets/ng-component-view.png")
        : null;
  }
}
