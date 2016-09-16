package view;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import controller.Command;
import controller.Controller;

/**
 * <h1> The MyView Class</h1>
 * Creates an instance of the View interface
 * @author ofir and rom
 *
 */
public class MyView extends Observable implements View, Observer {
	private CLI cli; 
	
	/**
	 * MyView constructor, creates a view to connect between the controller and CLI
	 * @param controller
	 * @param cli
	 */
	public MyView(BufferedReader in, PrintWriter out) {
		super();
		cli = new CLI(in, out);
		cli.addObserver(this);
	}

	/**
	 * This method starts the CLI
	 */
	public void start() {
		cli.start();
	}

	/**
	 * This method sends a confirmation string that the maze is ready to the CLI
	 * @param name
	 */
	@Override
	public void notifyMazeIsReady(String name) {
		cli.receiveNotification("maze " + name + " is ready.");		
	}

	/**
	 * This method prints the received maze
	 * @param Maze3d maze
	 */
	@Override
	public void displayMaze(Maze3d maze) {
		cli.displayMaze3d(maze);
	}

	/**
	 * This method receives a File array with a list of files in a directory and sends it to the CLI
	 * @param listOfFiles
	 */
	@Override
	public void printListOfFiles(File[] listOfFiles) {
		cli.getListOfFiles(listOfFiles); //Send File array to CLI
	}
	/**
	 * This method receives a notification message from the controller and sends it to the CLI
	 * @param message
	 */
	public void notify(String message){
		cli.notify(message);
	}

	/**
	 * This method receives a 2d maze from the controller and sends it to the CLI
	 * @param listOfFiles
	 */
	@Override
	public void displayMaze2d(int[][] maze2d) {
		cli.displayMaze2d(maze2d);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o==cli) {
			setChanged();
			notifyObservers(arg);
		}
		
	}
}
