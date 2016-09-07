package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

import controller.Command;

public class CLI extends Thread {
	private BufferedReader in;
	private PrintWriter out;
	private HashMap<String, Command> commands = new HashMap<String, Command>();
	
	public CLI(BufferedReader in, PrintWriter out, HashMap<String, Command> commands) {
		super();
		this.in = in;
		this.out = out;
		this.commands = commands;
		commands.put("1", new oneCommand());
	}
	
	 public void start()
	{
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				out.println("Choose a command: ");
				String cmd="";
				try {
					cmd = in.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				while (!(cmd.equals("exit"))) {
					
					Command command = commands.get(cmd);
					if (command != null)
						command.doCommand();
					else
						out.println("No such command.");
					
					out.println("Choose a command: ");
					try {
						cmd = in.readLine();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				out.close(); //Close output file to save changes
			}
		});
		thread.start();
	}
	 
	 class oneCommand implements Command {
			@Override
			public void doCommand() {
				out.println("one");
			}	
		}
}
