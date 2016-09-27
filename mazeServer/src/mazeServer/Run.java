package mazeServer;

import model.MyTcpIpServer;
import properties.PropertiesLoader;
import view.ServerWindow;

public class Run {

	
	public static void main(String[] args) {
		
		//Initialize Properties
		int numOfThreads; // integer
		int port;
		String solveMazeAlgorithm;	//BFS or DFS
		String path = "resources/properties.xml";
		PropertiesLoader loader = new PropertiesLoader(path); 
		
		numOfThreads = loader.getProperties().getNumOfThreads();
		port = loader.getProperties().getPort();;
		solveMazeAlgorithm = loader.getProperties().getSolveMazeAlgorithm();
				
				
		MyTcpIpServer server = new MyTcpIpServer(port);
		server.startServer(numOfThreads);
		
		
		ServerWindow viewGUI= new ServerWindow();
		viewGUI.start();
	}
}
