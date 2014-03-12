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
public class CheckDetailsUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtCourseName;
	private JTextField txtSession;
	private JTextField txtCourseCode;
	private JTextField textRemark;
	private JTextField txtGrade;
	private JTextArea FYI;
	private JLabel lblRecordid;
	private JLabel lblProctorName;

	/**
	 * Create the frame.
	 */
	public CheckDetailsUI(final student_side.CheckDetailsController controller) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnClose = new JButton("Close");
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.cancel();
				setVisible(false);
			}
		});
		btnClose.setBounds(321, 232, 117, 25);
		contentPane.add(btnClose);
		
		txtCourseCode = new JTextField();
		txtCourseCode.setEditable(false);
		txtCourseCode.setText(controller.getCourseCode());
		txtCourseCode.setBounds(76, 50, 120, 22);
		contentPane.add(txtCourseCode);
		txtCourseCode.setColumns(10);
		
		txtCourseName = new JTextField();
		txtCourseName.setEditable(false);
		txtCourseName.setText(controller.getCourseName());
		txtCourseName.setBounds(76, 85, 120, 22);
		contentPane.add(txtCourseName);
		txtCourseName.setColumns(10);
		
		txtSession = new JTextField();
		txtSession.setEditable(false);
		txtSession.setText(controller.getSessionId());
		txtSession.setBounds(76, 120, 120, 22);
		contentPane.add(txtSession);
		txtSession.setColumns(10);
		
		txtGrade = new JTextField();
		txtGrade.setEditable(false);
		txtGrade.setText(controller.getGrade());
		txtGrade.setBounds(288, 50, 150, 22);
		contentPane.add(txtGrade);
		txtGrade.setColumns(10);
		
		textRemark = new JTextField();
		textRemark.setEditable(false);
		textRemark.setText(controller.getRemark());
		textRemark.setBounds(288, 85, 150, 22);
		contentPane.add(textRemark);
		textRemark.setColumns(10);
		
/*		textEndTime = new JTextField();
		textEndTime.setEnabled(false);
		textEndTime.setEditable(false)o;
		textEndTime.setText(controller.getEndTime());
		textEndTime.setBounds(288, 120, 150, 22);
		contentPane.add(textEndTime);
		textEndTime.setColumns(10);*/
		
		JLabel lblCourseCode = new JLabel("Code");
		lblCourseCode.setBounds(15, 50, 105, 15);
		contentPane.add(lblCourseCode);
		
		JLabel lblCourseName = new JLabel("Course");
		lblCourseName.setBounds(15, 85, 105, 15);
		contentPane.add(lblCourseName);
		
		JLabel lblSession = new JLabel("Session");
		lblSession.setBounds(15, 120, 105, 15);
		contentPane.add(lblSession);
	
		JLabel lblGrade = new JLabel("Grade");
		lblGrade.setBounds(208, 50, 70, 15);
		contentPane.add(lblGrade);
		
		JLabel labelRemark = new JLabel("Remark");
		labelRemark.setBounds(208, 85, 70, 15);
		contentPane.add(labelRemark);
		
/*		JLabel lblEndTime = new JLabel("End Time");
		lblEndTime.setEnabled(false);
		lblEndTime.setBounds(208, 120, 70, 15);
		contentPane.add(lblEndTime);	*/
		
		FYI = new JTextArea();
		FYI.setLineWrap(true);
		FYI.setEditable(false);
		FYI.setText("*If any issue need to be declared, please feel free to send message to your proctor");
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
