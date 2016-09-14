package algorithms.search;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <h1> DFS - Depth First Search Algorithm </h1>
 * The algorithm searches the goal state of the searchable object.
 * @author ofir and rom
 *
 * @param <T>
 */

public class DFS<T> extends CommonSearcher<T> {

	private Searchable searchable;
	private Set<State<T>> closedList = new HashSet<State<T>>();
	/**
	 * This method is used to run the search. it calls the <i>searchHelper</i> with the start state.
	 * @param searchable This is the searchable object.
	 * @return Solution<T> This returns the Solution for the searchable object using DFS algorithm returned by searchHelper. 
	 */
	@Override
	public Solution<T> search(Searchable searchable) {
		this.searchable = searchable;
		return searchHelper(searchable.getStartState());
	}
	/**
	 * This method is called by search and does the search for a specific state.
	 * @param state this is the state to start searching from.
	 * @return Solution<T> This returns the Solution for the searchable object using DFS algorithm.
	 */
	public Solution<T> searchHelper(State<T> state){
		if (isDone)
			return null;
		evaluateNodes++;
		if ( state.equals(searchable.getGoalState()))
			return backTrace(state);
		
		List<State<T>> neighbors = searchable.getAllPossibleStates(state);
		closedList.add(state);
		
		for (State<T> neighbor : neighbors) {
			if(!closedList.contains(neighbor)) {
				closedList.add(neighbor);
				return searchHelper(neighbor);
			}
		}
		return searchHelper(state.getCameFrom());
	}
}
