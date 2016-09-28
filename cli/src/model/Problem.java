package model;

import java.io.Serializable;

import algorithms.mazeGenerators.Maze3d;
/**
 * <h1> The Problem Class</h1>
 * This class wraps the solution into a serializable class.<br>
 * it holds the maze and the algorithm type
 * @author ofir and rom
 *
 */
public class Problem implements Serializable{
	private static final long serialVersionUID = 1L;
	private Maze3d maze;
	private String algType;
	
	public Maze3d getMaze() {
		return maze;
	}
	
	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}

	public String getAlgType() {
		return algType;
	}
	
	public void setAlgType(String algType) {
		this.algType = algType;
	}
}
