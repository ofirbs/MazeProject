package algorithms.mazeGenerators;

import java.util.ArrayList;
/**
 * <h1> The NeighborChooser Interface </h1>
 * This interface makes sure the chooseNeighbor function is implemented.
 * @author ofir and rom
 *
 */
public interface NeighborChooser {
	public Position chooseNeighbor(ArrayList<Position> positions);

}
