package com.gerhardboer.fileditor.state;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
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

  private Map<String, NgComponentFileState> fileStates = new HashMap<>();

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

  public NgComponentFileState getFileState(String componentName) {

    NgComponentFileState recordedState = this.fileStates.get(componentName);
    if (recordedState == null) {
      recordedState = new NgComponentFileState();
      this.fileStates.put(componentName, recordedState);
    }

    return recordedState;
  }

}

