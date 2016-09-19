package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;
import algorithms.mazeGenerators.Direction;

/**
 * <h1> The SimpleMaze3dGenerator Class </h1>
 * This class generates a maze with a simple algorithm. The DFS.
 */
public class SimpleMaze3dGenerator extends Maze3dGeneratorBase {
	private Random rand = new Random();
	private Maze3d maze3d;
	
	public SimpleMaze3dGenerator() {
	}
	
	/**
	 * This is the abstract generate method we implement.
	 * This methods initializes a maze by the parameters given and creates a route with the GenerateSimpleRoute method
	 * @param floors number of floors
	 * @param rows number of rows
	 * @param cols number of columns
	 * @return Maze3d returns the final maze
	 */
	@Override
	public Maze3d generate(int floors, int rows, int cols) {
		maze3d = init(maze3d, floors, rows, cols);
		Position startPos = maze3d.getStartPosition();
		maze3d.setFree(startPos.x, startPos.y, startPos.z);
		GenerateSimpleRoute(maze3d.getStartPosition());			
		return maze3d;
	}

	/**
	 * This method receives an initialized maze and creates a route with the Growing Tree algorithm. 
	 * @param currPos the current position to start the dfs algorithm.
	 */
	private void GenerateSimpleRoute(Position currPos){
		if (isDone)
			return;
		if ( currPos == maze3d.getGoalPosition())
			return;
		
		ArrayList<Direction> dirs = getPossibleDirections(maze3d, currPos);
		if (dirs.size() == 0)
			return;
		int num = rand.nextInt(dirs.size());
		Direction dir = dirs.get(num);
		
		Position newPos;
		
		switch (dir) {
		case Ascend:
			maze3d.setFree(currPos.x+1, currPos.y, currPos.z);
			maze3d.setFree(currPos.x+2, currPos.y, currPos.z);
			newPos = new Position(currPos.x+2,currPos.y,currPos.z);
			GenerateSimpleRoute(newPos);
			break;
			
		case Descend:
			maze3d.setFree(currPos.x-1, currPos.y, currPos.z);
			maze3d.setFree(currPos.x-2, currPos.y, currPos.z);
			newPos = new Position(currPos.x-2,currPos.y,currPos.z);
			GenerateSimpleRoute(newPos);
			break;
			
		case Up:
			maze3d.setFree(currPos.x, currPos.y+1, currPos.z);
			maze3d.setFree(currPos.x, currPos.y+2, currPos.z);
			newPos = new Position(currPos.x,currPos.y+2,currPos.z);
			GenerateSimpleRoute(newPos);
			break;
		
		case Down:
			maze3d.setFree(currPos.x, currPos.y-1, currPos.z);
			maze3d.setFree(currPos.x, currPos.y-2, currPos.z);
			newPos = new Position(currPos.x,currPos.y-2,currPos.z);
			GenerateSimpleRoute(newPos);
			break;
			
		case Right:
			maze3d.setFree(currPos.x, currPos.y, currPos.z+1);
			maze3d.setFree(currPos.x, currPos.y, currPos.z+2);
			newPos = new Position(currPos.x,currPos.y,currPos.z+2);
			GenerateSimpleRoute(newPos);
			break;
			
		case Left:
			maze3d.setFree(currPos.x, currPos.y, currPos.z-1);
			maze3d.setFree(currPos.x, currPos.y, currPos.z-2);
			newPos = new Position(currPos.x,currPos.y,currPos.z-2);
			GenerateSimpleRoute(newPos);
			break;
		}
	}
}
