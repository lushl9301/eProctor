package student_side;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import entity.Main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MakeARequestUI extends JFrame {

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
	public MakeARequestUI(final student_side.MakeARequestController controller) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.deleteRecord();
			}
		});
		btnDelete.setBounds(152, 232, 157, 25);
		contentPane.add(btnDelete);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.cancel();
				setVisible(false);
			}
		});
		btnCancel.setBounds(321, 232, 117, 25);
		contentPane.add(btnCancel);
		
		txtCourseCode = new JTextField();
		txtCourseCode.setEditable(false);
		txtCourseCode.setText(controller.getCourseCode());
		txtCourseCode.setBounds(97, 50, 99, 22);
		contentPane.add(txtCourseCode);
		txtCourseCode.setColumns(10);
		
		txtCourseName = new JTextField();
		txtCourseName.setEditable(false);
		txtCourseName.setText(controller.getCourseName());
		txtCourseName.setBounds(97, 85, 99, 22);
		contentPane.add(txtCourseName);
		txtCourseName.setColumns(10);
		
		txtSession = new JTextField();
		txtSession.setEditable(false);
		txtSession.setText(controller.getSessionId());
		txtSession.setBounds(97, 120, 99, 22);
		contentPane.add(txtSession);
		txtSession.setColumns(10);
		
		txtLocation = new JTextField();
		txtLocation.setEditable(false);
		txtLocation.setText(controller.getLocation());
		txtLocation.setBounds(288, 50, 150, 22);
		contentPane.add(txtLocation);
		txtLocation.setColumns(10);
		
		textStartTime = new JTextField();
		textStartTime.setEditable(false);
		textStartTime.setText(controller.getStartTime());
		textStartTime.setBounds(288, 85, 150, 22);
		contentPane.add(textStartTime);
		textStartTime.setColumns(10);
		
		textEndTime = new JTextField();
		textEndTime.setEditable(false);
		textEndTime.setText(controller.getEndTime());
		textEndTime.setBounds(288, 120, 150, 22);
		contentPane.add(textEndTime);
		textEndTime.setColumns(10);
		
		JLabel lblCourseCode = new JLabel("Code");
		lblCourseCode.setBounds(30, 50, 105, 15);
		contentPane.add(lblCourseCode);
		
		JLabel lblCourseName = new JLabel("Course");
		lblCourseName.setBounds(30, 85, 105, 15);
		contentPane.add(lblCourseName);
		
		JLabel lblSession = new JLabel("Session");
		lblSession.setBounds(30, 120, 105, 15);
		contentPane.add(lblSession);
	
		JLabel lblLocation = new JLabel("Location");
		lblLocation.setBounds(208, 50, 70, 15);
		contentPane.add(lblLocation);
		
		JLabel labelStartTime = new JLabel("StartTime");
		labelStartTime.setBounds(208, 85, 70, 15);
		contentPane.add(labelStartTime);
		
		JLabel lblEndTime = new JLabel("End Time");
		lblEndTime.setBounds(208, 120, 70, 15);
		contentPane.add(lblEndTime);	
		
		FYI = new JTextArea();
		FYI.setLineWrap(true);
		FYI.setEditable(false);
		FYI.setText("*If any updates of booked exam session is needed,please delete current record before making new booking.");
		FYI.setBounds(68, 160, 370, 60);
		contentPane.add(FYI);
		
		lblRecordid = new JLabel("Exam Record: " + controller.getRecordId());
		lblRecordid.setBounds(28, 12, 176, 15);
		contentPane.add(lblRecordid);
		
		lblProctorName = new JLabel("Proctor: " + controller.getProctorId());
		lblProctorName.setBounds(224, 12, 139, 15);
		contentPane.add(lblProctorName);
	}
}
