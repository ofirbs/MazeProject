package view;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
/**
 * <h1> The MazeDisplay Class</h1>
 * This class handles the display of the maze.<br>
 * It Holds the characters and the maze, and handles the movement of the character.
 * Includes basic maze commands.
 * @author ofir and rom
 *
 */
public class MazeDisplay extends Canvas {
	private Maze3d maze;
	private int[][] mazeData;
	private String mazeName;
	private int floors;
	private int rows;
	private int cols;
	private Character character;
	private Hint hint;
	private Goal goal;
	private int currentFloor;
	private Label lblCurrentFloor;
	private PaintListener ps;
	private KeyListener keyListener;
	private Boolean isHinted = false;
	private Image border = new Image(null, "images/border1.png");;
	private Image upArrow = new Image(null, "images/upArrow.png");;
	private Image downArrow = new Image(null, "images/downArrow.png");;
	private Image upDownArrow = new Image(null, "images/upDownArrow.png");;


	public Character getCharacter() {
		return character;
	}

	public Goal getGoal() {
		return goal;
	}

	public void setGoal(Position pos) {
		goal.setPos(pos);
	}

	public void setCharacter(Position pos) {
		character.setPos(pos);
	}
	
	public Boolean getIsHinted() {
		return isHinted;
	}

	public void setIsHinted(Boolean isHinted) {
		this.isHinted = isHinted;
	}

	public Hint getHint() {
		return hint;
	}

	public void setSolutionForHint(List<State<Position>> states) {
		this.hint.setSolution(states);
	}

	public String getMazeName() {
		return mazeName;
	}

	public void setMazeName(String mazeName) {
		this.mazeName = mazeName;
	}

	public Maze3d getMaze() {
		return maze;
	}

	public void setMaze(Maze3d maze) {
		this.maze = maze;
	}

	public int getCurrentFloor() {
		return currentFloor;
	}

	public void setCurrentFloor(int currentFloor) {
		this.currentFloor = currentFloor;
	}
	
	public PaintListener getPs() {
		return ps;
	}
	
	public void initialize(Maze3d maze) {
		this.maze = maze;
	}
	public void configPaintListiner() {
		this.setBackgroundImage(null);
		ps = new PaintListener() {
			int type;
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(new Color(null,0,0,0));
				e.gc.setBackground(new Color(null,0,0,0));
				   
				int width=getSize().x;
				int height=getSize().y;
	
				int w=width/mazeData[0].length;
				int h=height/mazeData.length;
	
				for(int i=0;i<mazeData.length;i++)
				   for(int j=0;j<mazeData[i].length;j++){
				       int x=j*w;
				       int y=i*h;
				       //draw the border cells
				       if(mazeData[i][j]!=0) {
				    	   e.gc.drawImage(border, 0, 0, border.getBounds().width, border.getBounds().height, x, y, w, h);
				       }
				    	   
				    	   
				       else {
				    	   //get the cell type. can be a cell that has a way up, down, both or none.
				    	   type = getCellType(new Position(currentFloor,i,j));
				    	   
				    	   //draw black road(the base)
				    	   e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_BLACK));
				    	   e.gc.fillRectangle(x,y,w,h);
				    	switch (type) {
				    	
				    	//a cell that has a way up, draw arrow up
						case 2:
							e.gc.drawImage(upArrow, 0, 0, upArrow.getBounds().width, upArrow.getBounds().height, x, y, w, h);
							break;
						//a cell that has a way down, draw arrow down
						case 4:
							e.gc.drawImage(downArrow, 0, 0, downArrow.getBounds().width, downArrow.getBounds().height, x, y, w, h);
							break;
						//a cell that has a way up and down, draw arrow up and down
						case 6:
							e.gc.drawImage(upDownArrow, 0, 0, upDownArrow.getBounds().width, upDownArrow.getBounds().height, x, y, w, h);
							break;
						}
				       }   
				   }
				//if the goal is in the same floor, draw it
				if (goal.getPos().x == currentFloor)
					goal.draw(w, h, e.gc);
				
				//if a hint was requested draw it
				if (isHinted) 
					hint.draw(w,h, e.gc);	
				//draw the character
				character.draw(w, h, e.gc);
			}
		};
		
		this.addPaintListener(ps);
	}
	
	
	public MazeDisplay(Composite parent, int style, Label lblCurrentFloor) {
		super(parent, style);
		this.setBackgroundImage(new Image(null, "images/start.png"));
		
		character = new Character();
		goal = new Goal();
		hint = new Hint();
		this.lblCurrentFloor = lblCurrentFloor;
		//goal.setPos(new Position(0,1,1));
		//character.setPos(new Position(0,1,1)); 
	}
	
	public int getCellType(Position pos) {
		int mode=0;
		if (!(pos.x >= floors -1))
			//can move up
			if(maze.getValue(pos.x+1, pos.y, pos.z) == 0)
				mode+=2;
		if (!(pos.x <= 0))
			//can move down
			if(maze.getValue(pos.x-1, pos.y, pos.z) == 0)
				mode+=4;
		return mode;
	}
	
	public void setMaze2d(Maze3d maze, int floor) {
		this.mazeData = maze.getCrossSectionByX(floor);
		this.rows = maze.getRows();
		this.cols = maze.getCols();
		this.floors = maze.getFloors();
	}
	
	
	public void checkWon() {
		if (character.getPos().equals(goal.getPos())) {
			this.removePaintListener(ps);
			this.removeKeyListener(keyListener);
			this.maze = null;
			this.mazeData = null;
			this.mazeName = null;
			this.setBackgroundImage(new Image(null,"images/end.png"));
		}
	}
	
	/**
	 * moveLeft if possible, moves the character left and changes its picture
	 * @param pos the position of the current characters position
	 */
	public void moveLeft(Position pos) {
		character.setImg(new Image(null,"images/characterL.png"));
		redraw();
		if (pos.z <= 0)
			return;
		if(character.checkCollision(mazeData[pos.y][pos.z-1]))
			return;
		character.moveLeft();
		checkWon();
		redraw();
	}
	
	/**
	 * moveRight if possible, moves the character right and changes its picture
	 * @param pos the position of the current characters position
	 */
	public void moveRight(Position pos) {
		character.setImg(new Image(null,"images/character.png"));
		redraw();
		if (pos.z >= cols-1)
			return;
		if(character.checkCollision(mazeData[pos.y][pos.z+1]))
			return;
		character.moveRight();
		checkWon();
		redraw();
	}
	
	/**
	 * moveUp if possible, moves the character up and changes its picture
	 * @param pos the position of the current characters position
	 */
	public void moveUp(Position pos) {
		character.setImg(new Image(null,"images/characterU.png"));
		redraw();
		if (pos.y <= 0)
			return;
		if(character.checkCollision(mazeData[pos.y-1][pos.z]))
			return;
		character.moveUp();
		checkWon();
		redraw();
	}
	
	/**
	 * moveDown if possible, moves the character down and changes its picture
	 * @param pos the position of the current characters position
	 */
	public void moveDown(Position pos) {
		character.setImg(new Image(null,"images/characterD.png"));
		redraw();
		if (pos.y >= rows-1)
			return;
		if(character.checkCollision(mazeData[pos.y+1][pos.z]))
			return;
		character.moveDown();
		checkWon();
		redraw();
	}
	
	/**
	 * moveAbove if possible, moves the character to the upper floor
	 * @param pos the position of the current characters position
	 */
	public void moveAbove(Position pos) {
		if (pos.x >= floors -1)
			return;
		if(maze.getValue(pos.x+1, pos.y, pos.z) == 1)
			return;
		isHinted = false;
		mazeData = maze.getCrossSectionByX(pos.x+1);
		character.moveAbove();
		currentFloor++;
		lblCurrentFloor.setText("Floor: " + (currentFloor+1) +"/"+floors);					
		checkWon();
		redraw();
	}
	
	/**
	 * moveBelow if possible, moves the character to the lower floor
	 * @param pos the position of the current characters position
	 */
	public void moveBelow(Position pos) {
		if (pos.x <= 0)
			return;
		if(maze.getValue(pos.x-1, pos.y, pos.z) == 1)
			return;
		isHinted = false;
		mazeData = maze.getCrossSectionByX(pos.x-1);	
		character.moveBelow();
		currentFloor--;
		lblCurrentFloor.setText("Floor: " + (currentFloor+1) +"/"+floors);					
		checkWon();
		redraw();
	}

	//the key listener
	public void configKeyListiner() {
			
		keyListener = new KeyListener() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				Position pos = character.getPos();
				switch (e.keyCode) {
				case SWT.ARROW_LEFT:					
					moveLeft(pos);
					break;
				
				case SWT.ARROW_RIGHT:
					moveRight(pos);
					break;
					
				case SWT.ARROW_UP:					
					moveUp(pos);
					break;
					
				case SWT.ARROW_DOWN:					
					moveDown(pos);
					break;
					
				case SWT.PAGE_UP:					
					moveAbove(pos);
					break;
					
				case SWT.PAGE_DOWN:					
					moveBelow(pos);
					break;
				}
			}
		};
	this.addKeyListener(keyListener);
	}
}

