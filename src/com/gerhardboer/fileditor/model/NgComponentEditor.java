package com.gerhardboer.fileditor.model;

import javax.swing.*;

public class NgComponentEditor {

    public JComponent view;
    public String fileName;
    public boolean active = true;
    public String type;

    public NgComponentEditor(JComponent c, String fileName, boolean active, String type) {
        this.view = c;
        this.fileName = fileName;
        this.active = active;
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

}
