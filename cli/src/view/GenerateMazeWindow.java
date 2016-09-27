package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
/**
 * <h1> The GenerateMazeWindow Class</h1>
 * This class gets the necessary information to build a new maze.<br>
 * It needs : a Name, Floors, Rows, Columns
 * All fields must be filled and greater than 0.
 * @author ofir and rom
 *
 */
public class GenerateMazeWindow extends DialogWindow {
	
	private MazeWindow mazeWindow;
	
	public GenerateMazeWindow(MazeWindow mazeWindow) {
		super();
		this.mazeWindow = mazeWindow;
	}

	@Override
	protected void initWidgets() {
		shell.setText("Generate maze");
		shell.setSize(275, 175);		
				
		shell.setLayout(new GridLayout(2, false));	

		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Name: ");
		
		Text txtName = new Text(shell, SWT.BORDER);
		txtName.setLayoutData(new GridData(125,15));
		
		Label lblFloors = new Label(shell, SWT.NONE);
		lblFloors.setText("Floors: ");
		
		Text txtFloors = new Text(shell, SWT.BORDER);
		txtFloors.setLayoutData(new GridData(50,15));
		
		Label lblRows = new Label(shell, SWT.NONE);
		lblRows.setText("Rows: ");
		
		Text txtRows = new Text(shell, SWT.BORDER);
		txtRows.setLayoutData(new GridData(50,15));
		
		Label lblCols = new Label(shell, SWT.NONE);
		lblCols.setText("Cols: ");
		
		Text txtCols = new Text(shell, SWT.BORDER);
		txtCols.setLayoutData(new GridData(50,15));
				
		Button btnGenerateMaze = new Button(shell, SWT.PUSH);
		shell.setDefaultButton(btnGenerateMaze);
		btnGenerateMaze.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
		btnGenerateMaze.setText("Generate");
		
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {	
				
				//ensures all the the fields have been filled.
				String name = txtName.getText();
				MessageBox msgError = new MessageBox(shell, SWT.ICON_WARNING);
				if((name =="") || (txtFloors.getText()=="") || (txtRows.getText()=="") || (txtCols.getText()==""))
				{
					
					msgError.setMessage("All fields must be filled");
					msgError.open();
					
				}
				//ensures all the dimentions fields are greater than 0
				else if((Integer.parseInt(txtFloors.getText())<=0) || (Integer.parseInt(txtRows.getText())<=0) || (Integer.parseInt(txtCols.getText())<=0)) 
				{
					msgError.setMessage("Maze dimensions must be greater than 0");
					msgError.open();
				}
				else
				{
					int floors = Integer.parseInt(txtFloors.getText());
					int rows = Integer.parseInt(txtRows.getText());
					int cols = Integer.parseInt(txtCols.getText());
					
					mazeWindow.setIsMazeReady(false);
					//send the generate maze command to the model
					mazeWindow.update("generate_3d_maze "+name+" "+floors+" "+rows+" "+cols);
					//waits until the maze is generated
					while(!mazeWindow.getIsMazeReady()) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					mazeWindow.update("display "+name);
					mazeWindow.setMazeName(name);
					mazeWindow.setIsMazeDisplayed(true);
					shell.close();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {			
				
			}
		});	
		
	}

}
