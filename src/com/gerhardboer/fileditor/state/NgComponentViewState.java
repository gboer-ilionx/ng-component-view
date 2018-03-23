package com.gerhardboer.fileditor.state;

import com.gerhardboer.fileditor.FileType;
import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@State(
        name = "NgComponentViewState",
        storages = {
                @Storage(StoragePathMacros.WORKSPACE_FILE)
        }
)
public class NgComponentViewState implements PersistentStateComponent<NgComponentViewState> {

    private Map<String, Map<FileType, Boolean>> fileStates = new HashMap<>();

    @Nullable
    @Override
    public NgComponentViewState getState() {
        return this;
    }

    @Override
    public void loadState(NgComponentViewState ngComponentViewState) {
        XmlSerializerUtil.copyBean(ngComponentViewState, this);
    }

    @Nullable
    public static NgComponentViewState getInstance(Project project) {
        return ServiceManager.getService(project, NgComponentViewState.class);
    }

    public Map<FileType, Boolean> getFileState(String fileName) {
        Map<FileType, Boolean> currentState = this.fileStates.get(fileName);
        if (currentState == null) {
            currentState = new HashMap<>();
            this.fileStates.put(fileName, currentState);
        }

        return currentState;
    }
}

