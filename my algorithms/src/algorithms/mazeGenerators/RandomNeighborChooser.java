package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class tells the GrowingTreeGenerator how to choose the next position.
 * This class chooses a random neighbor from the position list.
 * @author ofir
 *
 */
public class RandomNeighborChooser implements NeighborChooser {

	private Random rand = new Random();

	@Override
	public Position chooseNeighbor(ArrayList<Position> positions) {
		int num = rand.nextInt(positions.size());
		return positions.get(num);
		
	}

}
