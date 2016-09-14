package view;

import java.io.File;

import algorithms.mazeGenerators.Maze3d;

/**
 * <h1> The View interface</h1>
 * This is the interface of the view object. <br>
 * The view object interacts with the user CLI.
 * @author ofir and rom
 *
 */
public interface View {
	void notifyMazeIsReady(String name);
	void displayMaze(Maze3d maze);
	void printListOfFiles(File[] listOfFiles);
	void notify(String type);
	void displayMaze2d(int[][] maze2d);
}
