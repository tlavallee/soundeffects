package ca.lavallee.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class PropertyConfigProviderTest {
	@Rule
	public TemporaryFolder folder= new TemporaryFolder();

	@Test
	public void testGetStringValues() throws FileNotFoundException, IOException {
		AppConfig config = new PropertyConfigProvider(this.getClass().getResourceAsStream("test.properties"));
		
		List<String> values = config.getStringValues("group", "key");
		assertNotNull(values);
		assertEquals(2, values.size());
		assertEquals("value1", values.get(0));
		assertEquals("value2", values.get(1));
		
		values = config.getStringValues("group", "key2");
		assertNotNull(values);
		assertEquals(2, values.size());
		assertEquals("Value", values.get(0));
		assertEquals("Value1", values.get(1));
		
		values = config.getStringValues("nogroupkey");
		assertNotNull(values);
		assertEquals(2, values.size());
		assertEquals("No group value 1", values.get(0));
		assertEquals("No group value 2", values.get(1));
	}
	
	@Test
	public void testGetStringValue() throws FileNotFoundException, IOException {
		AppConfig config = new PropertyConfigProvider(this.getClass().getResourceAsStream("test.properties"));
		
		String value = config.getStringValue("group", "key3");
		assertNotNull(value);
		assertEquals("Test", value);
		
		value = config.getStringValue("key");
		assertNotNull(value);
		assertEquals("KeyValue", value);	
	}
	
	@Test
	public void testPutValue() throws IOException {
		AppConfig config = new PropertyConfigProvider(this.getClass().getResourceAsStream("test.properties"));
		try {
			config.putValue("putgroup", "key", "This should fail");
			fail("Put to InputStream property files not allowed");
		} catch (UnsupportedOperationException expected) {		
		}
		
		try {
			config.putValue("putkey", "This should fail");
			fail("Put to InputStream property files not allowed");
		} catch (UnsupportedOperationException expected) {		
		}
		
	}

	@Test
	public void testPutValues() throws IOException {
		AppConfig config = new PropertyConfigProvider(this.getClass().getResourceAsStream("test.properties"));
		List<Object> values = new ArrayList<>();
		values.add("Value 1");
		values.add("Value 2");
		try {
			config.putValues("putgroup", "key", values);
			fail("Put to InputStream property files not allowed");
		} catch (UnsupportedOperationException expected) {		
		}
		
		try {
			config.putValue("putkey", values);
			fail("Put to InputStream property files not allowed");
		} catch (UnsupportedOperationException expected) {		
		}
		
	}
	
	@Test
	public void testWriteableConfigFile() throws FileNotFoundException, IOException {
		AppConfig config = new PropertyConfigProvider(folder.getRoot().getPath(), "test.properties");
		config.putValue("key", "test");
		config.putValue("putgroup", "key", "Put Value");
		List<Object> values = new ArrayList<>();
		values.add("Value 1");
		values.add("Value 2");		
		config.putValues("list", values);
		values.clear();
		values.add("Group Value 1");
		values.add("Group Value 2");
		config.putValues("putgroup", "list", values);
		
		String value = config.getStringValue("key");
		assertNotNull(value);
		assertEquals("test", value);
		value = config.getStringValue("putgroup", "key");
		assertNotNull(value);
		assertEquals("Put Value", value);
		List<String> strValues = config.getStringValues("list");
		assertNotNull(strValues);
		assertEquals(2, strValues.size());
		assertEquals("Value 1", strValues.get(0));
		assertEquals("Value 2", strValues.get(1));
		strValues = config.getStringValues("putgroup", "list");
		assertNotNull(strValues);
		assertEquals(2, strValues.size());
		assertEquals("Group Value 1", strValues.get(0));
		assertEquals("Group Value 2", strValues.get(1));
		
	}
	

}
