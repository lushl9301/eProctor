package entity;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginUI extends JInternalFrame {

	public entity.LoginController controller;
	private JTextField textFieldUsername;
	private JPasswordField passwordField;
	private JComboBox cbxDomain;
	private String[] domainStrings = { "Student", "Proctor" };
	private JLabel lblErrorMessage;

	public LoginUI(entity.LoginController controller) {
		this.controller = controller;
		initialize();
	}

	private void initialize() {
		setResizable(false);
		setBounds(500, 100, 350, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Welcome to eProctor");
		getContentPane().setLayout(null);

		JLabel lblUsername = new JLabel("Username:");
		lblUsername.setBounds(100, 50, 150, 14);
		getContentPane().add(lblUsername);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(100, 100, 150, 14);
		getContentPane().add(lblPassword);

		JLabel lblDomain = new JLabel("Domain:");
		lblDomain.setBounds(100, 150, 150, 14);
		getContentPane().add(lblDomain);

		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(100, 70, 150, 24);
		getContentPane().add(textFieldUsername);
		textFieldUsername.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(100, 120, 150, 24);
		getContentPane().add(passwordField);

		final JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (controller.isUser(textFieldUsername.getText(),
							passwordField.getPassword(),
							(String) cbxDomain.getSelectedItem())) {
						controller.showHomeUI((String) cbxDomain.getSelectedItem());
					} else {
						lblErrorMessage.setText(controller.getErrorMessage());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(140, 201, 70, 30);
		getContentPane().add(btnLogin);

		cbxDomain = new JComboBox(domainStrings);
		cbxDomain.setBounds(100, 170, 150, 24);
		getContentPane().add(cbxDomain);
		
		lblErrorMessage = new JLabel("");
		lblErrorMessage.setForeground(Color.RED);
		lblErrorMessage.setBounds(100, 230, 150, 18);
		getContentPane().add(lblErrorMessage);
	}
}