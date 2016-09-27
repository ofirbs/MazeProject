package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * <h1> The DialogWindow Abstract Class</h1>
 * This class implements the main start function of any dialog window.<br>
 * @author ofir and rom
 *
 */
public abstract class DialogWindow {
	protected Shell shell;	
	
	protected abstract void initWidgets();
	
	public void start(Display display) {		
		shell = new Shell(display);
		
		initWidgets();
		shell.open();		
	}
}
