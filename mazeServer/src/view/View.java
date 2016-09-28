package view;

import model.MyTcpIpServer;
/**
 * <h1> The View interface</h1>
 * This is the interface of the view object. <br>
 * The view object interacts with the user.
 * @author ofir and rom
 *
 */
public interface View {
	public MyTcpIpServer getMyTcpIpServer();
	public void setMyTcpIpServer(MyTcpIpServer myTcpIpServer);
	public void addConnection();
	public void addLog(String log);
}
