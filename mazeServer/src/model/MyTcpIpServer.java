package model;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import view.ServerWindow;
/**
 * <h1> The MyTcpIpServer Class</h1>
 * This class starts the tcp ip server, and listens on the port for any connections.<br>
 * @author ofir and rom
 *
 */
public class MyTcpIpServer {
	private ServerSocket server;
	private ExecutorService executor;
	private int maxClientsNum;
	private int port;
	private ServerWindow serverWindow;
	
	
	public ServerWindow getServerWindow() {
		return serverWindow;
	}

	public void setServerWindow(ServerWindow serverWindow) {
		this.serverWindow = serverWindow;
	}

	public MyTcpIpServer(int port, int maxClientsNum) {	
		
		try {
			server = new ServerSocket(port);
			this.port = port;
			this.maxClientsNum = maxClientsNum;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot listen on port " + port);
			System.exit(0);
		}
	}
	
	/**
	 * starts the server with a limit to the number of connections
	 */
	public void startServer() {
		executor = Executors.newFixedThreadPool(maxClientsNum);
		serverWindow.addLog("Server started on port: " + port);
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// keep the server running forever
				while (true) {
					try {
						Socket socket = server.accept();
						serverWindow.addLog("Client connected");
						//notify the gui there is another connection for the counter
						serverWindow.addConnection();
						
						//create a new client handler for the connection
						ClientHandler handler = new ClientHandler(socket, serverWindow);
						executor.submit(new Runnable() {

							@Override
							public void run() {
								handler.start();								
							}							
						});
						
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}			
		});
		thread.start();
		
	}
	/**
	 * closes all of the handles and quits the program.
	 */
	public void closerServer() {
		serverWindow.addLog("Server closed.");
		executor.shutdown();
		System.exit(0);
	}
}
