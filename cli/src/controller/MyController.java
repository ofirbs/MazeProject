package controller;

import model.Model;
import view.View;


/**
 * <h1> the MyController Class</h1>
 * Creates an instance of the Controller interface
 * @author ofir and rom
 *
 */
public class MyController implements Controller {
	private View view;
	private Model model;
	
	
	/**
	 * MyController constructor, creates a controller to interact with the view and model
	 * @param view
	 * @param model
	 */
	public MyController(View view, Model model) {
		super();
		this.view = view;
		this.model = model;
	}
	
	/**
	 * This method notifies the view that the maze is ready
	 */
	@Override
	public void notifyMazeIsReady(String name) {
		view.notifyMazeIsReady(name);
	}

	/**
	 * This method sends a notification string to the view
	 */
	@Override
	public void notify(String string) {
		view.notify(string);
	}
}
