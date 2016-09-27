package model;

import java.io.Serializable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class Problem implements Serializable{
	private Maze3d maze;
	private Solution<Position> solution;
	private String algType;
	
	public Maze3d getMaze() {
		return maze;
	}
	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}
	public Solution<Position> getSolution() {
		return solution;
	}
	public void setSolution(Solution<Position> solution) {
		this.solution = solution;
	}
	public String getAlgType() {
		return algType;
	}
	public void setAlgType(String algType) {
		this.algType = algType;
	}
	
	
}
