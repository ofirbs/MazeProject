package algorithms.mazeGenerators;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <h1> the Maze3d Class</h1>
 * This class holds the actual maze information and basic maze functions.
 * @author ofir and rom
 *
 */

public class Maze3d implements Serializable{
	private int [][][] maze;
	private int floors;
	private int rows;
	private int cols;
	private Position startPosition;
	private Position goalPosition;
	
	public static final int FREE = 0;
	public static final int WALL = 1;
	
	/**
	 * This method is the constructor. it creates a 3d array by the given parameters. 
	 * @param floors
	 * @param rows
	 * @param cols
	 */
	public Maze3d(int floors, int rows, int cols){
		this.floors = floors;
		this.rows = rows;
		this.cols = cols;
		maze = new int [floors][rows][cols];		
	}
	/**
	 * This method is the second constructor. it creates a 3d array by array of bytes given. 
	 * @param byte[] byte array
	 */
	public Maze3d(byte[] arr) {
		int k = 0;
		this.floors = arr[k++];
		this.rows = arr[k++];
		this.cols = arr[k++];
		
		maze = new int[floors][rows][cols];		
		
		Position startPos = new Position(arr[k++], arr[k++], arr[k++]);
		this.setStartPosition(startPos);
		Position goalPos = new Position(arr[k++], arr[k++], arr[k++]);
		this.setGoalPosition(goalPos);
		
		for (int x = 0; x < floors; x++) {
			for (int y = 0; y < rows; y++) {
				for (int z = 0; z < cols ; z++) {
					maze[x][y][z] = arr[k++];
				}
			}			
		}
	}
	
	public int[][][] getMaze() {
		return maze;
	}
	
	public void setWall(int x, int y, int z){
		maze[x][y][z] = WALL;
	}
	
	public void setFree(int x, int y, int z){
		maze[x][y][z] = FREE;
	}
	
	public int getValue(int x, int y, int z){
		return maze[x][y][z];
	}
	
	public Boolean isBorder(int x, int y, int z){
		if ( x == 0 || x == floors)
			return true;
		else if ( y == 0 || y == rows)
			return true;
		else if ( z == 0 || z == cols)
			return true;
		return false;
	}
	
	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public int getFloors() {
		return floors;
	}

	public Position getStartPosition() {
		return startPosition;
	}

	public Position getGoalPosition() {
		return goalPosition;
	}
	
	public void setStartPosition(Position startPosition) {
		this.startPosition = startPosition;
	}
	
	public void setGoalPosition(Position goalPosition) {
		this.goalPosition = goalPosition;
	}
	/**
	 * This method prints the maze by layers.
	 * The Start is marked with 'E'
	 * The Goal is marked with 'X'
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int x = 0; x < floors; x++)
		{
			for (int y = 0; y < rows; y++)
			{
				for(int z = 0; z < cols; z++)
				{
					if (x == startPosition.x && y == startPosition.y && z == startPosition.z)
						sb.append("E");
					else if (x == goalPosition.x && y == goalPosition.y && z == goalPosition.z)
						sb.append("X");
					else 
						sb.append(maze[x][y][z]);
				}
				sb.append("\n");
			}
			sb.append("\n");
			sb.append("\n");
		}
		return sb.toString();
	}
	
	/**
	 * This method initializes all of the positions with walls
	 */
	public void setAllWalls() {
		for(int x=0 ; x < floors; x++)
		{
			for(int y=0 ; y < rows; y++)
			{
				for(int z=0 ; z < cols; z++)
				{
					maze[x][y][z] = WALL;
				}
			}
		}
	}
	
	/**
	 * This method calculates all of possible moves for a given position
	 * @param currPos
	 * @return String[] returns a String array containing all of the possible moves(e.g Ascend, Descend, Etc...)
	 */
	public String[] getPossibleMoves(Position currPos) {
		ArrayList<String> moves = new ArrayList<String>();
		
		if(currPos.x + 1 < floors && this.getValue(currPos.x+1, currPos.y, currPos.z) == FREE)
			moves.add("Ascend");
		
		if(currPos.x - 1 >=0 && this.getValue(currPos.x-1, currPos.y, currPos.z) == FREE)
			moves.add("Descend");
		
		if(currPos.y - 1 >= 0 && this.getValue(currPos.x, currPos.y-1, currPos.z) == FREE)
			moves.add("Up");
		
		if(currPos.y + 1 < rows && this.getValue(currPos.x, currPos.y+1, currPos.z) == FREE)
			moves.add("Down");
		
		if(currPos.z + 1 < cols && this.getValue(currPos.x, currPos.y, currPos.z+1) == FREE)
			moves.add("Right");
		
		if(currPos.z - 1 >= 0 && this.getValue(currPos.x, currPos.y, currPos.z-1) == FREE)
			moves.add("Left");

		String[] movesArray = new String[moves.size()];
		movesArray = moves.toArray(movesArray);
		return movesArray;
	}
	/**
	 * This methods generates a 2d array of the cross section by x 
	 * @param section
	 * @return int[][] returns the 2d array of the section by x
	 * @throws IndexOutOfBoundsException
	 */
	public int[][] getCrossSectionByX(int section) throws IndexOutOfBoundsException{
		if (section > floors-1 || section < 0)
			throw new IndexOutOfBoundsException("Invalid floor requested.");
		int[][] maze2d = new int[rows][cols];
		for(int i = 0; i <rows;i++){
			for(int j=0 ; j < cols ; j++){
				maze2d[i][j] = this.getValue(section, i, j);
			}
		}
		return maze2d;
	}
	
	/**
	 * This methods generates a 2d array of the cross section by y 
	 * @param section
	 * @return int[][] returns the 2d array of the section by y
	 * @throws IndexOutOfBoundsException
	 */
	public int[][] getCrossSectionByY(int section) throws IndexOutOfBoundsException{
		if (section > rows -1 || section < 0)
			throw new IndexOutOfBoundsException("Invalid row requested.");
		int[][] maze2d = new int[floors][cols];
		for(int i = 0; i <floors;i++){
			for(int j=0;j<cols;j++){
				maze2d[i][j] = this.getValue(i,section, j);
			}
		}
		return maze2d;
	}
	
	/**
	 * This methods generates a 2d array of the cross section by z 
	 * @param section
	 * @return int[][] returns the 2d array of the section by z
	 * @throws IndexOutOfBoundsException
	 */
	public int[][] getCrossSectionByZ(int section) throws IndexOutOfBoundsException{
		if (section > cols -1 || section < 0)
			throw new IndexOutOfBoundsException("Invalid column requested.");
		int[][] maze2d = new int[floors][rows];
		for(int i = 0; i <floors;i++){
			for(int j=0;j<rows;j++){
				maze2d[i][j] = this.getValue(i, j, section);
			}
		}
		return maze2d;
	}
	
	/**
	 * This methods returns the byte array of the maze data 
	 * @return byte[] the byte array of the maze data
	 */
	public byte[] toByteArray() {
		ArrayList<Byte> arr = new ArrayList<Byte>();
		arr.add((byte)floors);
		arr.add((byte)rows);
		arr.add((byte)cols);
		arr.add((byte)startPosition.x);
		arr.add((byte)startPosition.y);
		arr.add((byte)startPosition.z);
		arr.add((byte)goalPosition.x);
		arr.add((byte)goalPosition.y);
		arr.add((byte)goalPosition.z);
		
		for (int x = 0; x < floors; x++) {
			for (int y = 0; y < rows; y++) {
				for ( int z = 0; z < cols; z++)
				arr.add((byte)maze[x][y][z]);
			}			
		}
		
		byte[] bytes = new byte[arr.size()];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte)arr.get(i);
		}
		return bytes;
	}
}
