package view;

import java.util.List;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;
import algorithms.search.State;
/**
 * <h1> The Hint Class</h1>
 * This class represents the hint functionality for the maze.<br>
 * It gets and draws the solution for the current floor only.
 * @author ofir and rom
 *
 */
public class Hint {
	private Image img;
	private List<State<Position>> solution;
	
	public Hint() {
		img = new Image(null, getClass().getClassLoader().getResourceAsStream("hint.png"));
	}
	
	
	public List<State<Position>> getSolution() {
		return solution;
	}

	public void setSolution(List<State<Position>> solution) {
		this.solution = solution;
	}

	public void draw(int cellWidth, int cellHeight, GC gc) {
		if ((solution == null) || (solution.size() == 0)) {
			return;
		}
		int index = 0;
		State<Position> currState = solution.get(index);
		while ((index != solution.size() )) {
			gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 
					cellWidth * currState.getValue().z, cellHeight * currState.getValue().y, cellWidth, cellHeight);
			index++;
			//do not draw on the goal
			if (index == solution.size())
				return;
			currState = solution.get(index);
		}
	}
	
}
