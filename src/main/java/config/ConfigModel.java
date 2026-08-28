package config;
import java.util.List;
import java.util.Map;

public class ConfigModel {


    private String cleanupFolder = System.getProperty("user.home") + "/Downloads";
    private int ignoreLargerThanMB = 0;
    private int ignoreRecentHours = 0;


    private List<String> customExtensions;
    private Map<String, List<String>> rules;

    public List<String> getCustomExtensions() {
        return customExtensions;
    }

    public void setCustomExtensions(List<String> customExtensions) {
        this.customExtensions = customExtensions;
    }

    public Map<String, List<String>> getRules() {
        return rules;
    }

    public void setRules(Map<String, List<String>> rules) {
        this.rules = rules;
    }

    public String getCleanupFolder() { return cleanupFolder; }
    public void setCleanupFolder(String folder) { this.cleanupFolder = folder; }

    public int getIgnoreLargerThanMB() { return ignoreLargerThanMB; }
    public void setIgnoreLargerThanMB(int mb) { this.ignoreLargerThanMB = mb; }

    public int getIgnoreRecentHours() { return ignoreRecentHours; }
    public void setIgnoreRecentHours(int hours) { this.ignoreRecentHours = hours; }

}
