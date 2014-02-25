package entity;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.UIManager;

public class LoginUI {

	private JFrame frame;
	private JTextField textFieldUsername;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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

	/**
	 * Create the application.
	 */
	public LoginUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(500, 100, 350, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Welcome to eProctor");
		frame.getContentPane().setLayout(null);
		
		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(100, 50, 150, 14);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(100, 100, 150, 14);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblDomain = new JLabel("Domain:");
		lblDomain.setBounds(100, 150, 150, 14);
		frame.getContentPane().add(lblDomain);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(100, 70, 150, 20);
		frame.getContentPane().add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(100, 120, 150, 20);
		frame.getContentPane().add(passwordField);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(140, 192, 70, 23);
		frame.getContentPane().add(btnLogin);
		
		String[] domainStrings = { "Student", "Proctor" };
		JComboBox cbxDomain = new JComboBox(domainStrings);
		cbxDomain.setBounds(100, 170, 150, 20);
		frame.getContentPane().add(cbxDomain);
	}
}
