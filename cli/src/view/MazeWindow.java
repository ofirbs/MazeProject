package view;

import java.io.File;
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
		
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				GenerateMazeWindow win = new GenerateMazeWindow(MazeWindow.this);				
				win.start(display);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		Button btnSolveMaze = new Button(buttons, SWT.PUSH);
		btnSolveMaze.setText("Solve maze");
		
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
					isSolutionReady=false;
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
		
		btnHint.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(mazeDisplay.getMazeName() != null){
					//TODO: Add hint function
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
		/*MessageBox msg = new MessageBox(shell, SWT.OK);
		if ( solution == null )
			msg.setMessage("Could not solve solution");
		else
			msg.setMessage(solution.toString());
		msg.open();*/	
		
		
		List<State<Position>> states = solution.getStates();
		//OPTION 1
		/*ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
		Runnable runnable = new Runnable() {
			
			@Override
			public void run() {
				int currIndex = 0;
					if (currIndex == states.size() - 1) {
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
				}			

			}
		};
		
		scheduler.scheduleAtFixedRate(runnable, 0, 500, TimeUnit.MILLISECONDS);*/
		
		
		//OPTION 2
		/*
		TimerTask task = new TimerTask() {
			int currIndex = 0;
			
			@Override
			public void run() {
				if (currIndex == states.size() - 1) {
					this.cancel();					
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
				}				
			}			
		};
			
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 500);*/
			
	}
}
