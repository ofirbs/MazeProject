package controller;

/**
 * <h1> The Controller interface</h1>
 * This is the interface of the controller object.
 * Every class implementing this interface must implement the following functions: <br>
 * <i> void notifyMazeIsReady(String name) <br> </i>
 * <i> void notify(String string) </i>
 * @author ofir and rom
 *
 */
public interface Controller {
	void notifyMazeIsReady(String name);

	void notify(String string);
}
