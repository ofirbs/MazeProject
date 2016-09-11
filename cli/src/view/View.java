package view;

import java.io.File;

import algorithms.mazeGenerators.Maze3d;

public interface View {
	void notifyMazeIsReady(String name);
	void displayMaze(Maze3d maze);
	void printListOfFiles(File[] listOfFiles);
	void notify(String type);
	void displayMaze2d(int[][] maze2d);
}
