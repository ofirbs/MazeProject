package view;

import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

import algorithms.mazeGenerators.Position;
/**
 * <h1> The Character Class</h1>
 * This class represents the starting position for the maze.<br>
 * it can move throughout the maze in its own. 
 * @author ofir and rom
 *
 */
public class Character {
	private Position pos;
	private Image img;
	
	public Character() {
		img = new Image(null, "images/character.png");
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}
	
	public void draw(int cellWidth, int cellHeight, GC gc) {
		gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 
				cellWidth * pos.z, cellHeight * pos.y, cellWidth, cellHeight);
	}
	
	public void moveRight() {
		pos.z++;
	}
	
	public void moveLeft() {
		pos.z--;
	}
	
	public void moveUp() {
		pos.y--;
	}
	
	public void moveAbove() {
		pos.x++;
	}
	
	public void moveBelow() {
		pos.x--;
	}
	
	public void moveDown() {
		pos.y++;
	}

	public Image getImg() {
		return img;
	}

	public void setImg(Image img) {
		this.img = img;
	}
	
	/**
	 * checks if the given position is a wall.
	 * @param pos
	 * @return true if its a wall
	 */
	public boolean checkCollision(int pos){
		if(pos!=0)
			return true;
		else
			return false;
	}


}
