package entity;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
	public static server_side.Server server;
	public static LoginController loginController;
	public static LoginUI loginUI;
	
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		loginUI = new LoginUI();
		loginController = new LoginController();
		server = new server_side.Server();
	}
}
