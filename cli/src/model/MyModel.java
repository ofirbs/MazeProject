package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.RandomNeighborChooser;
import controller.Controller;
import controller.MyController;

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
}
