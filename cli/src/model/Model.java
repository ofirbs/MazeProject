package model;

import algorithms.mazeGenerators.Maze3d;

public interface Model {
	void generate_3d_maze(String name, int floors, int rows, int cols);
	Maze3d display(String name);
}
