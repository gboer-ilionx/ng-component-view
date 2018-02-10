package editor.model;

import javax.swing.*;

public class NgComponentEditor {

    public JComponent view;
    public String name;

    public NgComponentEditor(JComponent c, String name) {
        this.view = c;
        this.name = name;
    }

}
