package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import algorithms.mazeGenerators.Maze3d;
import controller.Command;
import controller.CommandsManager;

/**
 * <h1> The CLI Class</h1>
 * Creates a CLI to receive user input and display output from the maze
 * @author ofir and rom
 *
 */
public class CLI extends Thread {
	private BufferedReader in;
	private PrintWriter out;
	private CommandsManager commandsManager;
	
	/**
	 * CLI constructor, CLI to receive user input and display output from the maze
	 * @param in
	 * @param out
	 * @param commandsManager
	 */
	public CLI(BufferedReader in, PrintWriter out, CommandsManager commandsManager) {
		super();
		this.in = in;
		this.out = out;
		this.commandsManager = commandsManager;
	}
	
	/**
	 * This method sets the command manager
	 */
	 public void setCommandsManager(CommandsManager commandsManager) {
		this.commandsManager = commandsManager;
	}
	 
		/**
		 * This method receives a notification that the maze is ready and prints it
		 * @param notification
		 */
	 public void receiveNotification(String notification){
		 out.println(notification);
		 out.flush();
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
				while (true) {
				
					out.println("Choose a command: ");
					out.flush();
					try {
						String commandLine = in.readLine();
						String arr[] = commandLine.split(" ");
						String command = arr[0];			
						
						if(!commandsManager.getCommandsMap().containsKey(command)) {
							out.println("Command doesn't exist");
						}
						else {
							String[] args = null;
							if (arr.length > 1) {
								String commandArgs = commandLine.substring(
										commandLine.indexOf(" ") + 1);
								args = commandArgs.split(" ");							
							}
							Command cmd = commandsManager.getCommandsMap().get(command);
							cmd.doCommand(args);				
							
							if (command.equals("exit"))
							{
								out.println("exited.");
								out.flush();
								break;
							}
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