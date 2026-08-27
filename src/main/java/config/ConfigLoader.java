package config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.InputStream;

public class ConfigLoader {

    private static ConfigModel config;

    public static ConfigModel loadConfig() {
        if (config != null) {
            return config;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream is = ConfigLoader.class.getClassLoader().getResourceAsStream("config.json");

            if (is == null) {
                throw new RuntimeException("config.json not found in resources!");
            }

            config = mapper.readValue(is, ConfigModel.class);
            return config;

        } catch (Exception ex) {
            throw new RuntimeException("Failed to load config.json: " + ex.getMessage(), ex);
        }
    }

    public static void saveConfig(ConfigModel config) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writerWithDefaultPrettyPrinter()
                    .writeValue(new File("src/main/resources/config.json"), config);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to save config.json: " + ex.getMessage(), ex);
        }
    }

}
