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

import com.intellij.openapi.ui.VerticalFlowLayout;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class NgComponentGlobalOptionsPanel {
  private JPanel rootPane;

  private JCheckBox templateView = new JCheckBox();
  private JCheckBox componentView = new JCheckBox();
  private JCheckBox stylesView = new JCheckBox();
  private JCheckBox specView = new JCheckBox();


  public JComponent getComponent() {
    if (rootPane == null) {
      rootPane = new JPanel(new BorderLayout());
      rootPane.add(createTopPanel(), BorderLayout.NORTH);
    }

    return rootPane;
  }

  private JPanel createTopPanel() {

    JPanel tickOptions = new JPanel(new VerticalFlowLayout());
    tickOptions.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
    tickOptions.add(createCheckbox(this.templateView, "Template view"));
    tickOptions.add(createCheckbox(this.componentView, "Component view"));
    tickOptions.add(createCheckbox(this.stylesView, "Styles view"));
    tickOptions.add(createCheckbox(this.specView, "Spec view"));

    return tickOptions;
  }

  private JCheckBox createCheckbox(JCheckBox box, String name) {
    box.setText(name);
    box.setFocusable(false);
    return box;
  }

  public boolean isModified(NgComponentGlobalSettings model) {
    getComponent();
    return model.isTemplateViewSelected() != templateView.isSelected() ||
        model.isComponentViewSelected() != componentView.isSelected() ||
        model.isStylesViewSelected() != stylesView.isSelected() ||
        model.isSpecViewSelected() != specView.isSelected();
  }

  public void load(NgComponentGlobalSettings model) {
    getComponent();
    templateView.setSelected(model.isTemplateViewSelected());
    componentView.setSelected(model.isComponentViewSelected());
    stylesView.setSelected(model.isStylesViewSelected());
    specView.setSelected(model.isSpecViewSelected());

  }

  public void save(NgComponentGlobalSettings model) {
    getComponent();
    model.setTemplateView(templateView.isSelected());
    model.setComponentView(componentView.isSelected());
    model.setStylesView(stylesView.isSelected());
    model.setSpecView(specView.isSelected());
  }
}

