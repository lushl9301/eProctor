package entity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.swing.JTextField;

public class LoginController {
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public LoginController() {
		Main.loginUI.frame.setVisible(true);
	}
	
	public static void verifyUser() throws Exception {
		String username = Main.loginUI.getTextFieldUsername().getText();
		String MD5Password = LoginController.getMD5FromCharArray(Main.loginUI.getPasswordField().getPassword(), false);
		String domain = (String) Main.loginUI.getCbxDomain().getSelectedItem();
		if (Main.server.verifyUser(username, MD5Password, domain)) {
			Main.loginUI.getLblErrormessage().setText("Login error! Please try again.");
		} else {
			Main.loginUI.getTextFieldUsername().setText("");
			Main.loginUI.getPasswordField().setText("");
			Main.loginUI.getLblErrormessage().setText("");
			Main.loginUI.frame.setVisible(false);
		}
	}
	
	public static String getMD5FromCharArray(char[] cInput, boolean getHex) throws NoSuchAlgorithmException {
		String sInput = String.valueOf(cInput);
		MessageDigest md;
		byte[] bytesOfMessage = sInput.getBytes(StandardCharsets.UTF_8);
		md = MessageDigest.getInstance("MD5");
		byte[] thedigest = md.digest(bytesOfMessage);
		String output;
		if (!getHex)
			return new String(thedigest, StandardCharsets.UTF_8);
		else
			return LoginController.bytesToHex(thedigest);
	}
	
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
}
