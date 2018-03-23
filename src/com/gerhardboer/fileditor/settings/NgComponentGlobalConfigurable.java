/*
 * SonarLint for IntelliJ IDEA
 * Copyright (C) 2015 SonarSource
 * sonarlint@sonarsource.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package com.gerhardboer.fileditor.settings;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.ui.components.JBTabbedPane;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class NgComponentGlobalConfigurable implements Configurable, Configurable.NoScroll {
  private final Application app;
  private final NgComponentGlobalSettings globalSettings;
  private JPanel rootPanel;
  private NgComponentGlobalOptionsPanel globalPanel;

  public NgComponentGlobalConfigurable() {
    this.app = ApplicationManager.getApplication();
    this.globalSettings = app.getComponent(NgComponentGlobalSettings.class);
  }

  @Nls @Override public String getDisplayName() {
    return "NgComponentView";
  }

  @Nullable
  @Override public String getHelpTopic() {
    return null;
  }

  @Nullable @Override public JComponent createComponent() {
    return getPanel();
  }

  @Override public boolean isModified() {
    return globalPanel.isModified(globalSettings);
  }

  @Override public void apply() {
    globalPanel.save(globalSettings);
  }


  @Override public void reset() {
    globalPanel.load(globalSettings);
  }

  @Override public void disposeUIResources() {
    if (rootPanel != null) {
      rootPanel.setVisible(false);
      rootPanel = null;
    }
  }

  private JPanel getPanel() {
    if (rootPanel == null) {
      globalPanel = new NgComponentGlobalOptionsPanel();

      JPanel settingsPanel = new JPanel(new BorderLayout());
      settingsPanel.add(globalPanel.getComponent(), BorderLayout.NORTH);

      rootPanel = new JPanel(new BorderLayout());
      JBTabbedPane tabs = new JBTabbedPane();
      tabs.insertTab("Default view settings", null, settingsPanel, "Configure the default open views", 0);
      rootPanel.add(tabs, BorderLayout.CENTER);
    }

    return rootPanel;
  }
}
