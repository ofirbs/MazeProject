package view;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;

import algorithms.mazeGenerators.Position;

public class MazeDisplay extends Canvas {
	
	private int[][] mazeData;
	private int rows;
	private int cols;
	private Character character;
	private Goal goal;

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
	
	public MazeDisplay(Composite parent, int style) {
		super(parent, style);
		mazeData = new int[][] {{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				   {1,0,0,1,1,1,1,1,1,1,1,1,1,1,1},
				   {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				   {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				   {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				   {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				   {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				   {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				   {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				   {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}  
				 };
		character = new Character();
		goal = new Goal();
		goal.setPos(new Position(0,1,1));
		character.setPos(new Position(0,1,1));
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				Position pos = character.getPos();
				switch (e.keyCode) {
				case SWT.ARROW_LEFT:					
					character.setImg(new Image(null,"images/characterL.png"));
					redraw();
					if (pos.z <= 0)
						break;
					if(character.checkCollision(mazeData[pos.y][pos.z-1]))
						break;
					character.moveLeft();
					checkWon();
					redraw();
					break;
				
				case SWT.ARROW_RIGHT:
					character.setImg(new Image(null,"images/character.png"));
					redraw();
					if (pos.z >= cols)
						break;
					if(character.checkCollision(mazeData[pos.y][pos.z+1]))
						break;
					character.moveRight();
					checkWon();
					redraw();
					break;
					
				case SWT.ARROW_UP:					
					character.setImg(new Image(null,"images/characterU.png"));
					redraw();
					if (pos.y <= 0)
						break;
					if(character.checkCollision(mazeData[pos.y-1][pos.z]))
						break;
					character.moveUp();
					checkWon();
					redraw();
					break;
					
				case SWT.ARROW_DOWN:					
					character.setImg(new Image(null,"images/characterD.png"));
					redraw();
					if (pos.y >= rows)
						break;
					if(character.checkCollision(mazeData[pos.y+1][pos.z]))
						break;
					character.moveDown();
					checkWon();
					redraw();
					break;
				}
			}
		});
		
		this.addPaintListener(new PaintListener() {
			
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
				       if(mazeData[i][j]!=0)
				           e.gc.fillRectangle(x,y,w,h);
				   }
				   
				 
				character.draw(w, h, e.gc);
				goal.draw(w, h, e.gc);
				
			}
		});
		
		/*TimerTask task = new TimerTask() {
			
			@Override
			public void run() {	
				getDisplay().syncExec(new Runnable() {					

					@Override
					public void run() {
						
						character.moveRight();
						redraw();
					}
				});
				
			}
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 500);*/
	}
	public void setMaze2d(int[][] maze2d) {
		this.mazeData = maze2d;
		this.rows = maze2d.length;
		this.cols = maze2d[0].length;
	}
	
	public void checkWon() {
		if (character.getPos().equals(goal.getPos())) {
			this.setVisible(false);			
		}
	}
}

