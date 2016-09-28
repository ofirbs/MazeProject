package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class NetworkHandler {
	private String ip;
	private int port;
	
	public NetworkHandler(String ip,int port){
		this.ip=ip;
		this.port=port;
	}
	
	public Solution<Position> sendMaze(Problem problem) throws Exception {
		
		try {
			Socket socket = new Socket(ip, port);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(problem);
			
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			Solution<Position> result =  (Solution<Position>) ois.readObject();
			ois.close();
			socket.close();
			return result;			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		throw new Exception("Something went wrong");
	}
}
