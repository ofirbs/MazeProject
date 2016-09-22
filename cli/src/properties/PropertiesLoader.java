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
	
	public static PropertiesLoader getInstance(String path) {
		if (instance == null) 
			instance = new PropertiesLoader(path);
		return instance;
	}
	
	
}
