package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import algorithms.demo.MazeDomain;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomNeighborChooser;
import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.Solution;
import controller.Controller;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;


public class MyModel implements Model {
	private Controller controller;
	private Map<String, Maze3d> mazes = new ConcurrentHashMap<String, Maze3d>();
	private Map<Maze3d, Solution> solutions = new ConcurrentHashMap<Maze3d, Solution>();
	private List<Thread> threads = new ArrayList<Thread>();

	
	public MyModel(Controller controller) {
		super();
		this.controller = controller;
	}

	@Override
	public void generate_3d_maze(String name, int floors, int rows, int cols) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				GrowingTreeGenerator generator = new GrowingTreeGenerator(new RandomNeighborChooser());
				Maze3d maze = generator.generate(floors, rows, cols);
				mazes.put(name, maze);
				
				controller.notifyMazeIsReady(name);				
			}	
		});
		thread.start();
		threads.add(thread);
	}

	@Override
	public Maze3d display(String name) {
		return mazes.get(name);
	}

	public void setController(Controller controller) {
		this.controller = controller;
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

	@Override
	public boolean isMazeExists(String name) {
		if ( mazes.get(name) != null )
			return true;
		return false;
	}

	@Override
	public void saveMaze(String name, String path) {
		
		OutputStream out=null;
		try {
			out = new MyCompressorOutputStream(new FileOutputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			byte[] b = mazes.get(name).toByteArray();
			for (int i = 0; i <b.length; i++) {
				System.out.print(b[i]);
			}
			System.out.println();
			
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

	@Override
	public void loadMaze(String path, String name) {
		try {
			//first, get the maze size
			File file = new File(path);
			FileInputStream in = new FileInputStream(file);
			byte floors=0,rows=0,cols = 0,rep = 0;
			
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

	@Override
	public void solveMaze(String name, String alg) {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				switch (alg) {
				case "DFS" : solutions.put(mazes.get(name), new BFS<Position>().search( new MazeDomain(mazes.get(name))));
							controller.notify("solution for " + name + " is ready");
							break;
							
				case "BFS" : solutions.put(mazes.get(name), new DFS<Position>().search( new MazeDomain(mazes.get(name))));
							controller.notify("solution for " + name + " is ready");
							break;
				}	
			}	
		});
		thread.start();
		threads.add(thread);
	}

	@Override
	public void displaySolution(String name) {
		if (solutions.get(mazes.get(name)) != null)
			controller.notify(solutions.get(mazes.get(name)).toString());
		else
			controller.notify("no solution found");
	}
}
