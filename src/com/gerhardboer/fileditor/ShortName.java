package com.gerhardboer.fileditor;

import com.intellij.openapi.vfs.VirtualFile;

public class ShortName {

    private ShortName() {}

    public static String shortName(String fileName) {
        int index = fileName.lastIndexOf(".");
        String shortName = index > -1 ? fileName.substring(0, index) : fileName;
        if (shortName.endsWith(".spec")) {
            shortName = shortName.substring(0, shortName.length() - 5);
        }
        return shortName;
    }

    public static String shortName(VirtualFile virtualFile) {
        return shortName(virtualFile.getName());
    }
}
