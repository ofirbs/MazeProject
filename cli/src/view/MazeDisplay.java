package view;

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
	private Boolean isHinted = false;


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

	public void setSolutionForHint(Solution<Position> solution) {
		this.hint.setSolution(solution);
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
				       if(mazeData[i][j]!=0) {
							e.gc.setBackground(new Color(null,0,0,0));
					    	e.gc.fillRectangle(x,y,w,h);

				       }
				       
				       else {
				    	   type = getCellType(new Position(currentFloor,i,j));
				    	   
				    	switch (type) {
							case 2:
								e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_GREEN));
								e.gc.fillRectangle(x,y,w,h);
								break;
							case 4:
								e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_RED));
								e.gc.fillRectangle(x,y,w,h);
								break;
								
							case 6:
								e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_BLUE));
								e.gc.fillRectangle(x,y,w,h);
								break;
						}
				       }   
				   }
				   
				 
				character.draw(w, h, e.gc);
				if (goal.getPos().x == currentFloor)
					goal.draw(w, h, e.gc);
				if (isHinted) 
					hint.draw(w,h, e.gc);
				
			}
		};
		
		this.addPaintListener(ps);
	}
	public MazeDisplay(Composite parent, int style, Label lblCurrentFloor) {
		super(parent, style);
		this.setBackgroundImage(new Image(null, "images/goal.png"));
		
		character = new Character();
		goal = new Goal();
		hint = new Hint();
		this.lblCurrentFloor = lblCurrentFloor;
		goal.setPos(new Position(0,1,1));
		character.setPos(new Position(0,1,1));
		this.addKeyListener(new KeyListener() {
			
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
		});
	}
	
	public int getCellType(Position pos) {
		int mode=0;
		if (!(pos.x >= floors -1))
			if(maze.getValue(pos.x+1, pos.y, pos.z) == 0)
				mode+=2;
		if (!(pos.x <= 0))
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
			//this.setVisible(false);	
			this.removePaintListener(ps);
			this.setBackgroundImage(new Image(null,"images/character.png"));
		}
	}
	
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
	
	public PaintListener getPs() {
		return ps;
	}

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
}

