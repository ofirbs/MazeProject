package algorithms.search;

import java.util.List;
/**
 * <h1> The Searchable interface</h1>
 * This is the interface of the searchable object.
 * Every class implementing this interface must implement the following functions:
 * <i> <T> State<T> getStartState() </i>
 * <i> <T> State<T> getGoalState() </i>
 * <i> <T> List<State<T>> getAllPossibleStates(State<T> s) </i>
 * <i> <T> double getMoveCost(State<T> currState, State<T> neighbor) </i>
 * @author ofir and rom
 *
 */
public interface Searchable {
	<T> State<T> getStartState();
	<T> State<T> getGoalState();
	<T> List<State<T>> getAllPossibleStates(State<T> s);
	<T> double getMoveCost(State<T> currState, State<T> neighbor);
}
