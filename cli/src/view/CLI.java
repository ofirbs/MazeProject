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
				out.println("Choose a command: ");
				out.flush();
				String cmd="";
				try {
					cmd = in.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				while (!(cmd.equals("exit"))) {
					//System.out.println(cmd.substring(0, cmd.indexOf(' ')));
					Command command = commandsManager.getCommandsMap().get(cmd.substring(0, cmd.indexOf(' ')));
					if (command != null){
						String[] args = cmd.split(" ", 2)[1].split(" ");
						command.doCommand(args);
					}
					else
					{
						out.println("No such command.");
						out.flush();
					}
					out.flush();
					out.println("Choose a command: ");
					out.flush();
					try {
						cmd = in.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				out.println("exited.");
				out.close(); //Close output file to save changes
				
			}
		});
		thread.start();
		thread.interrupt();
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
