package pl.alpheratzteam.obfuscator.util;

import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * @author Unix
 * @since 21.03.2020
 */

public final class FileUtil
{
    private FileUtil() {
    }

    @NotNull
    public static String renameExistingFile(@NotNull File existingFile) {
        int i = 0;

        while (true) {
            ++i;

            final String newName = existingFile.getAbsolutePath().replace(".jar", "") + i + ".jar";
            final File backUpName = new File(newName);
            if (!backUpName.exists()) {
                existingFile.renameTo(backUpName);
                existingFile.delete();

                return newName;
            }
        }
    }
}