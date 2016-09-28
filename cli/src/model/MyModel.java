package model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGeneratorBase;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomNeighborChooser;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

/**
 * <h1> The MyModel Class</h1>
 * Creates an instance of the Model interface <br>
 * Includes a controller, Maze3d map, Solution map, Thread list and Maze tasks list
 * @author ofir and rom
 *
 */
public class MyModel extends Observable implements Model {

	private Map<String, Maze3d> mazes = new ConcurrentHashMap<String, Maze3d>();
	private Map<Maze3d, Solution<Position>> solutions = new ConcurrentHashMap<Maze3d, Solution<Position>>();
	private ExecutorService executor;

	private String generateMazeAlgorithm;
	private String solveMazeAlgorithm;
	private String saveMethod;
	private String ip;
	private int port;

	
	/**
	 * MyModel constructor, creates a model that interacts with the maze classes
	 * @param controller
	 * @param cli
	 */
	public MyModel(int numOfThreads, String generateMazeAlgorithm, String solveMazeAlgorithm, String saveMethod, String ip,int port) {
		super();
		this.generateMazeAlgorithm = generateMazeAlgorithm;
		this.solveMazeAlgorithm = solveMazeAlgorithm;
		this.saveMethod = saveMethod;
		this.ip = ip;
		this.port = port;
		executor = Executors.newFixedThreadPool(numOfThreads);
	}
	
	/**
	 * This method generates a 3d Maze with user input size
	 * @param name
	 * @param floors
	 * @param rows
	 * @param cols
	 */
	@Override
	public void generate_3d_maze(String name, int floors, int rows, int cols) {
		executor.submit(new Callable<Maze3d>() {

			@Override
			public Maze3d call() throws Exception {
				Maze3dGeneratorBase generator=null;
				switch (generateMazeAlgorithm) {
				case "GrowingTreeGenerator":
					generator = new GrowingTreeGenerator(new RandomNeighborChooser());
					break;

				case "SimpleMaze3dGenerator":
					generator = new SimpleMaze3dGenerator();
					break;
				}
				Maze3d maze = generator.generate(floors, rows, cols);
				mazes.put(name, maze);
				
				setChanged();
				notifyMazeIsReady(name);
				return maze;
			}	
		});
	}

	/**
	 * This method returns the selected maze from the Maze map
	 * @param name
	 */
	@Override
	public Maze3d display(String name) {
		return mazes.get(name);
	}

	@Override
	public int[][] display_cross_section_byX(int index, String name) {
		int[][] maze2d =  mazes.get(name).getCrossSectionByX(index);
		return maze2d;
	}

	@Override
	public int[][] display_cross_section_byZ(int index, String name) {
		int[][] maze2d =  mazes.get(name).getCrossSectionByZ(index);
		return maze2d;
	}

	@Override
	public int[][] display_cross_section_byY(int index, String name) {
		int[][] maze2d =  mazes.get(name).getCrossSectionByY(index);
		return maze2d;
	}

	@Override
	public int getFloors(String name) {
		return mazes.get(name).getFloors();
	}

	@Override
	public int getRows(String name) {
		return mazes.get(name).getRows();
	}

	@Override
	public int getCols(String name) {
		return mazes.get(name).getCols();
	}
	
	/**
	 * checks if the maze exists in the mazes hash map.
	 * @param name the mazes name
	 */
	@Override
	public boolean isMazeExists(String name) {
		if ( mazes.get(name) != null )
			return true;
		return false;
	}

	/**
	 * This method saves the selected maze into a file in the selected path
	 * @param name
	 * @param path
	 */
	@Override
	public void saveMaze(String name, String path) {
		
		OutputStream out=null;
		try {
			out = new MyCompressorOutputStream(new FileOutputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			out.write(mazes.get(name).toByteArray());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method loads a maze from the selected file and pushes it into the Maze map with the selected name
	 * @param path
	 * @param name
	 */
	@Override
	public void loadMaze(String path, String name) {
		try {
			//first, get the maze size
			File file = new File(path);
			FileInputStream in = new FileInputStream(file);
			byte floors=0,rows=0,cols = 0,rep = 0;
			
			//Checking for byte repetitions in the selected file
			rep = (byte) in.read();
			if (rep==1)
			{
				floors = (byte) in.read();
				rep = (byte) in.read();
					if(rep == 1){
						rows= (byte) in.read();
						in.read();
						cols = (byte) in.read();
					}
					else{
						rows= (byte) in.read();
						
						cols = rows;
					}
			}
			else if(rep == 2)
			{
				floors = (byte) in.read();
				rows = floors;
				in.read();
				cols = (byte) in.read();
			}
			else{
				floors = (byte) in.read();
				rows = floors;
				cols = floors;
			}
			
			in.close();
			
			//maze size + floors, rows, cols + startPos, GoalPos
			byte[]arr = new byte[floors*rows*cols + 9];
			
			InputStream  mdi = new MyDecompressorInputStream(new FileInputStream(path));
			
			mdi.read(arr);
			mdi.close();
					
			Maze3d maze = new Maze3d(arr);
			mazes.put(name, maze);
			 			
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
	}
	
	/**
	 * This method solves the selected maze with the selected solution method and pushes it into the Solution map
	 * @param name
	 * @param alg
	 */
	@Override
	public void solveMaze(String name) {
		executor.submit(new Callable<Solution<Position>>() {

			@Override
			public Solution<Position> call() throws Exception {
				
				Solution<Position> solution=null;
				
				Problem problem = new Problem();
				problem.setAlgType(solveMazeAlgorithm);
				problem.setMaze(mazes.get(name));
				
				NetworkHandler handler = new NetworkHandler(ip,port);		
				
				try {
					solution = handler.sendMaze(problem);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				solutions.remove(mazes.get(name));
				solutions.put(mazes.get(name), solution);
				setChanged();
				notifyObservers("solution_ready " + name);
				
				return solution;
			}	
		});
	}

	/**
	 * This method displays the solution for the selected maze from the Solution map
	 * @param name
	 */
	@Override
	public void displaySolution(String name) {
		if (solutions.get(mazes.get(name)) != null) {
			setChanged();
			notifyObservers("display_solution " + solutions.get(mazes.get(name)).toString());
		}
		else
			displayMessage("no solution found");
	}
	
	public Solution<Position> getSolution(String name) {
		return solutions.get(mazes.get(name));
	}
	
	private void displayMessage(String msg) {
		setChanged();
		notifyObservers("display_message " + msg);
	}
	
	private void notifyMazeIsReady(String name) {
		 setChanged();
		 notifyObservers("maze_ready " + name);
	}
	
	/**
	 * This method closes all running threads in the model and terminates it.
	 */
	@Override
	public void exit() {
		executor.shutdownNow();
	}

	public void setGenerateMazeAlgorithm(String generateMazeAlgorithm) {
		this.generateMazeAlgorithm = generateMazeAlgorithm;
	}

	public void setSolveMazeAlgorithm(String solveMazeAlgorithm) {
		this.solveMazeAlgorithm = solveMazeAlgorithm;
	}
	
	/**
	 * This method saves the current mazes and solutions maps into a file or SQL database, based on property saveMethod.
	 */
	public void saveSolutions(){
		switch(saveMethod){
		case "ZIP":
			ObjectOutputStream oosZip = null;
			try {
			    oosZip = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("solutions.dat")));
				oosZip.writeObject(mazes);
				oosZip.writeObject(solutions);			
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					oosZip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		case "SQL":
			try{
				String user = "root";
				String pass = "";
				String dbName = "solutions";
				ByteArrayOutputStream baosMaze = new ByteArrayOutputStream();
				ByteArrayOutputStream baosSolution = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baosMaze);
				oos.writeObject(mazes);
				byte[] dataMaze = baosMaze.toByteArray();
				
				oos = new ObjectOutputStream(baosSolution);
				oos.writeObject(solutions);
				byte[] dataSolution = baosSolution.toByteArray();
				String url = "jdbc:mysql://localhost:3306/"+dbName;
		        Connection conn = DriverManager.getConnection(url,user,pass); 
		        Statement stmt = conn.createStatement();
		        stmt.executeUpdate("drop table if exists solutions");
		        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Solutions(mazes BLOB, solutions BLOB)");
		        String sql = "INSERT INTO Solutions (mazes,solutions) values (?,?)";
		        PreparedStatement ps = conn.prepareStatement(sql);
		        ps.setObject(1, dataMaze);
		        ps.setObject(2, dataSolution);
		        ps.executeUpdate();
		        conn.close();
				}
			catch (Exception e) { 
	        System.err.println("SQL Error");
	        System.err.println(e.getMessage()); 
	     				}
			break;
				
		}
	}
	
	/**
	 * This method loads and replaces the mazes and solutions maps from a file or SQL, based on property saveMethod.
	 */
	public void loadSolutions(){
		switch(saveMethod){
		case "ZIP":
			File file = new File("solutions.dat");
			if (!file.exists())
				return;
			
			ObjectInputStream oisZip = null;
			
			try {
				oisZip = new ObjectInputStream(new GZIPInputStream(new FileInputStream("solutions.dat")));
				mazes = (Map<String, Maze3d>)oisZip.readObject();
				solutions = (Map<Maze3d, Solution<Position>>)oisZip.readObject();		
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally{
				try {
					oisZip.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			break;
		case "SQL":
			try{ 
				String user = "root";
				String pass = "";
				String dbName = "solutions";
				String url = "jdbc:mysql://localhost:3306/"+dbName;
		        Connection conn = DriverManager.getConnection(url,user,pass);
		        String sql = "SELECT * FROM Solutions";
		        PreparedStatement ps=conn.prepareStatement(sql);

		        ResultSet rs=ps.executeQuery();

		        if(rs.next())
		        	{
			        	byte[] dataMaze = (byte[]) rs.getObject(1);
			        	byte[] dataSolution = (byte[]) rs.getObject(2);
			        	ByteArrayInputStream baisMaze = new ByteArrayInputStream(dataMaze);
			        	ByteArrayInputStream baisSolution = new ByteArrayInputStream(dataSolution);
			        	ObjectInputStream ois = new ObjectInputStream(baisMaze);
			        	mazes = (Map<String, Maze3d>)ois.readObject();
			        	ois = new ObjectInputStream(baisSolution);
			        	solutions = (Map<Maze3d, Solution<Position>>)ois.readObject();
		        	}
				}
				catch (Exception e){
					System.err.println("SQL Error");
			        System.err.println(e.getMessage()); 
				}
			break;
			}
		}
		
	}
