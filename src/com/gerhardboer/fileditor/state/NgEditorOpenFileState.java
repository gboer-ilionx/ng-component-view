package com.gerhardboer.fileditor.state;

import com.gerhardboer.fileditor.settings.NgComponentGlobalSettings;

public class NgEditorOpenFileState {
  private boolean ts;
  private boolean html;
  private boolean css;

  NgEditorOpenFileState() {
    NgComponentGlobalSettings globalSettings = NgComponentGlobalSettings.getInstance();

    this.html = globalSettings.isTemplateViewSelected();
    this.ts = globalSettings.isComponentViewSelected();
    this.css = globalSettings.isStylesViewSelected();
  }

  public void set(final String name, boolean newState) {
    if (name.endsWith(".ts"))
      this.ts = newState;

    if (name.endsWith(".html"))
      this.html = newState;

    if (name.endsWith(".css"))
      this.css = newState;
  }

  public boolean get(final String name) {
    if (name.endsWith(".ts")) {
      return this.ts;
    }

    if (name.endsWith(".html")) {
      return this.html;
    }

    if (name.endsWith(".css")) {
      return this.css;
    }

    return true;
  }


}