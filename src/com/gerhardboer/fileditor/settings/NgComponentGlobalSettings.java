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

import com.gerhardboer.fileditor.FileType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

@State(
    name = "NgComponentGlobalSettings",
    storages = {
        @Storage("ng-component-view.xml")
    }
)
public final class NgComponentGlobalSettings implements ApplicationComponent, PersistentStateComponent<NgComponentGlobalSettings> {

  private boolean templateView = true;
  private boolean componentView = true;
  private boolean stylesView = true;
  private boolean specView = true;


  public static NgComponentGlobalSettings getInstance() {
    return ApplicationManager.getApplication().getComponent(NgComponentGlobalSettings.class);
  }

  public boolean getByType(FileType type) {
    switch (type) {
      case TEMPLATE:
        return this.templateView;
      case COMPONENT:
        return this.componentView;
      case STYLE:
        return this.stylesView;
      case SPEC:
        return this.specView;
      default:
        return true;
    }
  }

  @Override
  public NgComponentGlobalSettings getState() {
    return this;
  }

  @Override
  public void loadState(NgComponentGlobalSettings state) {
    XmlSerializerUtil.copyBean(state, this);
  }

  @Override
  @NotNull
  @NonNls
  public String getComponentName() {
    return "NgComponentGlobalSettings";
  }


  public boolean isTemplateViewSelected() {
    return templateView;
  }

  public void setTemplateView(boolean templateView) {
    this.templateView = templateView;
  }

  public boolean isComponentViewSelected() {
    return componentView;
  }

  public void setComponentView(boolean componentView) {
    this.componentView = componentView;
  }

  public boolean isStylesViewSelected() {
    return stylesView;
  }

  public void setStylesView(boolean stylesView) {
    this.stylesView = stylesView;
  }

  public boolean isSpecViewSelected() {
    return specView;
  }

  public void setSpecView(boolean specView) {
    this.specView = specView;
  }
}
