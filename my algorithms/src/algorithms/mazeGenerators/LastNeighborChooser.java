package algorithms.mazeGenerators;

import java.util.ArrayList;
/**
 * This class tells the GrowingTreeGenerator how to choose the next position.
 * This class chooses the last neighbor in the position list.
 * @author ofir and rom
 *
 */
public class LastNeighborChooser implements NeighborChooser {
	
	@Override
	public Position chooseNeighbor(ArrayList<Position> positions) {
		return positions.get(positions.size()-1);
	}

}
