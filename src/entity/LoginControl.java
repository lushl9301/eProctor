package entity;

import java.awt.EventQueue;
import java.util.Random;

import javax.swing.UIManager;

public class LoginControl {
	public LoginControl() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					LoginUI window = new LoginUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static boolean verifyUser() {
		Random rand = new Random();
		return rand.nextBoolean();
	}
}
