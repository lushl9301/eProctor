package student_side;

import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.AbstractTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.MouseListener;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import org.bson.types.ObjectId;

import student_side.StudentHomeController.UpdateTimeToGoBeforeExam;
import student_side.StudentHomeController.UpdateTimeToGoDuringExam;

import com.mongodb.DBObject;

import entity.GrabberShow;
import entity.Main;
import entity.Messager;

public class StudentHomeUI extends JInternalFrame {
	
	GrabberShow grabberShow;

	private StudentHomeController controller;
	private JTable tableReview;
	JTextPane txtpnInformation;
	private JTextPane txtpnRecentMessages;
	private JTable tableCurrentBookings;
	private JList listAvailableCourses;
	private JList listAvailableSessions;
	
	javax.swing.Timer timer;
	UpdateTimeToGoBeforeExam utt;
	UpdateTimeToGoDuringExam updateTimeToGoDuringExam;
	
	private JTextField textField;
	
	protected ExamTab examTab;

	public StudentHomeUI(StudentHomeController controller) throws Exception {
		examTab = new ExamTab();
		this.controller = controller;
		initialize();
		this.setSize(1427, 742);
	}

	public void refreshUI() {
//		txtpnInformation.setText(controller.getInformation());
//		txtpnRecentMessages.setText(controller.getRecentMessage());
		tableCurrentBookings.setModel(new TableCurrentBookingsModel(controller.getTableCurrentBookings()));
		tableCurrentBookings.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableCurrentBookings.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableCurrentBookings.getColumnModel().getColumn(2).setPreferredWidth(170);
		listAvailableCourses.setModel(new ListArrayListModel(controller.getListAvailableCourses()));
		listAvailableSessions.setModel(new ListArrayListModel(controller.getListAvailableSessions(-1)));
		tableReview.setModel(new TableReviewModel(controller.getTableReview()));
		tableReview.getColumnModel().getColumn(0).setPreferredWidth(50);
		tableReview.getColumnModel().getColumn(1).setPreferredWidth(220);
		tableReview.getColumnModel().getColumn(2).setPreferredWidth(300);
		tableReview.getColumnModel().getColumn(3).setPreferredWidth(40);
		tableReview.getColumnModel().getColumn(4).setPreferredWidth(90);
	}
	
	private void initialize() throws Exception {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		UIManager.put("Button.font", new Font("Segoe UI", Font.PLAIN, 14));
		UIManager.put("Label.font", new Font("Segoe UI", Font.PLAIN, 14));
		UIManager.put("List.font", new Font("Segoe UI", Font.PLAIN, 14));
		UIManager.put("Menu.font", new Font("Segoe UI", Font.PLAIN, 15));
		UIManager.put("MenuBar.font", new Font("Segoe UI", Font.PLAIN, 15));
		UIManager.put("MenuItem.font", new Font("Segoe UI", Font.PLAIN, 15));
		UIManager.put("TabbedPane.font", new Font("Tahoma", Font.PLAIN, 14));
		UIManager.put("Table.font", new Font("Segoe UI", Font.PLAIN, 14));
		UIManager.put("TextPane.font", new Font("Segoe UI", Font.PLAIN, 14));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setResizable(false);
		
		setBorder(null);
		
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, screenSize.width - 20, screenSize.height - 60);
		getContentPane().add(tabbedPane);

		JPanel pnStatus = new JPanel();
		tabbedPane.addTab("Status", null, pnStatus, null);
		pnStatus.setLayout(null);

		txtpnInformation = new JTextPane();
		txtpnInformation.setBounds(screenSize.width / 2 - 420, 70, 400, 450);
		controller.updateInfoTextPand(examTab, txtpnInformation);
		pnStatus.add(txtpnInformation);

		JLabel lblInformation = new JLabel("Information");
		lblInformation.setBounds(screenSize.width / 2 - 420, 50, 150, 18);
		pnStatus.add(lblInformation);

		JLabel lblRecentMessages = new JLabel("Recent Messages");
		lblRecentMessages.setBounds(screenSize.width / 2, 50, 150, 18);
		pnStatus.add(lblRecentMessages);


		txtpnRecentMessages = new JTextPane();
		txtpnRecentMessages.setBounds(screenSize.width / 2, 70, 400, 450);
		controller.updateMsgTextPand(txtpnRecentMessages);
		pnStatus.add(txtpnRecentMessages);

		JPanel pnExam = new JPanel();
		tabbedPane.addTab("Exam", null, pnExam, null);
		pnExam.setLayout(null);

		JEditorPane dtrpnExampaper = new JEditorPane();
		dtrpnExampaper.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		dtrpnExampaper.setEditable(false);
		dtrpnExampaper.setVisible(false);
		examTab.dtrpnExampaper = dtrpnExampaper;

		JScrollPane spExampaper = new JScrollPane(dtrpnExampaper);
		spExampaper.setBounds(screenSize.width / 2 - 420, 70, 600, screenSize.height - 200);
		spExampaper.setVisible(false);
		pnExam.add(spExampaper);
		examTab.spExampaper = spExampaper;
		
		JLabel lblVideoTitle = new JLabel("video title");
		lblVideoTitle.setBounds(969, 42, 78, 15);
		lblVideoTitle.setVisible(false);
		pnExam.add(lblVideoTitle);
		examTab.lblVideoTitle = lblVideoTitle;
		
		JLabel lblVideoBox = new JLabel("video box");
		lblVideoBox.setBounds(969, 67, 256, 192);
		lblVideoBox.setVisible(false);
		pnExam.add(lblVideoBox);
		examTab.lblVideoBox = lblVideoBox;
		
		JLabel lblMsgReceived = new JLabel("New label");
		lblMsgReceived.setBounds(969, 288, 256, 127);
		lblMsgReceived.setVisible(false);
		pnExam.add(lblMsgReceived);
		examTab.lblMsgReceived = lblMsgReceived;
		
		JButton btnMsgSend = new JButton("Send");
		btnMsgSend.setBounds(1132, 592, 93, 23);
		btnMsgSend.setVisible(false);
		btnMsgSend.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ObjectId receiverId = controller.getOneProctor((ObjectId) examTab.mostRecentSession.get("_id"));
				String type = "Message";
				String content = examTab.textField.getText();
				System.out.println("cofirmed to #" + type + "# id #" + receiverId + "# content #" + content + "#");

				//
				// send to server
				//
				boolean wr = Messager.sendMsg(content, receiverId, Main.user_id, (ObjectId)examTab.mostRecentSession.get("_id"), type, new Date());
				System.out.println("sending message........... " + wr);
				if (wr) {
					examTab.textField.setText("");
					examTab.lblMsgStatus.setText("Sent.");
				} else {
					examTab.textField.setText(content);
					examTab.lblMsgStatus.setText("Failed to send.");
				}
			}
		});
		pnExam.add(btnMsgSend);
		examTab.btnMsgSend = btnMsgSend;
		
		final JLabel lblTimeToGo = new JLabel("Time To Go");
		lblTimeToGo.setBounds(112, 22, 256, 15);
		lblTimeToGo.setVisible(false);
		pnExam.add(lblTimeToGo);
		examTab.lblTimeToGo = lblTimeToGo;

		
		textField = new JTextField();
		textField.setBounds(969, 425, 256, 157);
		textField.setVisible(false);
		pnExam.add(textField);
		examTab.textField = textField;
		textField.setColumns(10);
		
		JLabel lblMsgStatus = new JLabel("");
		lblMsgStatus.setBounds(969, 596, 54, 15);
		lblMsgStatus.setVisible(false);
		examTab.lblMsgStatus = lblMsgStatus;
		pnExam.add(lblMsgStatus);
		
		JButton btnSign = new JButton("Okay...");
		btnSign.setBounds(112, 38, 93, 23);
		btnSign.setVisible(false);
		btnSign.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.getMostRecentSession(examTab);
				startTimer(examTab, controller.getTimeToMostRecentSession(examTab));
				examTab.btnSign.setVisible(false);
			}
		});
		pnExam.add(btnSign);
		examTab.btnSign = btnSign;

		JPanel pnBooking = new JPanel();
		tabbedPane.addTab("Booking", null, pnBooking, null);
		pnBooking.setLayout(null);

		JLabel lblCurrentBookings = new JLabel("Current Bookings");
		lblCurrentBookings.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblCurrentBookings.setBounds(screenSize.width / 2 - 420, 50, 150, 22);
		pnBooking.add(lblCurrentBookings);

		JButton btnMakeARequest = new JButton("Make A Request");
		btnMakeARequest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tableCurrentBookings != null) {
					int index = tableCurrentBookings.getSelectedRow();
					if (index != -1) {
						controller
								.makeRequestOfABooking(new ObjectId((String) tableCurrentBookings.getValueAt(index, 0)));
					}
				}
			}
		});
		btnMakeARequest.setBounds(screenSize.width / 2 + 200, 150, 150, 30);
		pnBooking.add(btnMakeARequest);

		JLabel lblNewBooking = new JLabel("New booking");
		lblNewBooking.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblNewBooking.setBounds(screenSize.width / 2 - 420, 323, 150, 22);
		pnBooking.add(lblNewBooking);

		JLabel lblAvailableCourses = new JLabel("Available Courses");
		lblAvailableCourses.setBounds(screenSize.width / 2 - 420, 352, 150, 18);
		pnBooking.add(lblAvailableCourses);

		listAvailableCourses = new JList();
		listAvailableCourses.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				listAvailableSessions.setModel(new ListArrayListModel(controller.getListAvailableSessions(listAvailableCourses.getSelectedIndex())));
			}
		});
		listAvailableCourses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listAvailableCourses.setBounds(screenSize.width / 2 - 420, 381, 405, 174);
		pnBooking.add(listAvailableCourses);

		JLabel lblAvailableSessions = new JLabel("Available Sessions");
		lblAvailableSessions.setBounds(screenSize.width / 2 - 5, 352, 150, 18);
		pnBooking.add(lblAvailableSessions);

		listAvailableSessions = new JList();
		listAvailableSessions.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listAvailableSessions.setBounds(screenSize.width / 2 - 5, 381, 240, 174);
		pnBooking.add(listAvailableSessions);

		JButton btnOk = new JButton("OK");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.bookNewSession(listAvailableSessions.getSelectedIndex(), listAvailableCourses.getSelectedIndex());
				controller.updateInfoTextPand(examTab, txtpnInformation);
			}
		});
		btnOk.setBounds(screenSize.width / 2 + 261, 403, 89, 30);
		pnBooking.add(btnOk);

		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(screenSize.width / 2 + 261, 450, 89, 30);
		pnBooking.add(btnReset);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				refreshUI();
			}
		});
		btnRefresh.setBounds(screenSize.width / 2 + 261, 497, 89, 30);
		pnBooking.add(btnRefresh);

		JSeparator separator = new JSeparator();
		separator.setBounds(screenSize.width / 2 - 420, 302, 770, 10);
		pnBooking.add(separator);
		
		JScrollPane scpCurrentBookings = new JScrollPane();
		scpCurrentBookings.setBounds(screenSize.width / 2 - 420, 78, 600, 213);
		pnBooking.add(scpCurrentBookings);
		
		tableCurrentBookings = new JTable();
		scpCurrentBookings.setViewportView(tableCurrentBookings);
		

		JPanel pnReview = new JPanel();
		tabbedPane.addTab("Review", null, pnReview, null);
		pnReview.setLayout(null);

		JButton btnCheckDetails = new JButton("Check Details");
		btnCheckDetails.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tableReview != null) {
					int index = tableReview.getSelectedRow();
					if (index != -1) {
						controller.checkDetailsOf(new ObjectId((String) tableReview.getValueAt(index, 0)));
					}
				}
			}
		});

		btnCheckDetails.setBounds(screenSize.width / 2 + 210, 73, 150, 30);
		pnReview.add(btnCheckDetails);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(screenSize.width / 2 - 420, 70, 600, 290);
		pnReview.add(scrollPane);

		tableReview = new JTable();
		scrollPane.setViewportView(tableReview);
		
		JPanel pnSetting = new JPanel();
		tabbedPane.addTab("Setting", null, pnSetting, null);
		
		// remove the border
        BasicInternalFrameUI basicInternalFrameUI = ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI());
        for (MouseListener listener : basicInternalFrameUI.getNorthPane().getMouseListeners())
        	basicInternalFrameUI.getNorthPane().removeMouseListener(listener);
        this.remove(basicInternalFrameUI.getNorthPane());
        this.setBorder(null);
        
//		controller.startTimer(examTab, timer, controller.getTimeToMostRecentSession(examTab));
		startTimer(examTab, 5);
	}
	public class TableCurrentBookingsModel extends AbstractTableModel {

		private ArrayList<ArrayList<String>> records;

		public TableCurrentBookingsModel(ArrayList<ArrayList<String>> records) {
			this.records = records;
		}

		@Override
		public int getRowCount() {
			return records.size();
		}

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			// "Record ID", "Course", "Session", "Grade", "Remark"ecly753
			case 0:
				return "Record ID";
			case 1:
				return "Course";
			case 2:
				return "Session";
			default:
				return "";
			}
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			return records.get(rowIndex).get(columnIndex);
		}
	}

	public class ListArrayListModel extends AbstractListModel {

		private ArrayList<String> records;

		public ListArrayListModel(ArrayList<String> records) {
			this.records = records;
		}

		public Object getElementAt(int index) {
			return records.get(index);
		}

		public int getSize() {
			if (records == null)
				return 0;
			return records.size();
		}

	}
	
	public class TableReviewModel extends AbstractTableModel {

		private ArrayList<ArrayList<String>> records;

		public TableReviewModel(ArrayList<ArrayList<String>> records) {
			this.records = records;
		}

		public int getRowCount() {
			return records.size();
		}

		public int getColumnCount() {
			return 5;
		}

		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return "Record ID";
			case 1:
				return "Course";
			case 2:
				return "Session";
			case 3:
				return "Grade";
			case 4:
				return "Remark";
			default:
				return "";
			}
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			return records.get(rowIndex).get(columnIndex);
		}
	}
	
	class ExamTab {
		JButton btnSign;
		JLabel lblTimeToGo;
		JLabel lblMsgStatus;

		JTextField textField;

		DBObject mostRecentSession;
		
		JScrollPane spExampaper;
		JEditorPane dtrpnExampaper;
		
		JLabel lblVideoTitle;
		JLabel lblVideoBox;
		
		JLabel lblMsgReceived;
		JButton btnMsgSend;
		
		public void showWhenStart() {
			spExampaper.setVisible(true);
			dtrpnExampaper.setVisible(true);
			try {
				dtrpnExampaper.setPage(new URL("http://www.google.com.sg/"));
			} catch (IOException e) {
				// XXX Auto-generated catch block
				System.out.println("dtrpnExampaper.setPage(); failed");
			}
			
			dtrpnExampaper.setVisible(true);
			lblVideoTitle.setVisible(true);
			lblVideoBox.setVisible(true);
			lblMsgReceived.setVisible(true);
			btnMsgSend.setVisible(true);
			textField.setVisible(true);
			lblMsgStatus.setVisible(true);
		}
		
		public void hideWhenEnd() {
			spExampaper.setVisible(false);
			dtrpnExampaper.setVisible(false);
			lblVideoTitle.setVisible(false);
			lblVideoBox.setVisible(false);
			lblMsgReceived.setVisible(false);
			btnMsgSend.setVisible(false);
			textField.setVisible(false);
			lblMsgStatus.setVisible(false);
		}
	}

	public void startTimer(ExamTab examTab, int milliToGo) {
		examTab.lblTimeToGo.setVisible(true);
		
		if (milliToGo == -1) {
			examTab.lblTimeToGo.setText("You don't have upcoming exam:)");
			return ;
		}
		
		utt = Main.studentHomeController.new UpdateTimeToGoBeforeExam(examTab, milliToGo);
		System.out.println("utt: " + utt);
		timer = new javax.swing.Timer(milliToGo, utt);
		timer.setInitialDelay(0);
		timer.setDelay(1000);
		timer.start();
	}
}

