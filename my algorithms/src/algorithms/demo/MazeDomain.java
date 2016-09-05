package algorithms.demo;

import java.util.ArrayList;
import java.util.List;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Searchable;
import algorithms.search.State;

/**
 * <h1> The MazeDomain Class </h1>
 * This class is an Object Adapter connection the Searchable object with a maze.
 * With this class no changes are needed for the maze class. This class can cooperate with the search algorithms easily.
 * This class implements all of the needed function in the Searchable interface.
 * @author ofir
 *
 */
public class MazeDomain implements Searchable {

	private Maze3d maze;
	
	public MazeDomain(Maze3d maze) {
		this.maze = maze;
	}

	@Override
	public <T> State<T> getStartState() {
		State<T> state = new State<T>();
		state.setValue((T) maze.getStartPosition());
		state.setKey(state.getValue().toString());
		state.setCost(0);
		state.setCameFrom(null);
		return state;		
	}

	@Override
	public <T> State<T> getGoalState() {
		State<T> state = new State<T>();
		state.setValue((T) maze.getGoalPosition());
		state.setKey(state.getValue().toString());
		state.setCost(0);
		state.setCameFrom(null);
		return state;
	}

	@Override
	public <T> List<State<T>> getAllPossibleStates(State<T> s) {
		String[] moves = maze.getPossibleMoves((Position)s.getValue());
		ArrayList<State<T>> states = new ArrayList<State<T>>();
		
		for (int i = 0; i < moves.length; i++) {
			State<Position> neighborState = new State<Position>();
			Position neighborPosition = new Position((Position)s.getValue());
			
			switch (moves[i]) {
			case "Ascend":
				neighborPosition.x++;
				break;
			case "Descend":
				neighborPosition.x--;
				break;
			case "Up":
				neighborPosition.y--;
				break;
			case "Down":
				neighborPosition.y++;
				break;
			case "Left":
				neighborPosition.z--;
				break;
			case "Right":
				neighborPosition.z++;
				break;
			default:
				break;
			}
			
			neighborState.setValue(neighborPosition);
			neighborState.setCost(s.getCost() + 10);
			neighborState.setCameFrom((State<Position>) s);
			neighborState.setKey(neighborState.getValue().toString());
			states.add((State<T>) neighborState);
		}
		return states;
	}

	@Override
	public <T> double getMoveCost(State<T> currState, State<T> neighbor) {
		return 10;
	}
}
