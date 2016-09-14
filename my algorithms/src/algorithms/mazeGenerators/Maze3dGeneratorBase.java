package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;

/**
 * <h1> The Abstract Maze3dGeneratorBase Class </h1>
 * This class implements Maze3dGenerator, and contains some of the common functions every maze generator class needs 
 * @author ofir and rom
 *
 */
public abstract class Maze3dGeneratorBase implements Maze3dGenerator {

	private Random rand = new Random();
	
	protected boolean isDone = false;
	
	public boolean isDone() {
		return isDone;
	}
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	
	@Override
	public String measureAlgorithmTime(int floors, int rows, int cols) {
		long startTime = System.currentTimeMillis();
		this.generate(floors, rows, cols);
		long endTime = System.currentTimeMillis();
		return String.valueOf(endTime - startTime);
	}
	/**
	 * This functions chooses a random position locates in an even position
	 * @param maze3d
	 * @return
	 */
	public Position chooseRandomPosition(Maze3d maze3d) {

		int x = rand.nextInt(maze3d.getFloors());
		while (x % 2 == 1)
			x = rand.nextInt(maze3d.getFloors());
		
		int y = rand.nextInt(maze3d.getRows());
		while (y % 2 == 1)
			y = rand.nextInt(maze3d.getRows());
		
		int z = rand.nextInt(maze3d.getCols());
		while (z % 2 == 1)
			z = rand.nextInt(maze3d.getCols());
		
		return new Position(x,y,z);
	}
	
	/**
	 * This methods generates a list of possible directions to carve for a position.
	 * The possible direction needs to be 2 positions away. 
	 * @param maze3d the maze 
	 * @param currPos the position to calculate possible directions from
	 * @return ArrayList<Direction> the list of possible directions
	 */
	
	public ArrayList<Direction> getPossibleDirections(Maze3d maze3d, Position currPos){
		ArrayList<Direction> directions = new ArrayList<Direction>();
		
		if(currPos.x + 2 < maze3d.getFloors() && maze3d.getValue(currPos.x+1, currPos.y, currPos.z) == Maze3d.WALL && maze3d.getValue(currPos.x+2, currPos.y, currPos.z) == Maze3d.WALL)
			directions.add(Direction.Ascend);
		
		if(currPos.x - 2 >=0 && maze3d.getValue(currPos.x-1, currPos.y, currPos.z) == Maze3d.WALL && maze3d.getValue(currPos.x-2, currPos.y, currPos.z) == Maze3d.WALL)
			directions.add(Direction.Descend);
		
		if(currPos.y + 2 < maze3d.getRows() && maze3d.getValue(currPos.x, currPos.y+1, currPos.z) == Maze3d.WALL && maze3d.getValue(currPos.x, currPos.y+2, currPos.z) == Maze3d.WALL)
			directions.add(Direction.Up);
		
		if(currPos.y - 2 >= 0 && maze3d.getValue(currPos.x, currPos.y-1, currPos.z) == Maze3d.WALL && maze3d.getValue(currPos.x, currPos.y-2, currPos.z) == Maze3d.WALL)
			directions.add(Direction.Down);
		
		if(currPos.z + 2 < maze3d.getCols() && maze3d.getValue(currPos.x, currPos.y, currPos.z+1) == Maze3d.WALL && maze3d.getValue(currPos.x, currPos.y, currPos.z+2) == Maze3d.WALL)
			directions.add(Direction.Right);
		
		if(currPos.z - 2 >= 0 && maze3d.getValue(currPos.x, currPos.y, currPos.z-1) == Maze3d.WALL && maze3d.getValue(currPos.x, currPos.y, currPos.z-2) == Maze3d.WALL)
			directions.add(Direction.Left);
						
		return directions;
	}
	
	/**
	 * This methods initializes the maze with walls, a start position and a goal position	
	 * @param maze3d the maze to initialize
	 * @param floors number of floors
	 * @param rows number of rows
	 * @param cols number of columns
	 * @return Maze3d the initialized maze
	 */
	public Maze3d init(Maze3d maze3d, int floors, int rows, int cols){
		maze3d = new Maze3d(floors,rows, cols);
		//begin by setting all the positions with walls
		maze3d.setAllWalls();
		maze3d.setStartPosition(chooseRandomPosition(maze3d));
		//make sure the goal is not the start
		Position randomTempPosition = chooseRandomPosition(maze3d);
		while(maze3d.getStartPosition() == randomTempPosition)
			randomTempPosition = chooseRandomPosition(maze3d);
		maze3d.setGoalPosition(chooseRandomPosition(maze3d));
		return maze3d;
	}
}
