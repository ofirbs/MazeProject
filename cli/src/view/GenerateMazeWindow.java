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
				MessageBox msg = new MessageBox(shell, SWT.OK);
				//msg.setText("Title");
				//msg.setMessage("Button was clicked");
				String name = txtName.getText();
				int floors = Integer.parseInt(txtFloors.getText());
				int rows = Integer.parseInt(txtRows.getText());
				int cols = Integer.parseInt(txtCols.getText());
				
				mazeWindow.update("generate_3d_maze "+name+" "+floors+" "+rows+" "+cols);
				//msg.setMessage("Generated maze " + name + " with floors: " + floors + " rows: " + rows + " cols: " + cols);
				mazeWindow.update("display "+name);
				//msg.open();
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {			
				
			}
		});	
		
	}

}
