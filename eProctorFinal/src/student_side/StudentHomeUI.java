package student_side;

import java.awt.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.AbstractTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.awt.event.MouseListener;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import entity.Main;
import entity.Messager;

import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

public class StudentHomeUI extends JInternalFrame {

	private StudentHomeController controller;
	private JTable tableReview;
	private JTextPane txtpnInformation;
	private JTextPane txtpnRecentMessages;
	private JTable tableCurrentBookings;
	private JList listAvailableCourses;
	private JList listAvailableSessions;
	
	private Timer timer;

	public StudentHomeUI(StudentHomeController controller) throws Exception {
		initialize();
		this.controller = controller;
		this.setSize(1427, 742);
	}

	public void refreshUI() {
		txtpnInformation.setText(controller.getInformation());
		txtpnRecentMessages.setText(controller.getRecentMessage());
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
		pnStatus.add(txtpnInformation);

		JLabel lblInformation = new JLabel("Information");
		lblInformation.setBounds(screenSize.width / 2 - 420, 50, 150, 18);
		pnStatus.add(lblInformation);

		JLabel lblRecentMessages = new JLabel("Recent Messages");
		lblRecentMessages.setBounds(screenSize.width / 2, 50, 150, 18);
		pnStatus.add(lblRecentMessages);


		txtpnRecentMessages = new JTextPane();
		txtpnRecentMessages.setBounds(screenSize.width / 2, 70, 400, 450);
		pnStatus.add(txtpnRecentMessages);

		JPanel pnExam = new JPanel();
		tabbedPane.addTab("Exam", null, pnExam, null);
		pnExam.setLayout(null);

		JEditorPane dtrpnExampaper = new JEditorPane();
		dtrpnExampaper.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		dtrpnExampaper.setEditable(false);
		dtrpnExampaper.setVisible(false);
		ExamTab.dtrpnExampaper = dtrpnExampaper;

		JScrollPane spExampaper = new JScrollPane(dtrpnExampaper);
		spExampaper.setBounds(screenSize.width / 2 - 420, 70, 600, screenSize.height - 200);
		spExampaper.setVisible(false);
		pnExam.add(spExampaper);
		
		JLabel lblVideoTitle = new JLabel("video title");
		lblVideoTitle.setBounds(969, 42, 78, 15);
		lblVideoTitle.setVisible(false);
		pnExam.add(lblVideoTitle);
		ExamTab.lblVideoTitle = lblVideoTitle;
		
		JLabel lblVideoBox = new JLabel("video box");
		lblVideoBox.setBounds(969, 67, 256, 192);
		lblVideoBox.setVisible(false);
		pnExam.add(lblVideoBox);
		ExamTab.lblVideoBox = lblVideoBox;
		
		JLabel lblMsgReceived = new JLabel("New label");
		lblMsgReceived.setBounds(969, 288, 256, 127);
		lblMsgReceived.setVisible(false);
		pnExam.add(lblMsgReceived);
		ExamTab.lblMsgReceived = lblMsgReceived;
		
		JLabel lblMsgBox = new JLabel("New label");
		lblMsgBox.setBounds(969, 425, 256, 89);
		lblMsgBox.setVisible(false);
		pnExam.add(lblMsgBox);
		ExamTab.lblMsgBox = lblMsgBox;
		
		JButton btnMsgSend = new JButton("New button");
		btnMsgSend.setBounds(1132, 524, 93, 23);
		btnMsgSend.setVisible(false);
		pnExam.add(btnMsgSend);
		ExamTab.btnMsgSend = btnMsgSend;
		
		JLabel lblTimeToGo = new JLabel("Time To Go");
		lblTimeToGo.setBounds(151, 22, 237, 15);
		lblTimeToGo.setVisible(false);
		pnExam.add(lblTimeToGo);
		startTimer(5, lblTimeToGo);

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
	
	static class ExamTab {
		static DBObject mostRecentSession;
		
		static JEditorPane dtrpnExampaper;
		static JLabel lblVideoTitle;
		static JLabel lblVideoBox;
		
		static JLabel lblMsgReceived;
		static JLabel lblMsgTitle;
		static JLabel lblMsgBox;
		static JButton btnMsgSend;
		
		public static void showWhenStart() {
			dtrpnExampaper.setVisible(true);
			try {
				dtrpnExampaper.setPage("www.google.com.sg");
			} catch (IOException e) {
				// XXX Auto-generated catch block
				System.out.println("dtrpnExampaper.setPage(); failed");
			}
			
			lblVideoTitle.setVisible(true);
			lblVideoBox.setVisible(true);
			lblMsgReceived.setVisible(true);
			lblMsgTitle.setVisible(true);
			lblMsgBox.setVisible(true);
			btnMsgSend.setVisible(true);
		}
		
		public static void hideWhenEnd() {
			dtrpnExampaper.setVisible(false);
			lblVideoTitle.setVisible(false);
			lblVideoBox.setVisible(false);
			lblMsgReceived.setVisible(false);
			lblMsgTitle.setVisible(false);
			lblMsgBox.setVisible(false);
			btnMsgSend.setVisible(false);
		}
	}

	class UpdateTimeToGoBeforeExam implements ActionListener {
		public int secondToGo;
		
		public JLabel lblTimeToGo;
		public UpdateTimeToGoBeforeExam(JLabel lblTimeToGo, int secondToGo) {
			this.secondToGo = secondToGo;
			this.lblTimeToGo = lblTimeToGo;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// XXX Auto-generated method stub
			if (secondToGo > 0) {
				secondToGo--;
				lblTimeToGo.setText(secondToString(secondToGo));
			} else if (secondToGo == 0) {
				timer.stop();
				getMostRecentSession();
								
				if (ExamTab.mostRecentSession == null) {
					System.out.println("session disappeared!");
				}
				
				// start a countdown timer during the exam
				long duration = ((Date)ExamTab.mostRecentSession.get("end")).getTime() - ((Date)ExamTab.mostRecentSession.get("start")).getTime();
				System.out.println("duration: " + secondToString((int)(duration / 1000)));
				UpdateTimeToGoDuringExam updateTimeToGoDuringExam = new UpdateTimeToGoDuringExam(lblTimeToGo, (int)duration / 1000);
				timer = new Timer((int)duration, updateTimeToGoDuringExam);
				timer.setInitialDelay(0);
				timer.setDelay(1000);
				timer.start();
				
				ExamTab.showWhenStart();
			} else {
				lblTimeToGo.setText("here is UpdateTimeToGoBeforeExam. something goes wrong");
			}
		}
	}
	class UpdateTimeToGoDuringExam implements ActionListener {
		public int secondToGo;
		public JLabel lblTimeToGo;
		
		public UpdateTimeToGoDuringExam(JLabel lblTimeToGo, int secondToGo) {
			this.secondToGo = secondToGo;
			this.lblTimeToGo = lblTimeToGo;
			System.out.println("new UpdateTimeToGoDuringExam");
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// XXX Auto-generated method stub
			if (secondToGo > 0) {
				secondToGo--;
				System.out.println("timeToGo--; left " + secondToString(secondToGo));
				lblTimeToGo.setText(secondToString(secondToGo));
				
				if (secondToGo % 5 == 0) {
					//
					// fetch message once
					//
					
					// String allTogether = "all student id and message together. " + secondToGo;
					System.out.println(Main.user_id + " fetching message");
					String allTogether = Messager.pollMsg(new ObjectId("532541929862bcab1a0000fd"), "Proctor");
					//
					// compress all message together
					//
					ExamTab.lblMsgReceived.setText("<html>" + allTogether + "</html>");
				}
			} else if (secondToGo == 0) {
				timer.stop();
				lblTimeToGo.setText("exam ended.");
				ExamTab.hideWhenEnd();
			} else {
				lblTimeToGo.setText("here is UpdateTimeToGoDuringExam. something goes wrong");
			}
		}
	}
	public String secondToString (int second) {
		int minute = second / 60;
		second = second % 60;
		
		int hour = minute / 60;
		minute = minute % 60;
		
		int day = hour / 24;
		hour = hour % 24;
		
		return day + " days " + hour + " hours " + minute + " minutes " + second + " seconds ";
	}

	public void startTimer(int milliToGo, JLabel lblTimeToGo) {
		lblTimeToGo.setVisible(true);
		
		if (milliToGo == -1) {
			lblTimeToGo.setText("You don't have upcoming exam:)");
			return ;
		}
			
		UpdateTimeToGoBeforeExam utt = new UpdateTimeToGoBeforeExam(lblTimeToGo, milliToGo);
		
		timer = new Timer(milliToGo, utt);
		timer.setInitialDelay(0);
		timer.setDelay(1000);
		timer.start();
	}
	
	public int getTimeToMostRecentSession() {
		if (ExamTab.mostRecentSession == null)
			return -1;
		
		Date mostRecentStartTime = (Date) ExamTab.mostRecentSession.get("start");
		
		int secondToGo = (int)((mostRecentStartTime.getTime() - new Date().getTime()) / 1000);
		System.out.println("\nstart: " + mostRecentStartTime.toString());
		System.out.println("now: " + new Date().toString());
		System.out.println("second to go: " + secondToGo);
		return secondToGo;
	}
	public void getMostRecentSession() {
//		DBCursor sessionsCursor = Main.mongoHQ.record.find(new BasicDBObject("user_id", Main.user_id), new BasicDBObject("session_id", 1));
		if (Main.mongoHQ == null) {
			System.out.println("server not found");
			return ;
		}
		DBCursor sessionsCursor = Main.mongoHQ.record.find(new BasicDBObject("student_id", new ObjectId("531ec0d07a0016ee1c000508")), new BasicDBObject("session_id", 1));
				
		ArrayList<ObjectId> sessions = new ArrayList<ObjectId>();
		
		if (sessionsCursor == null) {
			System.out.println("sessionsCursor == null");
			return ;
		}
		
		while (sessionsCursor.hasNext()) {
			sessions.add((ObjectId)sessionsCursor.next().get("session_id"));
		}
				
		ObjectId mostRecentSession = null;
		Date mostRecentStartTime = null;
		try {
			mostRecentStartTime = new SimpleDateFormat("dd-M-yyyy hh:mm:ss").parse("01-01-2015 00:00:00");
		} catch (ParseException e) {
			// XXX Auto-generated catch block
			e.printStackTrace();
			System.out.println("new SimpleDateFormat failed");
			ExamTab.mostRecentSession = null;
			return ;
		}
		ObjectId tempSession = null;
		Date tempStartTime = null;
		Iterator<ObjectId> iter = sessions.iterator();
		while (iter.hasNext()) {
			tempSession = iter.next();
			
			DBObject tempResult = Main.mongoHQ.session.findOne(new BasicDBObject("_id", tempSession));
			tempStartTime = (Date) tempResult.get("start");
			if ((tempStartTime.before(mostRecentStartTime))) {
				mostRecentSession = tempSession;
				mostRecentStartTime = tempStartTime;
			}
		}
		
		System.out.println("mostRecentSession: " + mostRecentSession);
		ExamTab.mostRecentSession = Main.mongoHQ.session.findOne(new BasicDBObject("_id", mostRecentSession));
		return ;
	}
}

