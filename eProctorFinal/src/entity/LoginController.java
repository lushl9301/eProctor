package entity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.mongodb.*;

import java.util.Arrays;

import org.bson.types.ObjectId;

public class LoginController {
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public LoginController() {

	}

	public boolean isUser(String username, char[] password, String domain)
			throws Exception {
		String MD5Password = LoginController.getMD5FromCharArray(password,
				false);
		QueryBuilder qb = new QueryBuilder();
		qb.put("username").is(username).put("password")
				.is((String) getMD5FromCharArray(password, true))
				.put("domain").is(domain);
		System.out.println(getMD5FromCharArray(password, true));
		DBObject obj = Main.validationServer.user.findOne(qb.get());
		if (obj == null)
			return false;
		else {
			qb = new QueryBuilder();
			qb.put("username").is(username);
			obj = Main.mongoHQ.student.findOne(qb.get());
			Main.user_id = (ObjectId) obj.get("_id");
			return true;
		}
	}

	public static String getMD5FromCharArray(char[] cInput, boolean getHex)
			throws NoSuchAlgorithmException {
		String sInput = String.valueOf(cInput);
		MessageDigest md;
		byte[] bytesOfMessage = sInput.getBytes(StandardCharsets.UTF_8);
		md = MessageDigest.getInstance("MD5");
		byte[] thedigest = md.digest(bytesOfMessage);
		if (!getHex)
			return new String(thedigest, StandardCharsets.UTF_8);
		else
			return LoginController.bytesToHex(thedigest);
	}

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}

	public String getErrorMessage() {
		return "Login error!";
	}

	public void showHomeUI(String domain) throws Exception {
		Main.desktopController.removeComponent(Main.loginUI);
		Main.loginUI = null;
		if ("Student".equals(domain)) {
			Main.studentHomeController = new student_side.StudentHomeController();
			Main.studentHomeUI = new student_side.StudentHomeUI(
					Main.studentHomeController);
			Main.studentHomeController.open();
		} else if ("Proctor".equals(domain)) {
			Main.proctorHomeController = new proctor_side.ProctorHomeController();
			Main.proctorHomeUI = new proctor_side.ProctorHomeUI(
					Main.proctorHomeController);
			Main.proctorHomeController.open();
		} else {
			System.exit(0);
		}
	}
}
