import com.intellij.codeInsight.navigation.actions.GotoDeclarationAction;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.fileEditor.ex.FileEditorManagerEx;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.Nullable;

import static providers.NgComponentViewFileEditorProvider.COMPONENT_VIEW_EDITOR_TYPE_ID;

/**
 * Created by hendrikvonprince on 09/11/16.
 */
public class OpenInComponentView extends AnAction {

    private GotoDeclarationAction gotoDeclarationAction; // used as alternative, when no PsiElement is found as target

    public OpenInComponentView() {
        this.gotoDeclarationAction = new GotoDeclarationAction();

        this.setEnabledInModalContext(true);
    }

    public void actionPerformed(AnActionEvent e) {
        final PsiDirectory target = getTarget(e);

        if (target != null) {
            final Project project = PlatformDataKeys.PROJECT.getData(e.getDataContext());
            final FileEditorManagerEx fileEditorManager = FileEditorManagerEx.getInstanceEx(project);


            fileEditorManager.setSelectedEditor(target.getVirtualFile(), COMPONENT_VIEW_EDITOR_TYPE_ID);
            fileEditorManager.openFile(target.getVirtualFile(), true);
        } else {
            this.gotoDeclarationAction.actionPerformed(e);
        }
    }

    @Override
    public void update(AnActionEvent e) {
        PsiDirectory target = getTarget(e);
        if (target != null) {
            e.getPresentation().setEnabled(true);
        } else {
            // we found no target for our own action, but maybe we can execute the GotoDeclaration-action
            this.gotoDeclarationAction.update(e);
        }
    }


    /**
     * @return The first <code>PsiElement</code> that is found by the GotoDeclarationAction for the currently selected <code>PsiElement</code>
     */
    @Nullable
    private PsiDirectory getTarget(AnActionEvent e) {
        PsiFile psiFile = e.getData(LangDataKeys.PSI_FILE);


        if (psiFile == null && !psiFile.getName().contains(".view.")) {
            e.getPresentation().setEnabled(false);
            return null;
        }

        return psiFile.getParent();

    }
}
