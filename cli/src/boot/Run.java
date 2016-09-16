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
import view.MyView;

public class Run {

	public static void main(String[] args) {
		int numOfThreads;
		Properties properties = new Properties();
		try {
			properties.loadFromXML(new FileInputStream("resources/properties.xml"));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			numOfThreads = 10;
		} catch (IOException e) {
			e.printStackTrace();
		}
		numOfThreads = Integer.parseInt(properties.getProperty("numOfThreads"));
		
		
		InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);
		
        OutputStreamWriter osw = new OutputStreamWriter(System.out);
        PrintWriter out = new PrintWriter(osw);

		
		MyView view = new MyView(in, out);
		MyModel model = new MyModel(numOfThreads);
		
		Presenter presenter = new Presenter(model, view);
		model.addObserver(presenter);
		view.addObserver(presenter);
				
		view.start();
	}
}
