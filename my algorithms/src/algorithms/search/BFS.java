package algorithms.search;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * <h1> BFS - Best First Search Algorithm </h1>
 * The algorithm searches the goal state of the searchable object.
 * @author ofir and rom
 *
 * @param <T>
 */

public class BFS<T> extends CommonSearcher<T> {
	
	private PriorityQueue<State<T>> openList = new PriorityQueue<State<T>>();

	private Set<State<T>> closedList = new HashSet<State<T>>();
	
	/**
	 * This method is used to run the search.
	 * @param s This is the searchable object.
	 * @return Solution<T> This returns the Solution for the searchable object using BFS algorithm. 
	 */
	
	@Override
	public Solution<T> search(Searchable s){

		openList.add(s.getStartState());
		
		while (!openList.isEmpty() && !isDone) {
			State<T> currState = openList.poll();
			evaluateNodes++;
			closedList.add(currState);
			

			if (currState.equals(s.getGoalState())) {
				return backTrace(currState);
			}
			
			List<State<T>> neighbors = s.getAllPossibleStates(currState);
			
			for (State<T> neighbor : neighbors) {
				if (!openList.contains(neighbor) && !closedList.contains(neighbor)) {
					neighbor.setCameFrom(currState);
					neighbor.setCost(currState.getCost() + s.getMoveCost(currState, neighbor));
					openList.add(neighbor);
				}
				else {
					double newPathCost = currState.getCost() + s.getMoveCost(currState, neighbor);
					if (neighbor.getCost() > newPathCost) {
						neighbor.setCost(newPathCost);
						neighbor.setCameFrom(currState);
						
						if (!openList.contains(neighbor)) {
							openList.add(neighbor);
						} 
						else { 
							openList.remove(neighbor);
							openList.add(neighbor);
						}
					}
				}			
			}
		}
		Solution<T> sol = new Solution<T>();
		sol.setStates(backTrace(s.getGoalState()).getStates());
		return null;
	}
}
