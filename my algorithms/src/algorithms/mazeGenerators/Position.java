package algorithms.mazeGenerators;

/**
 * <h1> The Position Class</h1>
 * This class contains the position information and basic functions.
 * @author ofir and rom
 *
 */
public class Position {
	public int x;
	public int y;
	public int z;
	
	public Position(int x, int y, int z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Position(Position pos) {
		this.x = pos.x;
		this.y = pos.y;
		this.z = pos.z;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + "," + z +")" ;
	}
	
	@Override
	public boolean equals(Object obj){
		Position pos = (Position)obj;
		return (this.x == pos.x && this.y == pos.y && this.z == pos.z);
	}
}
