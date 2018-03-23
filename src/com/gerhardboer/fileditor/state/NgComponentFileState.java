package com.gerhardboer.fileditor.state;

import com.gerhardboer.fileditor.FileType;
import com.gerhardboer.fileditor.settings.NgComponentGlobalSettings;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NgComponentFileState {


  private Map<FileType, Boolean> state;

  NgComponentFileState() {
    this.state = new HashMap<>();
    initFromGlobalSettings();
  }

  private void initFromGlobalSettings() {
    NgComponentGlobalSettings globalSettings = NgComponentGlobalSettings.getInstance();

    Arrays.stream(FileType.values())
        .forEach(fileType ->
            state.put(fileType, globalSettings.getByType(fileType))
        );
  }


  public boolean get(String fileName) {
    return this.state.getOrDefault(FileType.forFileName(fileName).get(), true);
  }

  public boolean put(FileType fileType, boolean value) {
    return this.state.put(fileType, value);
  }

}
