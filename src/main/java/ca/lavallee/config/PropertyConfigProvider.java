package ca.lavallee.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertyConfigProvider implements AppConfig {
	private File propertyFile;
	private Properties properties;
	
	public PropertyConfigProvider(InputStream is) throws IOException {
		properties = new Properties();
		properties.load(is);
	}
	public PropertyConfigProvider(String fileName) throws IOException {
		this(null, fileName);
	}
	public PropertyConfigProvider(String folder, String fileName) throws IOException {
		properties = new Properties();
		if (folder == null) {
			propertyFile = new File(fileName);
		} else {
			propertyFile = new File(folder, fileName);
		}
		loadProperties();
	}

	private void loadProperties() throws IOException {
		try (InputStream input = new FileInputStream(propertyFile)){
			properties.load(input);
		} catch (FileNotFoundException ignore) {
		}
	}
	@Override
	public List<String> getStringValues(String key) {
		return getStringValues(null, key);
	}
	
	@Override
	public List<String> getStringValues(String group, String key) {
		List<String> values = new ArrayList<>();
		String propertyName = key;
		if (group != null) {
			propertyName = group + "." + key;
		}
		String value = properties.getProperty(propertyName);
		if (value != null) {
			values.add(value);
		}
		int index = 1;
		do {
			String pn = propertyName + "." + index++;
			value = properties.getProperty(pn);
			if (value != null) {
				values.add(value);
			}
		} while (value != null);
		return values;
	}

	@Override
	public void putValues(String key, List<Object> values) throws IOException {
		if (propertyFile == null) {
			throw new UnsupportedOperationException("Property file is read only");
		}
		putValues(null, key, values);
	}
	@Override
	public void putValues(String group, String key, List<Object> values) throws IOException {
		if (propertyFile == null) {
			throw new UnsupportedOperationException("Property file is read only");
		}
		String propertyName = key;
		if (group != null) {
			propertyName = group + "." + key;
		}
		properties.remove(propertyName);
		int index = 1;
		boolean done = false;
		int cleared = 0;
		while (!done) {
			String pn = propertyName + "." + index;
			if (values == null || index > values.size()) {
				properties.remove(pn);
				cleared++;
			} else {
				properties.setProperty(pn, values.get(index - 1).toString());
			}
			index++;
			if (cleared == 10) {
				break;
			}
		}
		properties.store(new FileOutputStream(propertyFile), "Auto generated properties file");
		loadProperties();
	}
	@Override
	public String getStringValue(String group, String key) {
		String propertyName = key;
		if (group != null) {
			propertyName = group + "." + key;
		}
		return properties.getProperty(propertyName);
	}
	@Override
	public String getStringValue(String key) {
		return getStringValue(null, key);
	}
	@Override
	public void putValue(String key, Object value) throws IOException {
		putValue(null, key, value);
	}
	@Override
	public void putValue(String group, String key, Object value) throws IOException {
		if (propertyFile == null) {
			throw new UnsupportedOperationException("Property file is read only");
		}
		String propertyName = key;
		if (group != null) {
			propertyName = group + "." + key;
		}
		if (value == null) {
			properties.remove(propertyName);
		} else {
			properties.setProperty(propertyName, value.toString());
		}
		properties.store(new FileOutputStream(propertyFile), "Auto generated properties file");
		loadProperties();
	}

}
