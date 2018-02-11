package com.gerhardboer.fileditor.model;

import javax.swing.*;

public class NgComponentEditor {

    public JComponent view;
    public String name;
    public boolean active = true;

    public NgComponentEditor(JComponent c, String name) {
        this.view = c;
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

}
