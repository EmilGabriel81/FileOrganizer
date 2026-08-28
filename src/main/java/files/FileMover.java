package files;

import config.ConfigLoader;

import java.io.File;

public class FileMover {

    public static boolean moveFile(File file, String category) {
        try {
            File downloads = FileScanner.getDownloadsFolder();
            File targetDir = new File(downloads, category);

            if (!targetDir.exists()) {
                targetDir.mkdirs();
            }

            File targetFile = new File(targetDir, file.getName());

            boolean ok = file.renameTo(targetFile);

            if (ok) {
                UndoHistory.add(file, targetFile);
            }

            return ok;

        } catch (Exception ex) {
            return false;
        }
    }

}
