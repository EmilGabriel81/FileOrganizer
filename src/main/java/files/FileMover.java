package files;

import config.ConfigLoader;

import java.io.File;

public class FileMover {

    public static boolean moveFile(File file, String category) {
        try {
            File cleanupFolder = new File(ConfigLoader.loadConfig().getCleanupFolder());
            File targetDir = new File(cleanupFolder, category);

            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }

            File targetFile = new File(targetDir, file.getName());

            boolean ok = file.renameTo(targetFile);

            if (ok) {
                UndoHistory.add(file, targetFile);   // ← ESENȚIAL
            }

            return ok;

        } catch (Exception ex) {
            return false;
        }
    }

}
