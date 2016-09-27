package presenter;

import java.io.File;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import model.Model;
import view.View;

/**
 * <h1> The CommandsManager Class</h1>
 * Uses the Command interface and manages all user commands for the maze 
 * @author ofir and rom
 *
 */
public class CommandsManager {

	private Model model;
	private View view;
	HashMap<String, Command> commands = new HashMap<String, Command>();
		
	/**
	 * CommandsManager constructor, creates a commandsManager with a command map of the currently available commands<br>
	 * Requires instances of model and view
	 * @param model
	 * @param view
	 */
	public CommandsManager(Model model, View view) {
		this.model = model;
		this.view = view;	
		commands = getCommandsMap();
	}
	
	/**
	 * This method returns a Hash Map of all available commands
	 */
	public HashMap<String, Command> getCommandsMap() {
		commands.put("generate_3d_maze", new GenerateMazeCommand());
		commands.put("display", new DisplayMazeCommand());
		commands.put("dir", new DirCommand());
		commands.put("display_cross_section", new DisplayCrossSectionCommand());
		commands.put("save_maze", new SaveMazeCommand());
		commands.put("load_maze", new LoadMazeCommand());
		commands.put("solve", new SolveCommand());
		commands.put("display_solution", new DisplaySolutionCommand());
		commands.put("exit", new ExitCommand());
		commands.put("display_message", new DisplayMessageCommand());
		commands.put("maze_ready", new MazeReadyCommand());
		commands.put("solution_ready", new SolutionReadyCommand());
		commands.put("new_properties", new NewPropertiesCommand());
		commands.put("hint", new NewHintCommand());
		commands.put("save_solutions", new NewSaveSolutionCommand());
		commands.put("load_solutions", new NewLoadSolutionCommand());

		
		return commands;
	}
	
	/**
	 * Implements command interface, the hash map command that generates the maze
	 */
	public class GenerateMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 4) {
				view.notify("Please enter 4 parameters: <name> <floors> <rows> <columns>");
				return;
			}
			String name = args[0];
			int floors = Integer.parseInt(args[1]);
			int rows = Integer.parseInt(args[2]);
			int cols = Integer.parseInt(args[3]);
			
			model.generate_3d_maze(name, floors, rows, cols);
		}		
	}
	
	/**
	 * Implements command interface, the hash map command that displays the maze
	 */
	public class DisplayMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 1) {
				view.notify("Please enter 1 parameter: <name>");
				return;
			}
			String name = args[0];
			if ( ! (model.isMazeExists(name)) ) {
				view.notify("Maze " + name + " not found");
				return;
			}
				
			Maze3d maze = model.display(name);
			view.displayMaze(maze);
		}
	}
	
	/**
	 * Implements command interface, the hash map command that displays a list of files in a folder
	 */
	public class DirCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 1) {
				view.notify("Please enter 1 parameter: <directory path>");
				return;
			}
			File path = new File(args[0]);
			if(path.exists()!= true)
				view.notify("Direcotry not found.");
			else{
				File[] listOfFiles = path.listFiles();
				view.printListOfFiles(listOfFiles); //send File array to the View
			}
		}
	}
	
	/**
	 * Implements command interface, the hash map command that displays a cross section based on user input
	 */
	public class DisplayCrossSectionCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 3) {
				view.notify("Please enter 3 parameters: <index> <x/y/z> <name>");
				return;
			}
			String index = args[0];
			String section = args[1];
			String name = args[2];
			
			if ( ! (model.isMazeExists(name)) ) {
				view.notify("Maze " + name + " not found");
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
	
	/**
	 * Implements command interface, the hash map command that saves a maze to a file
	 */
	public class SaveMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 2) {
				view.notify("Please enter 2 parameter: <name> <path>");
				return;
			}
			String name = args[0];
			
			if ( ! (model.isMazeExists(name)) ) {
				view.notify("Maze " + name + " not found");
				return;
			}
			String path = args[1];
			model.saveMaze(name, path);
			view.notify("maze " +name+" saved to " + path);
			
		}
	}
	
	/**
	 * Implements command interface, the hash map command that loads a maze from a file
	 */
	public class LoadMazeCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 2) {
				view.notify("Please enter 2 parameters: <path> <name>");
				return;
			}
			String path = args[0];
			File file = new File(args[0]);
			if(file.exists()!= true) {
				view.notify("file not found.");
				return;
			}
			
			String name = args[1];
			if ( model.isMazeExists(name) ) {
				view.notify("Maze " + name + " already exists");
				return;
			}
			model.loadMaze(path, name);
		}	
	}
	
	/**
	 * Implements command interface, the hash map command that solves the selected maze
	 */
	public class SolveCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 1) {
				view.notify("Please enter 1 parameter: <name>");
				return;
			}
			String name = args[0];
			if ( ! (model.isMazeExists(name)) ) {
				view.notify("Maze " + name + " not found");
				return;
			}
			
			model.solveMaze(name);
		}
	}
	
	/**
	 * Implements command interface, the hash map command that displays a solution for the selected maze
	 */
	public class DisplaySolutionCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			if (args == null || args.length != 1) {
				view.notify("Please enter 1 parameter: <name>");
				return;
			}
			String name = args[0];
			if ( ! (model.isMazeExists(name)) ) {
				view.notify("Maze " + name + " not found");
				return;
			}
			view.displaySolution(model.getSolution(name));
		}
	}
	
	/**
	 * Implements command interface, the hash map command that build a string and sends it to the view
	 */
	public class DisplayMessageCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			StringBuilder builder = new StringBuilder();
			for(String s : args) {
			    builder.append(s + " ");
			}
			view.notify(builder.toString());
		}
	}
	
	/**
	 * Implements command interface, the hash map command that closes the CLI
	 */
	public class ExitCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			model.exit();
		}
	}
	
	/**
	 * Implements command interface, the hash map command that notifies the view that the maze was built successfully
	 */
	public class MazeReadyCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			view.notifyMazeIsReady(args[0]);
		}
	}
	
	/**
	 * Implements command interface, the hash map command that notifies the view that the maze was solved successfully
	 */
	public class SolutionReadyCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			view.notifySolutionIsReady(args[0]);
		}
	}
	
	/**
	 * Implements command interface, the hash map command that configures the model properties
	 */
	public class NewPropertiesCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			model.setGenerateMazeAlgorithm(args[0]);
			model.setSolveMazeAlgorithm(args[1]);
		}
	}
	
	/**
	 * Implements command interface, the hash map command that gets the solution for the maze from the model, 
	 * and sends it to the view to show the current hint
	 */
	public class NewHintCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			view.hint(model.getSolution(args[0]));
		}
	}
	
	/**
	 * Implements command interface, the hash map command that tells the model to saves all of the solutions
	 */
	public class NewSaveSolutionCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			model.saveSolutions();
		}
	}
	
	/**
	 * Implements command interface, the hash map command that tells the model to load all of the solutions
	 */
	public class NewLoadSolutionCommand implements Command {

		@Override
		public void doCommand(String[] args) {
			model.loadSolutions();
		}
	}
}
