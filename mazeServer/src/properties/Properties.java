package properties;

import java.io.Serializable;

public class Properties implements Serializable {
	/**
	 * <h1> The Properties Class</h1>
	 * This class represents the properties file.<br>
	 * it hold all of the information needed for the server.
	 * @author ofir and rom
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int numOfThreads;
	private int port;
			
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
}
