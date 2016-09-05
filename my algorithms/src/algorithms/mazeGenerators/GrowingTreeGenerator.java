package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GrowingTreeGenerator extends Maze3dGeneratorBase {

	/**
	 * <h1> The GrowingTreeGenerator Class </h1>
	 * This class generates a maze with the growing tree algorithm.
	 */
	
	private Maze3d maze3d;
	private NeighborChooser nc;
	
	/**
	 * The constructor of GrowingTreeGenerator accepts a NeighborChooser class
	 * which tells the generate algorithm how to choose the positions while building.
	 * @param nc
	 */
	
	public GrowingTreeGenerator(NeighborChooser nc) {
		super();
		this.nc = nc;
	}
	
	/**
	 * This is the abstract generate method we implement.
	 * This methods initializes a maze by the parameters given and creates a route with the generateGrowingTree method
	 * @param floors number of floors
	 * @param rows number of rows
	 * @param cols number of columns
	 * @return Maze3d returns the final maze
	 */
	@Override
	public Maze3d generate(int floors, int rows, int cols) {
		maze3d = init(maze3d,floors,rows,cols);
		generateGrowingTree(maze3d);
		return maze3d;
	}
	
	/**
	 * This method receives an initialized maze and creates a route with the Growing Tree algorithm. 
	 * @param maze3d
	 */
	public void generateGrowingTree(Maze3d maze3d)
	{
		
		ArrayList<Position> positionsList = new ArrayList<Position>(); 
		//start from the randomized start position
		positionsList.add(maze3d.getStartPosition());
		
		while(! positionsList.isEmpty())
		{
			//choose the next positions with the algorithm provided by the NeighborChooser injected
			Position pos = nc.chooseNeighbor(positionsList);
			//check if the position has unvisited neighbors
			ArrayList<Direction> dirs = getPossibleDirections(maze3d, pos);
			//randomize the direction array
			long seed = System.nanoTime();
			Collections.shuffle(dirs, new Random(seed));
			//for each neighbor of pos, create a passage and add it to the list
			for(Direction dir : dirs )
			{
				switch (dir) {
				case Ascend:
					maze3d.setFree(pos.x+1, pos.y, pos.z);
					maze3d.setFree(pos.x+2, pos.y, pos.z);
					positionsList.add(new Position(pos.x+2, pos.y, pos.z));
					break;
					
				case Descend:
					maze3d.setFree(pos.x-1, pos.y, pos.z);
					maze3d.setFree(pos.x-2, pos.y, pos.z);
					positionsList.add(new Position(pos.x-2, pos.y, pos.z));
					break;
					
				case Up:
					maze3d.setFree(pos.x, pos.y+1, pos.z);
					maze3d.setFree(pos.x, pos.y+2, pos.z);
					positionsList.add(new Position(pos.x, pos.y+2, pos.z));
					break;
				
				case Down:
					maze3d.setFree(pos.x, pos.y-1, pos.z);
					maze3d.setFree(pos.x, pos.y-2, pos.z);
					positionsList.add(new Position(pos.x, pos.y-2, pos.z));
					break;
					
				case Right:
					maze3d.setFree(pos.x, pos.y, pos.z+1);
					maze3d.setFree(pos.x, pos.y, pos.z+2);
					positionsList.add(new Position(pos.x, pos.y, pos.z+2));
					break;
					
				case Left:
					maze3d.setFree(pos.x, pos.y, pos.z-1);
					maze3d.setFree(pos.x, pos.y, pos.z-2);
					positionsList.add(new Position(pos.x, pos.y, pos.z-2));
					break;
				}
			}
			positionsList.remove(pos);
		}
	}
}
