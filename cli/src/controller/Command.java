package controller;

/**
 * <h1> The Command interface</h1>
 * This is the interface of the command object.
 * Every class implementing this interface must implement the following function:<br>
 * <i> void doCommand(String[] args) </i>
 * @author ofir and rom
 *
 */
public interface Command {
	void doCommand(String[] args);
}
