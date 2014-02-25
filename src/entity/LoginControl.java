package entity;

import java.awt.EventQueue;
import java.awt.Window;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.swing.UIManager;

public class LoginControl {
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
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
	
	public static String getMD5(char[] cInput) throws NoSuchAlgorithmException {
		String sInput = String.valueOf(cInput);
		MessageDigest md;
		byte[] bytesOfMessage = sInput.getBytes(StandardCharsets.UTF_8);
		md = MessageDigest.getInstance("MD5");
		byte[] thedigest = md.digest(bytesOfMessage);
		String output = new String(thedigest, StandardCharsets.UTF_8);
		output = LoginControl.bytesToHex(thedigest);
		return output;
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
