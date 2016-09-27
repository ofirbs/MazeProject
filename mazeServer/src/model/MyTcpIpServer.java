package model;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import view.ClientHandler;

public class MyTcpIpServer {
	private ServerSocket server;
	private ExecutorService executor;
	private int port;
	
	public MyTcpIpServer(int port) {	
		
		try {
			server = new ServerSocket(port);
			this.port = port;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Cannot listen on port " + port);
		}
	}
	
	public void startServer(int maxClientsNum) {
		executor = Executors.newFixedThreadPool(maxClientsNum);
		System.out.println("Server started on port: " + port);
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
					try {
						Socket socket = server.accept();
						System.out.println("Client connected");
						
						ClientHandler handler = new ClientHandler(socket);
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
}
