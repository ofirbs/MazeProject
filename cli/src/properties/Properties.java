package properties;
import java.io.Serializable;

/**
 * <h1> The Properties Class</h1>
 * This class represents the properties file.<br>
 * it hold all of the information needed for the client.
 * @author ofir and rom
 *
 */
public class Properties implements Serializable {
	private static final long serialVersionUID = 1L;
	private int numOfThreads;
	private String generateMazeAlgorithm;
	private String solveMazeAlgorithm;
	private String typeOfUI;
	private String saveMethod;
			
	public int getNumOfThreads() {
		return numOfThreads;
	}
	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}
	public String getGenerateMazeAlgorithm() {
		return generateMazeAlgorithm;
	}
	public void setGenerateMazeAlgorithm(String generateMazeAlgorithm) {
		this.generateMazeAlgorithm = generateMazeAlgorithm;
	}
	public String getSolveMazeAlgorithm() {
		return solveMazeAlgorithm;
	}
	public void setSolveMazeAlgorithm(String solveMazeAlgorithm) {
		this.solveMazeAlgorithm = solveMazeAlgorithm;
	}
	public String getTypeOfUI() {
		return typeOfUI;
	}
	public void setTypeOfUI(String typeOfUI) {
		this.typeOfUI = typeOfUI;
	}
	public String getSaveMethod() {
		return saveMethod;
	}
	public void setSaveMethod(String saveMethod) {
		this.saveMethod = saveMethod;
	}
}
