package entity;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Popup {
	private JFrame frame;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	/**
	 * Create the application.
	 */
	public Popup(String e) {
		initialize(e);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String e) {
		frame = new JFrame();
		frame.setBounds((screenSize.width - e.length() * 10) / 2, (screenSize.height - 100) / 2, e.length() * 10, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel message = new JLabel("");
		message.setBounds((frame.getWidth() - e.length() * 6) / 2, 0, frame.getWidth(), 50);
		message.setText(e);
		frame.getContentPane().add(message);
		
//		frame.setVisible(true);
	}
}
