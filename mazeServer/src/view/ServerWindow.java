package view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;

import model.MyTcpIpServer;
/**
 * <h1> The ServerWindow Class</h1>
 * The main window which hold the servers logs, counter and button.<br>
 * @author ofir and rom
 *
 */
public class ServerWindow extends BaseWindow implements View{

	private MyTcpIpServer myTcpIpServer;
	private int noConnections=0;
	private Label lblConnections;
	private List logList;
	private int noLogs=0;

	public MyTcpIpServer getMyTcpIpServer() {
		return myTcpIpServer;
	}

	public void setMyTcpIpServer(MyTcpIpServer myTcpIpServer) {
		this.myTcpIpServer = myTcpIpServer;
	}
	
	public void addConnection() {
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				noConnections++;
				lblConnections.setText("Total Connections: " + noConnections );
			}
		});
	}
	
	public void addLog(String log) {
		
		Display.getDefault().asyncExec(new Runnable() {
			
			@Override
			public void run() {
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				Date date = new Date();
				noLogs++;
				logList.add(dateFormat.format(date) + " - " + log);
				logList.setSelection(noLogs-1);
			}
		});
	}
	
	@Override
	protected void initWidgets() {
		
		GridLayout grid = new GridLayout(1, false);
		shell.setLayout(grid);
		
		
		Composite log = new Composite(shell, SWT.NONE);
		log.setLayout(grid);
		log.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		Composite buttons = new Composite(shell, SWT.NONE);
		buttons.setLayout(grid);
		buttons.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true));
		
		Composite labels = new Composite(shell, SWT.NONE);
		labels.setLayout(grid);
		log.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		GridLayout buttonsGrid = new GridLayout(1,true);
		
		buttonsGrid.marginWidth = 0;
		
		Label lblLog = new Label(log, SWT.NONE);
		lblLog.setText("Servers Log:" );

		logList = new List(log, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
		logList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		buttons.setLayout(buttonsGrid);
		
		
		lblConnections = new Label(labels, SWT.NONE);
		lblConnections.setText("Total Connections: " + noConnections );

		Button btnExit = new Button(buttons, SWT.PUSH | SWT.CENTER);
		btnExit.setText("Close Server");
		
		//Exit button functionality
		btnExit.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				MessageBox msg = new MessageBox(shell,SWT.YES | SWT.NO);
				msg.setMessage("Are you sure?");
				msg.setText("Close Server");
				int response = msg.open();
				if(response == SWT.YES){
					myTcpIpServer.closerServer();
					shell.dispose();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		
		//Close button functionality(top right red X)
		shell.addListener(SWT.Close, new Listener(){
	        public void handleEvent(Event event)
	        {
	        	myTcpIpServer.closerServer();
				shell.dispose();
	        }
	    });
		myTcpIpServer.startServer();
	}
}
