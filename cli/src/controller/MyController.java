package controller;

import model.Model;
import view.View;

public class MyController implements Controller {
	private View view;
	private Model model;
	private CommandsManager commandsManager;
	
	
	
	public MyController(View view, Model model) {
		super();
		this.view = view;
		this.model = model;
		commandsManager = new CommandsManager(model, view);
		view.setCommands(commandsManager.getCommandsMap());
	}

	@Override
	public void notifyMazeIsReady(String name) {
		view.notifyMazeIsReady(name);
	}
}
