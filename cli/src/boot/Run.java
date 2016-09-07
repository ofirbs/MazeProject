package boot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import controller.Command;
import view.CLI;

public class Run {

	public static void main(String[] args) {
		HashMap<String, Command> commands = new HashMap<String, Command>(); 
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
		cli.start();
	}
}
