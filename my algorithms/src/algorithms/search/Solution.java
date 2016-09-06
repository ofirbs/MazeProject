package algorithms.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1> The Solution Class </h1>
 * This class represents the solution for the goal state.
 * It contains a List of states from the start untill the goal.
 * @author ofir and rom
 *
 * @param <T>
 */

public class Solution<T> {
	private List<State<T>> states = new ArrayList<State<T>>();

	public List<State<T>> getStates() {
		return states;
	}

	public void setStates(List<State<T>> states) {
		this.states = states;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (State<T> s : states) {
			sb.append(s.toString()).append(" ");
		}
		return sb.toString();
	}
	
}
