package view;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;

public class MazeWindow extends BaseWindow implements View {

	private MazeDisplay mazeDisplay;

	@Override
	protected void initWidgets() {
		
		GridLayout grid = new GridLayout(2, false);
		shell.setLayout(grid);
		
		Composite buttons = new Composite(shell, SWT.NONE);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		buttons.setLayout(rowLayout);
		
		Button btnGenerateMaze = new Button(buttons, SWT.PUSH);
		btnGenerateMaze.setText("Generate maze");
		
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				GenerateMazeWindow win = new GenerateMazeWindow(MazeWindow.this);				
				win.start(display);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		Button btnSolveMaze = new Button(buttons, SWT.PUSH);
		btnSolveMaze.setText("Solve maze");
		
		mazeDisplay = new MazeDisplay(shell, SWT.BORDER);	
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		mazeDisplay.setFocus();
	}
	
	public void update(String command) {
		 setChanged();
		 notifyObservers(command);
	}

	@Override
	public void notifyMazeIsReady(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayMaze(Maze3d maze) {
		mazeDisplay.setMaze2d(maze.getCrossSectionByX(mazeDisplay.getCharacter().getPos().x));
		mazeDisplay.redraw();
	}

	@Override
	public void printListOfFiles(File[] listOfFiles) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notify(String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayMaze2d(int[][] maze2d) {
		// TODO Auto-generated method stub
		
	}
}
