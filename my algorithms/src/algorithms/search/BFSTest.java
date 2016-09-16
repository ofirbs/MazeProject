package algorithms.search;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import algorithms.demo.MazeDomain;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.LastNeighborChooser;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.RandomNeighborChooser;

public class BFSTest {
	
	private MazeDomain mdomainLNC;
	private MazeDomain mdomainRNC;
	BFS<Position> tester = new BFS<Position>();
	
	@Test
	public void testNull() {
		assertNull("solving null object should return null",tester.search(null));
	}
	
	@Before
	public void preTest() {
		mdomainLNC = new MazeDomain(new GrowingTreeGenerator(new LastNeighborChooser()).generate(6, 6, 6));
		mdomainRNC = new MazeDomain(new GrowingTreeGenerator(new RandomNeighborChooser()).generate(6, 6, 6));
	}
	
	@Test
	public void testLastChooser() {
		assertNotNull("solving a 6x6x6 maze via LastNeighborChooser should not return null", tester.search(mdomainLNC));
	}
	
	@Test
	public void testRandomChooser() {
		assertNotNull("solving a 6x6x6 maze via RandomNeighborChooser should not return null", tester.search(mdomainRNC));
	}
}
