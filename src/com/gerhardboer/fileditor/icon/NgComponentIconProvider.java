package com.gerhardboer.fileditor.icon;

import com.gerhardboer.fileditor.Constants;
import com.intellij.ide.IconProvider;
import com.intellij.openapi.util.IconLoader;
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
        if (containingFile != null) {
            boolean isNgComponentView = containingFile.getVirtualFile().getExtension().equals(Constants.EXTENSION);
            if (isNgComponentView) {
                return IconLoader.getIcon("/assets/ng-component-view.png");
            }
        }
        return null;
    }
}
