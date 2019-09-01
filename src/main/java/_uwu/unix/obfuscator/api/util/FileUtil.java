package _uwu.unix.obfuscator.api.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.File;

/**
 * @author Unix on 01.09.2019.
 */
public final class FileUtil {

    @Contract(pure = true)
    private FileUtil() {
    }

    @NotNull
    public static String renameExistingFile(@NotNull File existing) {
        int i = 0;

        while (true) {
            ++i;

            final String newName = existing.getAbsolutePath().replace(".jar", "") + i + ".jar";
            final File backUpName = new File(newName);

            if (!backUpName.exists()) {
                existing.renameTo(backUpName);
                existing.delete();

                return newName;
            }
        }
    }
}