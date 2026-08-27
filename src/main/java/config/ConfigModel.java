package config;
import java.util.List;
import java.util.Map;

public class ConfigModel {

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
}
