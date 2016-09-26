package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;

/**
 * <h1> The CLI Class</h1>
 * Creates a CLI to receive user input and display output from the maze
 * @author ofir and rom
 *
 */
public class CLI extends Observable {
	private BufferedReader in;
	private PrintWriter out;
	
	/**
	 * CLI constructor, CLI to receive user input and display output from the maze
	 * @param in
	 * @param out
	 * @param commandsManager
	 */
	public CLI(BufferedReader in, PrintWriter out) {
		super();
		this.in = in;
		this.out = out;
	}

	/**
	 * This method receives a File array with a list of files and prints it
	 * @param listOfFiles
	 */
	 public void getListOfFiles(File[] listOfFiles){
		    for (int i = 0; i < listOfFiles.length; i++)
		    	out.println("File: " + listOfFiles[i].getName());
		 out.flush();
	 }
	 
	/**
	 * This method receives a generic notification and prints it
	 * @param notification
	 */
	 public void notify(String message){
		 out.println(message);
		 out.flush();
	 }
	 
	/**
	 * The main loop of the CLI, waits for commands and executes them
	 */
	public void start()
	{
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				out.println("Available commands: ");
				out.println("------------------- ");
				out.println("display, dir, display_cross_section, save_maze, load_maze, solve, display_solution,");
				out.println("exit, new_properties, hint, save_solutions, load_solutions\n");
				while (true) {
			
					out.println("Choose a command: ");
					out.flush();
					try {
						String commandLine = in.readLine();
						setChanged();
						notifyObservers(commandLine);
						
						if (commandLine.equals("exit")) {
							out.println("exited.");
							out.flush();
							break;
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}			
		});
		thread.start();
	}

	/**
	 * This method receives a 2d maze and prints it
	 * @param maze2d
	 */
	public void displayMaze2d(int[][] maze2d) {
		for (int i = 0; i < maze2d.length; i++) {
			for (int j = 0; j < maze2d[0].length; j++) {
				out.print(maze2d[i][j]);
			}
			out.println("");
		}
		
	}
	
	/**
	 * This method receives a 3d maze and prints it
	 * @param maze3d
	 */
	public void displayMaze3d(Maze3d maze3d){
		out.println(maze3d);
	}
}