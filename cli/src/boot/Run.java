package boot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;
import controller.CommandsManager;
import controller.Controller;
import controller.MyController;
import model.MyModel;
import view.CLI;
import view.MyView;

public class Run {

	public static void main(String[] args) {
		/*HashMap<String, Command> commands = new HashMap<String, Command>(); 
		BufferedReader reader=null;
		try {
			reader = new BufferedReader(new FileReader("commands.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		PrintWriter writer=null;
		try {
			writer = new PrintWriter(new FileWriter("output.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		CLI cli = new CLI(reader,writer,commands);
		cli.start();*/
		
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
