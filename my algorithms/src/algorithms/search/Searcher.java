package algorithms.search;

/**
 * <h1>The Searcher Interface</h1>
 * @author ofir and rom
 * This is the interface of the Searcher object.
 * Every class implementing this interface must implement the following functions:
 * <i> Solution<T> search(Searchable s) </i>
 * <i> int getNumberOfNodesEvaluated() </i>
 * @param <T>
 */
public interface Searcher<T> {
	public Solution<T> search(Searchable s);
	
	public int getNumberOfNodesEvaluated();

}
