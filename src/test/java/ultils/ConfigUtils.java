package ultils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigUtils {
//    Save config data such as URL, username, password, timeouts, etc. from file config.properties
    private static final Properties CONFIG = loadConfig();

    private static Properties loadConfig() {
        Properties p = new Properties();
        try {
            InputStream in = ConfigUtils.class.getResourceAsStream("/config.properties");
            if (in != null) {
                p.load(in);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
        return p;
    }

    public static String getLoginUrl(){
        return CONFIG.getProperty("app.login.url");
    }

    public static String getUsername() {
        return CONFIG.getProperty("app.login.username");
    }

    public static String getPassword() {
        return CONFIG.getProperty("app.login.password");
    }
}
