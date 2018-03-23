package com.gerhardboer.fileditor;

import com.intellij.openapi.vfs.VirtualFile;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

public enum FileType {

    COMPONENT("component", hasExtension(".ts").and(hasExtension(".spec.ts").negate())),
    TEMPLATE("template", hasExtension(".html")),
    STYLE("style", hasExtension(".css", ".scss", ".less")),
    SPEC("spec", hasExtension(".spec.ts"));

    private final String displayName;
    private final Predicate<String> fileNamePredicate;

    FileType(String displayName, Predicate<String> fileNamePredicate) {
        this.displayName = displayName;
        this.fileNamePredicate = fileNamePredicate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean appliesToFileName(String name) {
        return fileNamePredicate.test(name);
    }

    public boolean appliesToVirtualFile(VirtualFile file) {
        return fileNamePredicate.test(file.getName());
    }

    public static Optional<FileType> forFileName(String fileName) {
        return Arrays.stream(FileType.values()).filter(type -> type.appliesToFileName(fileName)).findFirst();
    }

    private static Predicate<String> hasExtension(String... extensions) {
        return (String fileName) -> Arrays.stream(extensions)
                .anyMatch((extension -> fileName.endsWith(extension)));
    }
}
