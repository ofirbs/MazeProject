package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import properties.PropertiesLoader;

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
		
		//Generate Button Functionality
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				GenerateMazeWindow win = new GenerateMazeWindow(MazeWindow.this);				
				win.start(display);
				mazeDisplay.setIsHinted(false);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		Button btnSolveMaze = new Button(buttons, SWT.PUSH);
		btnSolveMaze.setText("Solve maze");
		
		//Solve Button Functionality
		btnSolveMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(mazeDisplay.getMazeName() != null){
					update("solve " + mazeDisplay.getMazeName());
					while(!getIsSolutionReady()) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					update("display_solution " + mazeDisplay.getMazeName());
					
				}
				else{
					MessageBox msg = new MessageBox(shell, SWT.OK);
					msg.setMessage("No maze to solve");
					msg.open();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				
			}
		});
		
		Button btnHint = new Button(buttons, SWT.PUSH);
		btnHint.setText("Hint");
		
		//Hint Button Functionality
		btnHint.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(mazeDisplay.getMazeName() != null){
					mazeDisplay.getMaze().setStartPosition(mazeDisplay.getCharacter().getPos());
					update("solve " + mazeDisplay.getMazeName());
					while(!getIsSolutionReady()) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					update("hint "+ mazeDisplay.getMazeName());
					mazeDisplay.setIsHinted(true);
				}
				else{
					MessageBox msg = new MessageBox(shell, SWT.OK);
					msg.setMessage("No maze found");
					msg.open();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
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
		
		//Exit Button Functionality
		btnExit.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MessageBox msg = new MessageBox(shell,SWT.YES | SWT.NO);
				msg.setMessage("Are you sure?");
				msg.setText("Exit maze");
				int response = msg.open();
				if(response == SWT.YES){
					update("exit");
					shell.dispose();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		Button btnProperties = new Button(buttons, SWT.PUSH);
		btnProperties.setText("Properties file");
		
		btnProperties.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog dialog = new FileDialog(shell, SWT.OPEN);
				dialog.setFilterNames(new String[] { ".xml"});
				dialog.setFilterExtensions(new String[] { "*.xml"}); 
				String selectedFile = dialog.open();
				if(selectedFile != null){
				PropertiesLoader loader = new PropertiesLoader(selectedFile);
				update("new_properties "+loader.getProperties().getGenerateMazeAlgorithm()+" "+loader.getProperties().getSolveMazeAlgorithm());
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
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
		mazeDisplay.setCharacter(maze.getStartPosition());
		mazeDisplay.setMaze2d(maze, mazeDisplay.getCharacter().getPos().x);
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
		
	}

	@Override
	public void notify(String type) {
		MessageBox msg = new MessageBox(shell, SWT.OK);
		msg.setMessage(type);
		msg.open();
	}

	@Override
	public void displayMaze2d(int[][] maze2d) {
		
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

		List<State<Position>> states = solution.getStates();

		display.timerExec(500,  new Runnable() {
			
				int currIndex = 0;
				@Override
				public void run() {
					if (currIndex == states.size() - 1) {
						mazeDisplay.removePaintListener(mazeDisplay.getPs());
						return;		
					}
					else {
						State<Position> currState = states.get(currIndex);
						State<Position> nextState = states.get(currIndex + 1);
						
						Position startPos = currState.getValue();
						Position nextPos = nextState.getValue();
						if (nextPos.z == startPos.z + 1)
							mazeDisplay.moveRight(startPos);
						else if (nextPos.z == startPos.z - 1)
							mazeDisplay.moveLeft(startPos);
						else if (nextPos.y == startPos.y + 1) 
							mazeDisplay.moveDown(startPos);
						else if (nextPos.y == startPos.y - 1)
							mazeDisplay.moveUp(startPos);
						else if (nextPos.x == startPos.x + 1)
							mazeDisplay.moveAbove(startPos);
						else if (nextPos.x == startPos.x - 1)
							mazeDisplay.moveBelow(startPos);
						
						currIndex++;
						display.timerExec(500,  this);
					}				
			}
		});
		isSolutionReady=false;
	}
	
	public void hint(Solution<Position> solution) {
		mazeDisplay.setIsHinted(true);
		if (solution == null) {
			MessageBox msg = new MessageBox(shell, SWT.OK);
			msg.setMessage("No solution");
			msg.open();
		}
			
		List<State<Position>> states = solution.getStates();
		
		List<State<Position>> floorStates = new ArrayList<State<Position>>();
		
		int currIndex = 0;
		if (currIndex == states.size() - 1) {
			return;		
		}
		
		State<Position> currState = states.get(currIndex);
		State<Position> nextState = states.get(currIndex + 1);
		
		//if (!(nextState.getValue().x == mazeDisplay.getMaze().getFloors() - 1))
		//{
			if (currState.getValue().x == nextState.getValue().x - 1) {
				MessageBox msg = new MessageBox(shell, SWT.OK);
				msg.setMessage("Go Above");
				msg.open();
			}
		//}
		//if (!(currState.getValue().x == 0 ))
		//{
			if (currState.getValue().x == nextState.getValue().x + 1) {
				MessageBox msg = new MessageBox(shell, SWT.OK);
				msg.setMessage("Go Below");
				msg.open();
			}
		//}
		
		if( nextState.getValue().equals(mazeDisplay.getGoal().getPos()))
			return;
		
		while ((currIndex != states.size() - 1) && (nextState.getValue().x == currState.getValue().x)) {
			floorStates.add(nextState);
			currIndex++;
			if (currIndex == states.size() - 2)
				break;
			nextState = states.get(currIndex + 1);
		} 
		
		mazeDisplay.setSolutionForHint(floorStates);
		mazeDisplay.redraw();
		
		/*int currIndex = 0;
		
		if (currIndex == states.size() - 1) {
			return;		
		}
		else {
			State<Position> currState = states.get(currIndex);
			State<Position> nextState = states.get(currIndex + 1);
			Position startPos = currState.getValue();
			Position nextPos = nextState.getValue();
			
			if (!(nextPos.x == mazeDisplay.getMaze().getFloors() - 1))
			{
				if (nextPos.x == startPos.x + 1) {
					MessageBox msg = new MessageBox(shell, SWT.OK);
					msg.setMessage("Go Above");
					msg.open();
				}
			}
			if (!(nextPos.x == 0 ))
			{
				if (nextPos.x == startPos.x - 1) {
					MessageBox msg = new MessageBox(shell, SWT.OK);
					msg.setMessage("Go Below");
					msg.open();
				}
			}
			
			mazeDisplay.setSolutionForHint(solution);
			mazeDisplay.redraw();
		}*/
	}
}
