package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import controller.Command;
import controller.CommandsManager;

public class CLI extends Thread {
	private BufferedReader in;
	private PrintWriter out;
	private CommandsManager commandsManager;
	
	public CLI(BufferedReader in, PrintWriter out, CommandsManager commandsManager) {
		super();
		this.in = in;
		this.out = out;
		this.commandsManager = commandsManager;
	}
	
	 public void setCommandsManager(CommandsManager commandsManager) {
		this.commandsManager = commandsManager;
	}
	 
	 public void receiveNotification(String notification){
		 out.println(notification);
		 out.flush();
	 }

	 public void getListOfFiles(File[] listOfFiles){
		    for (int i = 0; i < listOfFiles.length; i++)
		    	out.println("File: " + listOfFiles[i].getName());
		 out.flush();
	 }
	 
	 public void notify(String message){
		 out.println(message);
		 out.flush();
	 }
	 
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

	public void displayMaze2d(int[][] maze2d) {
		for (int i = 0; i < maze2d.length; i++) {
			for (int j = 0; j < maze2d[0].length; j++) {
				out.print(maze2d[i][j]);
			}
			out.println("");
		}
		
	}
}
