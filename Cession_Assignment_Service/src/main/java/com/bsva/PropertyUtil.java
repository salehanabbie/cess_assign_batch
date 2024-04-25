package com.bsva;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import com.bsva.interfaces.PropertyUtilRemote;

@Stateless
@Remote(PropertyUtilRemote.class)
public class PropertyUtil
{

	public String getPropValue(String key) throws IOException 
	{
		String result = "";
		InputStream inputStream = null;
		 
		try {
			Properties prop = new Properties();
			String propFileName = "MandateMessageCommons.properties";
														    
 
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 
			Date time = new Date(System.currentTimeMillis());
 
//			// get the property value and print it out
//			String user = prop.getProperty("user");
			
			HashMap hashMap = new HashMap<>(prop);
			
			for (Object k : hashMap.keySet()) {
				
				if(k.toString().equals(key)){
					
					result = (String) hashMap.get(k); 
					break;
				}
			}
			
			//System.out.println("result: " +result); 
			
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return result;
	}
}
