package entity;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import java.awt.Color;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class DesktopUI extends JFrame {

	private JPanel contentPane;
	public JDesktopPane desktopPane;
	public DesktopController controller;

	public DesktopUI(DesktopController controller) throws Exception {
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = graphicsEnvironment.getScreenDevices();
		GraphicsDevice graphicsDevice = devices[0];
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setAutoRequestFocus(true);
		setUndecorated(true);
		setResizable(false);
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.controller = controller;

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmLogin = new JMenuItem("Login");
		mnFile.add(mntmLogin);

		JMenuItem mntmLogout = new JMenuItem("Logout");
		mnFile.add(mntmLogout);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		desktopPane = new JDesktopPane();
		desktopPane.setBorder(null);
		desktopPane.setBackground(Color.LIGHT_GRAY);
		contentPane.add(desktopPane, BorderLayout.CENTER);

		graphicsDevice.setFullScreenWindow(this);
		validate();
		
		controller.showLogin();
		
		Main.loginController = new entity.LoginController();
		Main.loginUI = new entity.LoginUI(Main.loginController);
		desktopPane.add(Main.loginUI);
		Main.loginUI.setVisible(true);
	}

	// public void showStudentHome() {
	// student_side.StudentHomeController controller = new
	// student_side.StudentHomeController("gong0025");
	// student_side.StudentHomeUI home = new
	// student_side.StudentHomeUI(controller);
	// home.setBounds(0, 0, screenSize.width - 10, screenSize.height - 50);
	// desktopPane.add(home);
	// home.setVisible(true);
	// }
}
