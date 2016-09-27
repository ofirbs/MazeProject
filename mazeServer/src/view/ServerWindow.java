package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

public class ServerWindow extends BaseWindow implements View{

	@Override
	protected void initWidgets() {
		
		GridLayout grid = new GridLayout(1, false);
		shell.setLayout(grid);
		
		
		Composite clients = new Composite(shell, SWT.NONE);
		clients.setLayout(grid);
		clients.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Composite buttons = new Composite(shell, SWT.NONE);
		buttons.setLayout(grid);
		buttons.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		
		GridLayout buttonsGrid = new GridLayout(3,true);
		
		buttonsGrid.marginWidth = 0;
		
		List clientList = new List(clients, SWT.BORDER);
		clientList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		buttons.setLayout(buttonsGrid);

		clientList.add("bla");
		clientList.add("blablablabla");
		clientList.add("blablablabla");
		clientList.add("blblaa");
		Button btnKillSession = new Button(buttons, SWT.PUSH | SWT.CENTER);
		btnKillSession.setText("kill session");
		
		//Generate Button Functionality
		btnKillSession.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		Button btnKillAll = new Button(buttons, SWT.PUSH | SWT.CENTER);
		btnKillAll.setText("kill all");
		
		//Generate Button Functionality
		btnKillAll.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		Button btnExit = new Button(buttons, SWT.PUSH | SWT.CENTER);
		btnExit.setText("exit");
		
		//Generate Button Functionality
		btnExit.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
	}

}
