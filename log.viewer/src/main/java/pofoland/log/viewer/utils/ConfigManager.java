package pofoland.log.viewer.utils;

import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
	
	private static Properties prop = new Properties();
	
	private static int propCount = 0;
	
	public ConfigManager() {
	}
	
	public static void setProperties(String configFile) {
		try {
			if (propCount == 0) {
				prop.load(ConfigManager.class.getResourceAsStream("/"+configFile));
				propCount++;	
			} else {
				LoggerManager.debug(ConfigManager.class, "Property alreay load");
			}
		} catch (IOException e) {
			LoggerManager.debug(ConfigManager.class, "Property file does not exist");
		}
	}
	
	public static String getProperty(String key) {
		String value = "";
		
		if (prop != null) {
			value = prop.getProperty(key);
		} else {
			LoggerManager.debug(ConfigManager.class, "Property file load please");
		}
		
		return (value != null && value != "") ? value.trim() : "";
	}
	
	public static int getIntProperty(String key) {
		int value = -1;
		
		if (prop != null) {
			try {
				value = Integer.parseInt(prop.getProperty(key));
			} catch (Exception e) {
				value = -1;
			}
		} else {
			LoggerManager.debug(ConfigManager.class, "Property file load please");
		}
		
		return value;
	}
}
