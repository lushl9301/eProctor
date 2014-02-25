package entity;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginUI {

	JFrame frame;
	private JTextField textFieldUsername;
	private JPasswordField passwordField;
	
	public LoginUI() {
		initialize();
	}

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
		
		final JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String str = new String();
				str = "" + LoginControl.verifyUser();
				btnLogin.setText(str);
			}
		});
		btnLogin.setBounds(140, 192, 70, 23);
		frame.getContentPane().add(btnLogin);
		
		String[] domainStrings = { "Student", "Proctor" };
		JComboBox cbxDomain = new JComboBox(domainStrings);
		cbxDomain.setBounds(100, 170, 150, 20);
		frame.getContentPane().add(cbxDomain);
	}
}
