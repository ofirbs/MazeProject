package controller;

import java.io.File;
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
		commands.put("dir", new DirCommand());
		
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
	
	public class DirCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			File path = new File(args[0]);
			if(path.exists()!= true)
				view.notifyDirNotFound();
			else{
				File[] listOfFiles = path.listFiles();
				view.printListOfFiles(listOfFiles); //send File array to the View
			}
		}
	}
	
	public class DisplayCrossSectionCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String index = args[0];
			String section = args[1];
			String name = args[2];
			
			int[][] maze2d;
			switch (section) {
			case "X" : maze2d = model.display_cross_section_byX(Integer.parseInt(index), name);
					   break;
					   
			case "Y" : maze2d = model.display_cross_section_byY(Integer.parseInt(index), name);
					   break;
					   
			case "Z" : maze2d = model.display_cross_section_byZ(Integer.parseInt(index), name);
					   break;
					   
			default : view.notify("no such cross section");
					   break;
			}
			view.displayMaze2e(maze2d);
		}
	}
}
