package ca.lavallee.soundeffects;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import ca.lavallee.config.AppConfig;
import ca.lavallee.config.PropertyConfigProvider;
import ca.lavallee.soundeffects.swing.SoundEffects;

public class SoundEffectsMain {
	private final String CONFIG_DIR = "SoundEffects";
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private String configDir;
	
	public void setConfigDirectory(String configDir) {
		this.configDir = configDir;
	}
	public void startup() {
		checkConfigDir();
		try {
			AppConfig config = new PropertyConfigProvider(configDir, "config.properties");
			config.putValue("version", "1.0");
			SoundEffects app = new SoundEffects(config);
			app.setVisible(true);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Unable to create config file in " + configDir, e);
			return;
		}
	}
	
	private void checkConfigDir() {
		if (configDir == null) {
			File homeDir = new File(System.getProperty("user.home"));
			File appData = new File(homeDir, "AppData/Local");
			File config = null;
			if (appData.exists() && appData.isDirectory()) {				
				config = new File(appData, CONFIG_DIR);
			} else {
				config = new File(homeDir, CONFIG_DIR);
			}
			if (!config.exists()) {
				config.mkdir();
			}
			configDir = config.getPath();
		}
	}

	public static void main(String[] args) {
		SoundEffectsMain main = new SoundEffectsMain();
		main.startup();
	}

}
