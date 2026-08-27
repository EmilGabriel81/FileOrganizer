package files;

import config.ConfigLoader;
import config.ConfigModel;

import java.io.File;
import java.util.Map;

public class FileOrganizer {

    public static String getCategoryForFile(File file) {

        String name = file.getName();
        int dotIndex = name.lastIndexOf('.');

        if (dotIndex == -1) {
            return "unknown";
        }

        String ext = name.substring(dotIndex + 1).toLowerCase();

        ConfigModel config = ConfigLoader.loadConfig();

        for (Map.Entry<String, java.util.List<String>> entry : config.getRules().entrySet()) {
            if (entry.getValue().contains(ext)) {
                return entry.getKey();
            }
        }

        return "other";
    }
}
