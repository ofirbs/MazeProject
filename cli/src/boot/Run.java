package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import model.MyModel;
import presenter.Presenter;
import properties.PropertiesLoader;
import view.MazeWindow;
import view.MyView;

public class Run {

	public static void main(String[] args) {
		
		//Initialize Properties
		String path = "resources/properties.xml";
		PropertiesLoader loader = new PropertiesLoader(path); 

		int numOfThreads = loader.getProperties().getNumOfThreads(); // integer
		String generateMazeAlgorithm = loader.getProperties().getGenerateMazeAlgorithm(); // GrowingTreeGenerator or SimpleMaze3dGenerator
		String typeOfUI = loader.getProperties().getTypeOfUI(); // console or GUI
		String solveMazeAlgorithm = loader.getProperties().getSolveMazeAlgorithm();	//BFS or DFS
		String saveMethod = loader.getProperties().getSaveMethod(); //ZIP or SQL

		
		//Readers & Writers
		InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader in = new BufferedReader(isr);
		
        OutputStreamWriter osw = new OutputStreamWriter(System.out);
        PrintWriter out = new PrintWriter(osw);
        
        //initialize model with properties
		MyModel model = new MyModel(numOfThreads, generateMazeAlgorithm, solveMazeAlgorithm,saveMethod);
		

		switch (typeOfUI) {
		case "GUI":
			MazeWindow viewGUI= new MazeWindow();
			Presenter presenterGUI = new Presenter(model, viewGUI);
			model.addObserver(presenterGUI);
			viewGUI.addObserver(presenterGUI);
			viewGUI.start();
			break;

		case "console":
			MyView viewCLI = new MyView(in, out);
			Presenter presenter = new Presenter(model, viewCLI);
			model.addObserver(presenter);
			viewCLI.addObserver(presenter);
			viewCLI.start();
			break;
		}
	}
}
