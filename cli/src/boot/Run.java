package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import controller.CommandsManager;
import controller.MyController;
import model.MyModel;
import view.CLI;
import view.MyView;

public class Run {

	public static void main(String[] args) {
		
		InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
		
        OutputStreamWriter osw = new OutputStreamWriter(System.out);
        PrintWriter pr = new PrintWriter(osw);
        
		CLI cli = new CLI(br,pr,null);
		MyModel model = new MyModel(null);
		MyView view = new MyView(null, cli);
		cli.setCommandsManager(new CommandsManager(model, view));
		MyController controller = new MyController(view, model);
		model.setController(controller);
		view.setController(controller);
		
		view.start();
	}
}
