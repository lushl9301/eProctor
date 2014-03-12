package entity;

import java.awt.Font;

import javax.swing.*;

import org.bson.types.ObjectId;

import student_side.StudentHomeUI;

public class Main {
	public static student_side.StudentHomeUI studentHomeUI;
	public static student_side.StudentHomeController studentHomeController;
	public static server_side.MongoHQ mongoHQ;
	public static ObjectId user_id;

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 14));
		UIManager.put("ComboBox.font", new Font("Segoe UI", Font.PLAIN, 14));
		UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
		UIManager.put("TextField.font", new Font("Segoe UI", Font.PLAIN, 14));
		
		user_id = new ObjectId("531ec0d07a0016ee1c000508");
		mongoHQ = new server_side.MongoHQ();
		studentHomeController = new student_side.StudentHomeController("gong0025");
	}
}
