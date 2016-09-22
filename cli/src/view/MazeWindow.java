package view;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

public class MazeWindow extends BaseWindow implements View {

	private MazeDisplay mazeDisplay;
	private Boolean isMazeReady = false;
	private Boolean isSolutionReady = false;
	private Boolean isMazeDisplayed = false;
	
	private Label lblCurrentFloor;

	public void setIsMazeDisplayed(Boolean isMazeDisplayed) {
		this.isMazeDisplayed = isMazeDisplayed;
	}

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
		
		btnSolveMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				update("solve " + mazeDisplay.getMazeName());
				while(!getIsSolutionReady()) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				update("display_solution " + mazeDisplay.getMazeName());
				isSolutionReady=false;
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		Button btnExit = new Button(buttons, SWT.PUSH);
		btnExit.setText("Exit");
		
		Label lblBlank = new Label(buttons, SWT.NONE);
		lblBlank.setVisible(false);
		
		Label lblBlank2 = new Label(buttons, SWT.NONE);
		lblBlank2.setVisible(false);
		
		lblCurrentFloor = new Label(buttons, SWT.NONE);
		lblCurrentFloor.setText("Current floor: " );
		if(isMazeDisplayed)
			lblCurrentFloor.setVisible(true);
		else
			lblCurrentFloor.setVisible(false);
		
		btnExit.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				update("exit");
				shell.dispose();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		mazeDisplay = new MazeDisplay(shell, SWT.BORDER, lblCurrentFloor);
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		mazeDisplay.setFocus();
	}
	
	public Boolean getIsMazeReady() {
		return isMazeReady;
	}
	
	public Boolean getIsSolutionReady() {
		return isSolutionReady;
	}

	public void setIsMazeReady(Boolean isMazeReady) {
		this.isMazeReady = isMazeReady;
	}
	
	public void setMazeName(String mazeName) {
		mazeDisplay.setMazeName(mazeName);
	}
	
	public void update(String command) {
		 setChanged();
		 notifyObservers(command);
	}
	
	public void notifySolutionIsReady(String name) {
		setIsSolutionReady(true);
	}

	private void setIsSolutionReady(boolean isSolutionReady) {
		this.isSolutionReady = isSolutionReady;
	}

	@Override
	public void notifyMazeIsReady(String name) {
		setIsMazeReady(true);
	}

	@Override
	public void displayMaze(Maze3d maze) {
		mazeDisplay.setVisible(true);
		mazeDisplay.configPaintListiner();
		mazeDisplay.setMaze2d(maze, mazeDisplay.getCharacter().getPos().x);
		mazeDisplay.setCharacter(maze.getStartPosition());
		mazeDisplay.setGoal(maze.getGoalPosition());
		lblCurrentFloor.setText("Floor: " + Integer.toString(mazeDisplay.getCharacter().getPos().x + 1)+"/"+Integer.toString(maze.getFloors()));
		lblCurrentFloor.setVisible(true);
		mazeDisplay.initialize(maze);
		mazeDisplay.setCurrentFloor(mazeDisplay.getCharacter().getPos().x);
		mazeDisplay.redraw();
		mazeDisplay.setFocus();
	}

	@Override
	public void printListOfFiles(File[] listOfFiles) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notify(String type) {
		MessageBox msg = new MessageBox(shell, SWT.OK);
		msg.setMessage(type);
		msg.open();
	}

	@Override
	public void displayMaze2d(int[][] maze2d) {
		// TODO Auto-generated method stub
		
	}
	
	public void messageWon() {
		MessageBox msg = new MessageBox(shell, SWT.OK);
		msg.setMessage("You Won!");
		msg.open();
	}

	@Override
	public void displaySolution(Solution<Position> solution) {
		MessageBox msg = new MessageBox(shell, SWT.OK);
		if ( solution == null )
			msg.setMessage("Could not solve solution");
		else
			msg.setMessage(solution.toString());
		msg.open();
	}
}
