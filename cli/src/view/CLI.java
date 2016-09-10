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
	 
	 public void notifyAboutError(String type){
		 switch (type){
		 case "dir": out.println("Directory not found");
	 				break;
		 case "display_cross_section": out.println("Index not found");
		 			break;
		 }
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
}
