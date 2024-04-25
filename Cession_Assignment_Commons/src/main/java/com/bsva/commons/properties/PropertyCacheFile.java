package com.bsva.commons.properties;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;


/***
 * 
 * @author DimakatsoN
 *
 */

public class PropertyCacheFile {
	
	private static HashMap<String, String> properties;
	private static PropertyCacheFile instance = null;
	
	
	static {
		if(instance == null) {
			instance = new PropertyCacheFile();
			ResourceBundle rb = ResourceBundle.getBundle("com/bsva/conf/MandateMessageCommons");
			properties = new HashMap<String, String>();
			Enumeration<?> keys = rb.getKeys();
			while(keys.hasMoreElements())
			{
				String key = (String) keys.nextElement();
				properties.put(key,rb.getString(key).trim());
			}
		}
	}
	
	/**
	 * 
	 */
	
	private PropertyCacheFile() {
		
	}
	
	/**
	 *
	 *  @return
	 */
	
	public static PropertyCacheFile getInstance()
	{
		return instance;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	
	public String getProperty(String key)
	{
		String value = (String) properties.get(key);
		return value;
	}
	
	/**
	 * 
	 * @return
	 */
	
	public HashMap<String, String> getProperties()
	{
		return properties;
	}

}
