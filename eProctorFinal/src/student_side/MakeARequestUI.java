package entity;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MakeARequestUI extends JInternalFrame {

	private JPanel contentPane;
	private JTextField txtCourseName;
	private JTextField txtSession;
	private JTextField txtCourseCode;
	private JTextField textStartTime;
	private JTextField textEndTime;
	private JTextField txtLocation;
	private JTextArea FYI;
	private JLabel lblRecordid;
	private JLabel lblProctorName;

	/**
	 * Create the frame.
	 */
	public MakeARequestUI(final entity.MakeARequestController controller) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.deleteRecord();
				setVisible(false);
			}
		});
		btnDelete.setBounds(410, 232, 100, 25);
		contentPane.add(btnDelete);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});
		btnCancel.setBounds(524, 232, 100, 25);
		contentPane.add(btnCancel);
		
		txtCourseCode = new JTextField();
		txtCourseCode.setEditable(false);
		txtCourseCode.setText(controller.getCourseCode());
		txtCourseCode.setBounds(97, 49, 200, 25);
		contentPane.add(txtCourseCode);
		txtCourseCode.setColumns(10);
		
		txtCourseName = new JTextField();
		txtCourseName.setEditable(false);
		txtCourseName.setText(controller.getCourseName());
		txtCourseName.setBounds(97, 85, 200, 25);
		contentPane.add(txtCourseName);
		txtCourseName.setColumns(10);
		
		txtSession = new JTextField();
		txtSession.setEditable(false);
		txtSession.setText(controller.getSessionId());
		txtSession.setBounds(97, 119, 200, 25);
		contentPane.add(txtSession);
		txtSession.setColumns(10);
		
		txtLocation = new JTextField();
		txtLocation.setEditable(false);
		txtLocation.setText(controller.getLocation());
		txtLocation.setBounds(424, 50, 200, 25);
		contentPane.add(txtLocation);
		txtLocation.setColumns(10);
		
		textStartTime = new JTextField();
		textStartTime.setEditable(false);
		textStartTime.setText(controller.getStartTime());
		textStartTime.setBounds(424, 85, 200, 25);
		contentPane.add(textStartTime);
		textStartTime.setColumns(10);
		
		textEndTime = new JTextField();
		textEndTime.setEditable(false);
		textEndTime.setText(controller.getEndTime());
		textEndTime.setBounds(424, 120, 200, 25);
		contentPane.add(textEndTime);
		textEndTime.setColumns(10);
		
		JLabel lblCourseCode = new JLabel("Code");
		lblCourseCode.setBounds(30, 50, 70, 22);
		contentPane.add(lblCourseCode);
		
		JLabel lblCourseName = new JLabel("Course");
		lblCourseName.setBounds(30, 84, 70, 22);
		contentPane.add(lblCourseName);
		
		JLabel lblSession = new JLabel("Session");
		lblSession.setBounds(30, 118, 70, 22);
		contentPane.add(lblSession);
	
		JLabel lblLocation = new JLabel("Location");
		lblLocation.setBounds(344, 50, 70, 22);
		contentPane.add(lblLocation);
		
		JLabel labelStartTime = new JLabel("Start Time");
		labelStartTime.setBounds(344, 85, 70, 22);
		contentPane.add(labelStartTime);
		
		JLabel lblEndTime = new JLabel("End Time");
		lblEndTime.setBounds(344, 120, 70, 22);
		contentPane.add(lblEndTime);	
		
		FYI = new JTextArea();
		FYI.setLineWrap(true);
		FYI.setEditable(false);
		FYI.setText("*If any updates of booked exam session is needed,please delete current record before making new booking.");
		FYI.setBounds(30, 160, 594, 60);
		contentPane.add(FYI);
		
		lblRecordid = new JLabel("Exam Record: " + controller.getRecordId());
		lblRecordid.setBounds(30, 12, 300, 15);
		contentPane.add(lblRecordid);
		
		lblProctorName = new JLabel("Proctor: " + controller.getProctorId());
		lblProctorName.setBounds(344, 12, 150, 15);
		contentPane.add(lblProctorName);
	}
}
