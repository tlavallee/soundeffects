package ca.lavallee.config;

import java.io.IOException;
import java.util.List;

public interface AppConfig {
	
	public List<String> getStringValues(String key);
	public List<String> getStringValues(String group, String key);
	public void putValues(String key, List<Object> values) throws IOException;
	public void putValues(String group, String key, List<Object> values) throws IOException;
	
	public String getStringValue(String group, String key);
	public String getStringValue(String key);
	public void putValue(String key, Object value) throws IOException;
	public void putValue(String group, String key, Object value) throws IOException;
	
}
