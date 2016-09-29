package boot;

import model.MyTcpIpServer;
import properties.PropertiesLoader;
import view.ServerWindow;

public class Run {

	
	public static void main(String[] args) {
		
		//Initialize Properties
		int numOfThreads; // integer
		int port;
		PropertiesLoader loader = new PropertiesLoader(); 
		
		numOfThreads = loader.getProperties().getNumOfThreads();
		port = loader.getProperties().getPort();;
				
				
		MyTcpIpServer server = new MyTcpIpServer(port, numOfThreads);
		ServerWindow viewGUI= new ServerWindow();
		
		server.setServerWindow(viewGUI);
		viewGUI.setMyTcpIpServer(server);
		
		viewGUI.start();
	}
}
