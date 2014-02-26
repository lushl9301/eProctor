package entity;

import javax.swing.JFrame;
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

public class LoginUI {

	JFrame frame;
	private JTextField textFieldUsername;
	private JPasswordField passwordField;
	private JComboBox cbxDomain;
	private String[] domainStrings = { "Student", "Proctor" };
	private JLabel lblErrormessage;
	
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
		
		setTextFieldUsername(new JTextField());
		getTextFieldUsername().setBounds(100, 70, 150, 20);
		frame.getContentPane().add(getTextFieldUsername());
		getTextFieldUsername().setColumns(10);
		
		setPasswordField(new JPasswordField());
		getPasswordField().setBounds(100, 120, 150, 20);
		frame.getContentPane().add(getPasswordField());
		
		final JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Main.loginController.verifyUser();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnLogin.setBounds(140, 192, 70, 23);
		frame.getContentPane().add(btnLogin);
		
		setCbxDomain(new JComboBox(domainStrings));
		getCbxDomain().setBounds(100, 170, 150, 20);
		frame.getContentPane().add(getCbxDomain());
		
		setLblErrormessage(new JLabel(""));
		getLblErrormessage().setForeground(Color.RED);
		getLblErrormessage().setBounds(100, 230, 150, 14);
		frame.getContentPane().add(getLblErrormessage());
	}

	public JPasswordField getPasswordField() {
		return passwordField;
	}

	public void setPasswordField(JPasswordField passwordField) {
		this.passwordField = passwordField;
	}

	public JTextField getTextFieldUsername() {
		return textFieldUsername;
	}

	public void setTextFieldUsername(JTextField textFieldUsername) {
		this.textFieldUsername = textFieldUsername;
	}

	public JComboBox getCbxDomain() {
		return cbxDomain;
	}

	public void setCbxDomain(JComboBox cbxDomain) {
		this.cbxDomain = cbxDomain;
	}

	public JLabel getLblErrormessage() {
		return lblErrormessage;
	}

	public void setLblErrormessage(JLabel lblErrormessage) {
		this.lblErrormessage = lblErrormessage;
	}
}
