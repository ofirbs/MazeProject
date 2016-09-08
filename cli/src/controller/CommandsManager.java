package controller;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;

public class CommandsManager {

	private Model model;
	private View view;
	HashMap<String, Command> commands = new HashMap<String, Command>();
		
	public CommandsManager(Model model, View view) {
		this.model = model;
		this.view = view;	
		commands = getCommandsMap();
	}
	
	public HashMap<String, Command> getCommandsMap() {
		commands.put("generate_3d_maze", new GenerateMazeCommand());
		commands.put("display", new DisplayMazeCommand());
		
		return commands;
	}
	
	public class GenerateMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			int floors = Integer.parseInt(args[1]);
			int rows = Integer.parseInt(args[2]);
			int cols = Integer.parseInt(args[3]);
			
			model.generate_3d_maze(name, floors, rows, cols);
		}		
	}
	
	public class DisplayMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			Maze3d maze = model.display(name);
			view.displayMaze(maze);
		}
		
	}
}
