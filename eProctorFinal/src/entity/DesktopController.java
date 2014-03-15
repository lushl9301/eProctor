package entity;

import java.awt.Component;

public class DesktopController {
	public DesktopController() {
	}

	public void showLogin() {
//		Main.loginController = new entity.LoginController();
//		Main.loginUI = new entity.LoginUI(Main.loginController);
//		Main.loginUI.setBounds(0, 0, 350, 300);
//		Main.desktopUI.desktopPane.add(Main.loginUI);
//		Main.loginUI.setVisible(true);
	}

	public void addComponent(Component comp) {
		Main.desktopUI.desktopPane.add(comp);
		Main.desktopUI.repaint();
	}
	
	public void removeComponent(Component comp) {
		Main.desktopUI.desktopPane.remove(comp);
		Main.desktopUI.repaint();
	}
}
