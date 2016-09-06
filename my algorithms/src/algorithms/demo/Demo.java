package algorithms.demo;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.LastNeighborChooser;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.BFS;
import algorithms.search.DFS;
import algorithms.search.Searchable;
import algorithms.search.Searcher;
/**
 * <h1>The Demo Class</h1>
 *	This class creates a demo for the search algorithms for a 3d maze.
 * @author ofir and rom
 *
 */
public class Demo {
	/**
	 * <h1>Run()</h1>
	 * <br><i> public void run() </i>
	 * <br><br>This method creates a 6x6x6 3d Maze with the GrowingTreeAlgorithm and does the following:
	 * <br>1) Prints the maze.
	 * <br>2) Prints the BFS search algorithm output and the number of nodes evaluated.
	 * <br>3) Prints the DFS search algorithm output and the number of nodes evaluated.
	 */
	public void run(){
		Maze3d maze = new GrowingTreeGenerator(new LastNeighborChooser()).generate(6, 6, 6);
		System.out.println(maze);
		
		System.out.println("BFS:");
		testSearcher(new BFS<Position>(), new MazeDomain(maze));
		System.out.println("DFS:");
		testSearcher(new DFS<Position>(), new MazeDomain(maze));
	}
	
	private static void testSearcher(Searcher<Position> searcher, Searchable searchable) {
		System.out.println(searcher.search(searchable));
		System.out.println("Nodes evaluated: " + searcher.getNumberOfNodesEvaluated());
	}
}
