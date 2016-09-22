package properties;

import java.beans.XMLDecoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PropertiesLoader {
	private static PropertiesLoader instance;
	private Properties properties;
	
	public Properties getProperties() {
		return properties;
	}
	
	public PropertiesLoader() 
	{
		try {
			XMLDecoder decoder = new XMLDecoder(new FileInputStream("resources/properties.xml"));
			properties = (Properties)decoder.readObject();
			decoder.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static PropertiesLoader getInstance() {
		if (instance == null) 
			instance = new PropertiesLoader();
		return instance;
	}
	
	
}
