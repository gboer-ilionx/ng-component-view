package com.gerhardboer.fileditor.state;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@State(
    name = "NgComponentViewState",
    storages = {
        @Storage(
            id = "ng-component-view",
            value = "ng-component-view.xml"
        )
    }
)
public class NgComponentViewState implements PersistentStateComponent<NgComponentViewState> {

  private Map<String, NgEditorOpenFileState> fileStates = new HashMap<>();

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

  public NgEditorOpenFileState getFileState(String fileName) {
    NgEditorOpenFileState currentState = this.fileStates.get(fileName);
    if (currentState == null) {
      currentState = new NgEditorOpenFileState();
      this.fileStates.put(fileName, currentState);
    }

    return currentState;
  }
}

