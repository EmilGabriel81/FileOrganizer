package files;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileScanner {

    public static List<File> scanDownloads() {

        String userHome = System.getProperty("user.home");
        File downloads = new File(userHome + "/Downloads");

        List<File> result = new ArrayList<>();

        if (downloads.exists() && downloads.isDirectory()) {
            File[] files = downloads.listFiles();

            if (files != null) {
                for (File f : files) {
                    if (f.isFile()) {
                        result.add(f);
                    }
                }
            }
        }

        return result;
    }
}
