package com.gerhardboer.fileditor;

import com.intellij.openapi.vfs.VirtualFile;

public class ShortName {

    private ShortName() {}

    public static String shortName(String fileName) {
        int index = fileName.indexOf(".");
        return index > -1 ? fileName.substring(0, index) : fileName;
    }

    public static String shortName(VirtualFile virtualFile) {
        return shortName(virtualFile.getName());
    }
}
