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
		commands.put("display_cross_section", new DisplayCrossSectionCommand());
		commands.put("save_maze", new SaveMazeCommand());
		commands.put("load_maze", new LoadMazeCommand());
		commands.put("solve", new SolveCommand());
		commands.put("display_solution", new DisplaySolutionCommand());
		
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
			if ( ! (model.isMazeExists(name)) ) {
				view.notify("Maze " + name + " not found");
				return;
			}
				
			Maze3d maze = model.display(name);
			view.displayMaze(maze);
		}
		
	}
	
	public class DirCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			File path = new File(args[0]);
			if(path.exists()!= true)
				view.notify("Direcotry not found.");
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
			
			if ( ! (model.isMazeExists(name)) ) {
				view.notify("Maze" + name + " not found");
				return;
			}
			
			int[][] maze2d=null;
			switch (section) {
			case "X" : 	if ( Integer.parseInt(index) > model.getFloors(name) || Integer.parseInt(index) < 0 ) {
							view.notify("Out of bounds.");
							return;
						}
							
						else
							maze2d = model.display_cross_section_byX(Integer.parseInt(index), name);
						break;
					   
			case "Y" : 	if ( Integer.parseInt(index) > model.getRows(name) || Integer.parseInt(index) < 0 ) {
							view.notify("Out of bounds.");
							return;
						}
						else
							maze2d = model.display_cross_section_byY(Integer.parseInt(index), name);
					   	break;
					   
			case "Z" : 	if ( Integer.parseInt(index) > model.getCols(name) || Integer.parseInt(index) < 0 ) {
							view.notify("Out of bounds.");
							return;
						}
						else
							maze2d = model.display_cross_section_byZ(Integer.parseInt(index), name);
					   	break;
					   
			default : 	view.notify("no such cross section" + section + ".");
						return;
			}
			view.displayMaze2d(maze2d);
		}
	}
	
	public class SaveMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			
			if ( ! (model.isMazeExists(name)) ) {
				view.notify("Maze" + name + " not found");
				return;
			}
			String path = args[1];
			model.saveMaze(name, path);
			view.notify("maze" +name+" saved to " + path);
			
		}
	}
	
	public class LoadMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String path = args[0];
			File file = new File(args[0]);
			if(file.exists()!= true) {
				view.notify("file not found.");
				return;
			}
			
			String name = args[1];
			if ( model.isMazeExists(name) ) {
				view.notify("Maze" + name + " already exists");
				return;
			}
			model.loadMaze(path, name);
		}	
	}
	
	public class SolveCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					String name = args[0];
					
					if ( ! (model.isMazeExists(name)) ) {
						view.notify("Maze" + name + " not found");
						return;
					}
					String alg = args[1];
					if(alg != "BFS" || alg != "DFS"){
						view.notify("Algorithm not found");
						return;
					}
					model.solveMaze(name,alg);
				}
				
			});
		}
	}
	
	public class DisplaySolutionCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			String name = args[0];
			if ( ! (model.isMazeExists(name)) ) {
				view.notify("Maze" + name + " not found");
				return;
			}
			model.displaySolution(name);
		}
	}
}
