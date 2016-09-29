package properties;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
/**
 * <h1> The PropertiesLoader Class</h1>
 * This class reads the data from the XML properties file.<br>
 * and sets the information in the properties object. 
 * @author ofir and rom
 *
 */
public class PropertiesLoader {
	private static PropertiesLoader instance;
	private Properties properties;

	public Properties getProperties() {
		return properties;
	}
	
	public PropertiesLoader(String path) 
	 {
	  try {
	   XMLDecoder decoder = new XMLDecoder(new FileInputStream(path));
	   properties = (Properties)decoder.readObject();
	   decoder.close();
	  } catch (FileNotFoundException e) {
	   e.printStackTrace();
	  }
	 }
	
	public PropertiesLoader() 
	{
		XMLDecoder decoder = new XMLDecoder(getClass().getClassLoader().getResourceAsStream("properties.xml"));
		properties = (Properties)decoder.readObject();
		decoder.close();
	}
	
	public static PropertiesLoader getInstance(String path) {
		if (instance == null) 
			instance = new PropertiesLoader(path);
		return instance;
	}
	
	
}
