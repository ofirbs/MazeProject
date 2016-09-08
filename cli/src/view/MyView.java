package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import controller.Command;
import controller.Controller;

public class MyView implements View {
	private Controller controller;
	private CLI cli; 
	
	public MyView(Controller controller, CLI cli) {
		super();
		this.controller = controller;
		this.cli = cli;
	}

	public void start() {
		cli.start();
	}

	@Override
	public void notifyMazeIsReady(String name) {
		cli.receiveNotification("maze " + name + " is ready.");		
	}

	@Override
	public void setCommands(HashMap<String, Command> commands) {
		
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void displayMaze(Maze3d maze) {
		System.out.println(maze);
	}
}
