package view;

import java.util.List;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;

public class Hint {
	private Image img;
	private List<State<Position>> solution;
	
	public Hint() {
		img = new Image(null, "images/hint.png");
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
			if (index == solution.size())
				return;
			currState = solution.get(index);
		}
		
		/*List<State<Position>> states = solution.getStates();
		
		State<Position> currState = solution.getStates().get(index);
		State<Position> nextState = solution.getStates().get(++index);
		Position currPos = currState.getValue();
		Position nextPos = nextState.getValue();
		
		while ((index != states.size() - 1) && (nextPos.x == currPos.x)) {
			gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 
					cellWidth * nextPos.z, cellHeight * nextPos.y, cellWidth, cellHeight);
			
			index++;
			nextState = solution.getStates().get(index);
			nextPos = nextState.getValue();
		}*/
	}
	
}
