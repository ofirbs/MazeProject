package view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

import algorithms.demo.MazeDomain;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.BFS;
import algorithms.search.CommonSearcher;
import algorithms.search.DFS;
import algorithms.search.Solution;
import model.Problem;

public class ClientHandler {
	private Socket socket;
	
	public ClientHandler(Socket socket) {
		this.socket = socket;
	}
	
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
	
	public void start() {
		try {
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			Problem problem = (Problem)ois.readObject();
			System.out.println("Problem received");
			Solution<Position> solution = solveMaze(problem.getMaze(),problem.getAlgType());
			
			PrintWriter writer = new PrintWriter(socket.getOutputStream());
			writer.println(solution);
			writer.close();
			socket.close();
			
			System.out.println("Result sent: " + solution);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
