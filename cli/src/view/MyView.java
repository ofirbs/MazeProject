package view;

import java.io.File;
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

	
	public void setController(Controller controller) {
		this.controller = controller;
	}

	@Override
	public void displayMaze(Maze3d maze) {
		System.out.println(maze);
	}


	@Override
	public void printListOfFiles(File[] listOfFiles) {
		cli.getListOfFiles(listOfFiles); //Send File array to CLI
	}
	
	public void notify(String type){
		cli.notifyAboutError(type);
	}
}
