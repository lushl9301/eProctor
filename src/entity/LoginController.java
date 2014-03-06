package entity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginController {
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public LoginController() {

    }

    public static void verifyUser() throws Exception {
        String username = Main.loginUI.getTextFieldUsername().getText();
        String MD5Password = LoginController.getMD5FromCharArray(Main.loginUI
                .getPasswordField().getPassword(), false);
        String domain = (String) Main.loginUI.getCbxDomain().getSelectedItem();
        if (Main.client.verifyUser(username, MD5Password, domain)) {
            displayWelcome();
        } else {
            loginError();
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

    private static void loginError() {
        Main.loginUI.getLblErrormessage().setText("Login error!");
    }

    private static void displayWelcome() throws Exception {
        String domain = (String) Main.loginUI.getCbxDomain().getSelectedItem();
        Main.loginUI.getTextFieldUsername().setText("");
        Main.loginUI.getPasswordField().setText("");
        Main.loginUI.getLblErrormessage().setText("");
        Main.loginUI.setVisible(false);
        if ("Student".equals(domain)) {
            Main.studentHomeController = new student_side.StudentHomeController();
            Main.studentHomeUI = new student_side.StudentHomeUI();
            Main.studentHomeUI.setVisible(true);
        } else if ("Proctor".equals(domain)) {
            Main.proctorHomeController = new proctor_side.ProctorHomeController();
            Main.proctorHomeUI = new proctor_side.ProctorHomeUI();
            Main.proctorHomeUI.setVisible(true);
        } else if ("Coordinator".equals(domain)) {
            Main.coordinatorHomeController = new proctor_side.CoordinatorHomeController();
            Main.coordinatorHomeUI = new proctor_side.CoordinatorHomeUI();
            Main.coordinatorHomeUI.setVisible(true);
        } else {
            System.exit(0);
        }
    }
}
