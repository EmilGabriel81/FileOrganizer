package files;

import java.io.File;

public class FileMover {

    public static boolean moveFile(File file, String category) {

        String userHome = System.getProperty("user.home");
        File targetDir = new File(userHome + "/Downloads/" + category);

        if (!targetDir.exists()) {
            targetDir.mkdirs();
        }

        File targetFile = new File(targetDir, file.getName());

        return file.renameTo(targetFile);
    }
}
