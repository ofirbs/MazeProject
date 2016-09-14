package model;

import algorithms.mazeGenerators.Maze3d;

public interface Model {
	void generate_3d_maze(String name, int floors, int rows, int cols);
	Maze3d display(String name);
	int[][] display_cross_section_byX(int index, String name);
	int[][] display_cross_section_byZ(int index, String name);
	int[][] display_cross_section_byY(int index, String name);
	int getFloors(String name);
	int getRows(String name);
	int getCols(String name);
	boolean isMazeExists(String name);
	void saveMaze(String name, String path);
	void loadMaze(String path, String name);
	void solveMaze(String name,String alg);
	void displaySolution(String name); 
	void exit();
}
