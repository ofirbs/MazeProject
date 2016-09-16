package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import controller.MyController;
import model.MyModel;
import presenter.CommandsManager;
import presenter.Presenter;
import view.CLI;
import view.MyView;

public class Run {

	public static void main(String[] args) {
		
		InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);
		
        OutputStreamWriter osw = new OutputStreamWriter(System.out);
        PrintWriter out = new PrintWriter(osw);
        
		/*CLI cli = new CLI(br,pr,null);
		MyModel model = new MyModel(null);
		MyView view = new MyView(null, cli);
		cli.setCommandsManager(new CommandsManager(model, view));
		MyController controller = new MyController(view, model);
		model.setController(controller);
		view.setController(controller);
		view.start();*/
		
		MyView view = new MyView(in, out);
		MyModel model = new MyModel();
		
		Presenter presenter = new Presenter(model, view);
		model.addObserver(presenter);
		view.addObserver(presenter);
				
		view.start();
	}
}
