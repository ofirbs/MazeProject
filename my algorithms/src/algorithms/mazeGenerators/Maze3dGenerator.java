package algorithms.mazeGenerators;
/**
 * <h1> The Maze3dGenerator interface </h1>
 * This Interface describes the methods every maze generator needs to implement
 * @author ofir and rom
 *
 */
public interface Maze3dGenerator {
	Maze3d generate(int floors, int rows, int cols);
	String measureAlgorithmTime(int floors, int rows, int cols);
}
