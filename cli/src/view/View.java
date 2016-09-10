package view;

import java.io.File;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import controller.Command;

public interface View {
	void notifyMazeIsReady(String name);
	void setCommands(HashMap<String, Command> commands);
	void displayMaze(Maze3d maze);
	void printListOfFiles(File[] listOfFiles);

}
