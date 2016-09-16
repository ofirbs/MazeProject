package boot;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import model.MyModel;
import presenter.Presenter;
import view.MazeWindow;
import view.MyView;

public class Run {

	public static void main(String[] args) {
		
		//Initialize Properties
		int numOfThreads; // integer
		String generatorType; // GrowingTreeGenerator or SimpleMaze3dGenerator
		String typeOfUI; // console or GUI
		Properties properties = new Properties();
		try {
			properties.loadFromXML(new FileInputStream("resources/properties.xml"));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		numOfThreads = Integer.parseInt(properties.getProperty("numOfThreads","10"));
		generatorType = properties.getProperty("generatorType", "GrowingTreeGenerator");
		typeOfUI = properties.getProperty("typeOfUI", "console");
		
		
		InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);
		
        OutputStreamWriter osw = new OutputStreamWriter(System.out);
        PrintWriter out = new PrintWriter(osw);

		
		MyView view = new MyView(in, out);
		MyModel model = new MyModel(numOfThreads, generatorType);
		
		Presenter presenter = new Presenter(model, view);
		model.addObserver(presenter);
		view.addObserver(presenter);
		
		switch (typeOfUI) {
		case "GUI":
			MazeWindow win = new MazeWindow();
			win.start();
			break;

		case "console":
			view.start();
			break;
		}
	}
}
