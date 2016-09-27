package properties;

import java.io.Serializable;

public class Properties implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numOfThreads;
	private int port;
	private String solveMazeAlgorithm;
	
	public Properties() {
		
	}
			
	public int getNumOfThreads() {
		return numOfThreads;
	}
	public void setNumOfThreads(int numOfThreads) {
		this.numOfThreads = numOfThreads;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getSolveMazeAlgorithm() {
		return solveMazeAlgorithm;
	}

	public void setSolveMazeAlgorithm(String solveMazeAlgorithm) {
		this.solveMazeAlgorithm = solveMazeAlgorithm;
	}
	
}
