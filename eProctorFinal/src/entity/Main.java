package entity;

import java.awt.Font;
import java.util.Date;

import javax.swing.*;

import org.bson.types.ObjectId;

import com.mongodb.DBObject;

public class Main {
	public static server_side.MongoHQ mongoHQ;
	public static server_side.ValidationServer validationServer;
	public static ObjectId user_id;
	public static String domain;
    
//	public static DBObject user;
    
	public static entity.DesktopController desktopController;
	public static entity.DesktopUI desktopUI;
	public static entity.LoginController loginController;
	public static entity.LoginUI loginUI;
	public static student_side.StudentHomeUI studentHomeUI;
	public static student_side.StudentHomeController studentHomeController;
	public static proctor_side.ProctorHomeUI proctorHomeUI;
	public static proctor_side.ProctorHomeController proctorHomeController;
	public static entity.MakeARequestUI makeARequestUI;
	public static entity.MakeARequestController makeARequestController;
	public static entity.CheckDetailsUI checkDetailsUI;
	public static entity.CheckDetailsController checkDetailsController;
    	
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 14));
		UIManager.put("ComboBox.font", new Font("Segoe UI", Font.PLAIN, 14));
		UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
		UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 14));
		UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 14));
		UIManager.put("List.font", new Font("Segoe UI", Font.PLAIN, 14));
		UIManager.put("Menu.font", new Font("Segoe UI", Font.PLAIN, 15));
		UIManager.put("MenuBar.font", new Font("Segoe UI", Font.PLAIN, 15));
		UIManager.put("MenuItem.font", new Font("Segoe UI", Font.PLAIN, 15));
		UIManager.put("TabbedPane.font", new Font("Tahoma", Font.PLAIN, 14));
		UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 14));
		UIManager.put("TextPane.font", new Font("Segoe UI", Font.PLAIN, 14));
        
        //		user_id = new ObjectId("531ec0d07a0016ee1c000508");
		mongoHQ = new server_side.MongoHQ("mongodb://admin:admin@emma.mongohq.com:10051/real_like_doc","real_like_doc");
		validationServer = new server_side.ValidationServer("mongodb://admin:admin@emma.mongohq.com:10063/NTU_Server","NTU_Server");
		
//		try {
			desktopController = new DesktopController();
			desktopUI = new DesktopUI(desktopController);
//		} catch (Exception e) {
//			new Popup(e.getClass().getCanonicalName());
//		}
        
	}
}