package view;

import java.util.Observable;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
/**
 * <h1> The BaseWindow Abstract Class</h1>
 * This class implements the basic functionality for any window.<br>
 * It starts that dispatches the GUI jobs.
 * @author ofir and rom
 *
 */
public abstract class BaseWindow extends Observable {
	protected Display display;
	protected Shell shell;	
	
	protected abstract void initWidgets();
	
	public void start() {
		display = new Display();
		shell = new Shell(display);
		shell.setSize(700, 500);
		
		initWidgets();
		shell.open();		
		
		// main event loop
		while(!shell.isDisposed()){ // window isn't closed
			if(!display.readAndDispatch()){
				display.sleep();
			}
		}
		display.dispose();
	}
}
