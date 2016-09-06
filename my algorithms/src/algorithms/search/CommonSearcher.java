package algorithms.search;

import java.util.List;

/**
 * <h1> The Abstract CommonSearcher Class</h1>
 * This class implements Searcher<T> and overrides <i> getNumberOfNodesEvaluated() </i> and <i> getNumberOfNodesEvaluated() </i>
 * The CommonSearcher also generates the solution for the goal state.
 * @author ofir and rom
 * @param <T>
 */

public abstract class CommonSearcher<T> implements Searcher<T> {
	
	protected int evaluateNodes;
	/**
	 * This method only zeroes <i>evaluatedNodes</i> data member
	 */
	public CommonSearcher() {
		super();
		evaluateNodes = 0;
	}

	@Override
	public int getNumberOfNodesEvaluated() {
		return evaluateNodes;
	}
	
	
	public void setEvaluateNodes(int evaluateNodes) {
		this.evaluateNodes = evaluateNodes;
	}
	/**
	 * This method generates the solution of the goal state.
	 * @param goalState This is the goal state
	 * @return Solution<T> this is the solution route leading to the goal State.
	 */
	protected Solution<T> backTrace(State<T> goalState) {
		Solution<T> sol = new Solution<T>();
		
		State<T> currState = goalState;
		List<State<T>> states = sol.getStates();
		while (currState != null) {
			states.add(0, currState);
			currState = currState.getCameFrom();
		}
		return sol;
	}
}
