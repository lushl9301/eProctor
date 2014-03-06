package entity;

import java.awt.Font;

import javax.swing.*;

public class Main {

    public static entity.User currentUser;
    public static client_side.Client client;
    public static int port1 = 6001;
    public static int port2 = 6002;
    
    //declare UI class and Controller class
    public static LoginUI loginUI;
    public static LoginController loginController;
    public static student_side.StudentHomeUI studentHomeUI;
    public static student_side.StudentHomeController studentHomeController;
    public static proctor_side.ProctorHomeUI proctorHomeUI;
    public static proctor_side.ProctorHomeController proctorHomeController;
    public static proctor_side.CoordinatorHomeUI coordinatorHomeUI;
    public static proctor_side.CoordinatorHomeController coordinatorHomeController;
    
    public static entity.About about;

    public static void main(String[] args) throws ClassNotFoundException,
                                                InstantiationException, IllegalAccessException,
                                                UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("ComboBox.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 14));
        loginUI = new LoginUI();
        loginController = new LoginController();
        loginUI.setVisible(true);

        client = new client_side.Client();

    }
}
