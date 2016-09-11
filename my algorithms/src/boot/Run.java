package boot;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.LastNeighborChooser;
import algorithms.mazeGenerators.Maze3d;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;


public class Run {

	public static void main(String[] args) {
		
		//Demo dm = new Demo();
		//dm.run();
		
		Maze3d maze = new GrowingTreeGenerator(new LastNeighborChooser()).generate(4,4,4);
		
		try {
			OutputStream out = new MyCompressorOutputStream(new FileOutputStream("1.maz"));
			out.write(maze.toByteArray());
			out.flush();
			out.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			InputStream in = new MyDecompressorInputStream(new FileInputStream("1.maz"));
			byte b[] = new byte[maze.toByteArray().length];
			in.read(b);
			in.close();
			
			Maze3d loaded = new Maze3d(b);
			System.out.println("loaded");
			System.out.println(loaded);
			System.out.println("#################");
			System.out.println("original");
			System.out.println(maze);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
