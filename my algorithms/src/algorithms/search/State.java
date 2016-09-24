package algorithms.search;

import java.io.Serializable;

/**
 * <h1>The State<T> Class</h1>
 * This class implements Comparable<State<T>> and represents the current search state. 
 * @author ofir and rom
 *
 * @param <T>
 */

public class State<T> implements Comparable<State<T>>,Serializable {
	private String key;
	private double cost;
	private State<T> cameFrom;
	private T value;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public State<T> getCameFrom() {
		return cameFrom;
	}
	public void setCameFrom(State<T> cameFrom) {
		this.cameFrom = cameFrom;
	}
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj instanceof State<?>)
		{
			State<?> s = (State<?>)obj;
			return s.value.equals(this.value);
		}
		return false;
	}
	
	@Override
	public int compareTo(State<T> s) {
		return (int)(this.getCost() - s.getCost());
	}
	
	@Override
	public String toString() {
		return value.toString();
	}
	
	@Override
	public int hashCode(){
		return key.hashCode();
	}

}
