package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import algorithms.demo.MazeDomain;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.BFS;
import algorithms.search.CommonSearcher;
import algorithms.search.DFS;
import algorithms.search.Solution;
import view.ServerWindow;
/**
 * <h1> The ClientHandler Class</h1>
 * This class is responsible for the sessions with the clients.<br>
 * It receives a problems and send back a solution.
 * @author ofir and rom
 *
 */
public class ClientHandler {
	private Socket socket;
	private ServerWindow serverWindow;
	
	public ClientHandler(Socket socket, ServerWindow serverWindow) {
		this.socket = socket;
		this.serverWindow = serverWindow;
	}
	
	/**
	 * solves the received maze with the given algorithm type, and returns the solution
	 * @param maze
	 * @param algType
	 * @return Solution<Position> the solution for the problem
	 */
	private Solution<Position> solveMaze(Maze3d maze, String algType) {
		
		CommonSearcher<Position> solver;
		Solution<Position> solution=null;
		switch (algType) {
		case "DFS" :
			solver = new BFS<Position>();
			solution = solver.search( new MazeDomain(maze));
			break;
					
		case "BFS" : 
			solver = new DFS<Position>(); 
			solution = solver.search( new MazeDomain(maze));
			break;
		}
		return solution;
	}
	
	/**
	 * starts a session with the client. opens socket that receives a problem, and send the solution back
	 */
	public void start() {
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			Problem problem = (Problem)ois.readObject();
			System.out.println("Problem received");
			serverWindow.addLog("Problem received");
			Solution<Position> solution = solveMaze(problem.getMaze(),problem.getAlgType());
			
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(solution);
			oos.close();
			
			System.out.println("Result sent: " + solution);
			serverWindow.addLog("Result sent: " + solution);
			
			socket.close();
			serverWindow.addLog("Client disconnected");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
