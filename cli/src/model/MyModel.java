package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.RandomNeighborChooser;
import controller.Controller;
import controller.MyController;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

public class MyModel implements Model {
	private Controller controller;
	private Map<String, Maze3d> mazes = new ConcurrentHashMap<String, Maze3d>();
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
				InputStream in = new MyDecompressorInputStream(new FileInputStream(path));
				File file = new File(path);
				byte[] b = new byte[(int)file.length()];
				
				for (int i = 0; i < (int)file.length(); i++) {
					System.out.print(b[i]);
				}
				System.out.println();
				
				//byte b[] = new byte[52];
				
				//RandomAccessFile f = new RandomAccessFile(path, "r");
				//byte[] b = new byte[(int)f.length()];
				//byte[] b = Files.readAllBytes(new File(path).toPath());
				
				in.read(b);
				in.close();
						
				Maze3d maze = new Maze3d(b);
				mazes.put(name, maze);
			 			
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
	}
}
