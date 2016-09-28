package boot;

import model.MyTcpIpServer;
import properties.PropertiesLoader;
import view.ServerWindow;

public class Run {

	
	public static void main(String[] args) {
		
		//Initialize Properties
		int numOfThreads; // integer
		int port;
		String path = "resources/properties.xml";
		PropertiesLoader loader = new PropertiesLoader(path); 
		
		numOfThreads = loader.getProperties().getNumOfThreads();
		port = loader.getProperties().getPort();;
				
				
		MyTcpIpServer server = new MyTcpIpServer(port, numOfThreads);
		ServerWindow viewGUI= new ServerWindow();
		
		server.setServerWindow(viewGUI);
		viewGUI.setMyTcpIpServer(server);
		
		viewGUI.start();
	}
}
