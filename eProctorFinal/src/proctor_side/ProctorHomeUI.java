package proctor_side;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import org.bson.types.ObjectId;

public class ProctorHomeUI extends JFrame {

	private ProctorHomeController controller;
	private JTable tableReview;
	private JTextPane txtpnInformation;
	private JTextPane txtpnRecentMessages;
	private JList<String> listCurrentBookings;
	private JTable tableCurrentBookings;
	private JList listAvailableCourses;
	private JList listAvailableSessions;
	private ArrayList<ArrayList<String>> currentBookingRecords;
	private TableCurrentBookingsModel tableCurrentBookingModel;

	public ProctorHomeUI(ProctorHomeController controller) throws Exception {
		this.controller = controller;
		initialize();
		//refreshUI()
		setVisible(true);
	}

	private void refreshUI() {
		txtpnInformation.setText(controller.getInformation());
		txtpnRecentMessages.setText(controller.getRecentMessage());
		tableCurrentBookings.setModel(new TableCurrentBookingsModel(controller.getTableCurrentBookings(false)));
		tableCurrentBookings.getColumnModel().getColumn(0).setPreferredWidth(100);
		tableCurrentBookings.getColumnModel().getColumn(1).setPreferredWidth(200);
		tableCurrentBookings.getColumnModel().getColumn(2).setPreferredWidth(170);
		
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
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
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
		GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		GraphicsDevice[] devices = graphicsEnvironment.getScreenDevices();
		GraphicsDevice graphicsDevice = devices[0];
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setAlwaysOnTop(true);
		setAutoRequestFocus(true);
		setUndecorated(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 27, screenSize.width - 20,
				screenSize.height - 60);
		getContentPane().add(tabbedPane);

		JPanel pnStatus = new JPanel();
		tabbedPane.addTab("Status", null, pnStatus, null);
		pnStatus.setLayout(null);

		txtpnInformation = new JTextPane();
		txtpnInformation.setBounds(screenSize.width / 2 - 420, 70, 400, 450);
		txtpnInformation.setText(controller.getInformation());
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

		JPanel pnInvigilate = new JPanel();
		tabbedPane.addTab("Invigilate", null, pnInvigilate, null);
		pnInvigilate.setLayout(null);

		JEditorPane dtrpnExampaper = new JEditorPane();
		dtrpnExampaper.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		dtrpnExampaper.setEditable(false);

		JPanel pnCheck = new JPanel();
		tabbedPane.addTab("Check", null, pnCheck, null);
		pnCheck.setLayout(null);

		currentBookingRecords = controller.getTableCurrentBookings(false);
		tableCurrentBookingModel = new TableCurrentBookingsModel(currentBookingRecords);
		JLabel lblCurrentBookings = new JLabel("Current Bookings");
		lblCurrentBookings.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblCurrentBookings.setBounds(screenSize.width / 2 - 420, 50, 150, 22);
		pnCheck.add(lblCurrentBookings);

		JButton btnMakeARequest = new JButton("Make A Request");
		btnMakeARequest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (currentBookingRecords != null) {
					int index = tableCurrentBookings.getSelectedRow();
					if (index != -1) {
						controller.makeRequestOfABooking((String) tableCurrentBookings.getValueAt(index, 0));
					}
				}
			}
		});
		
		btnMakeARequest.setBounds(screenSize.width / 2 + 200, 150, 150, 30);
		pnCheck.add(btnMakeARequest);

		JLabel lblNewBooking = new JLabel("New booking");
		lblNewBooking.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblNewBooking.setBounds(screenSize.width / 2 - 420, 323, 150, 22);
		pnCheck.add(lblNewBooking);

		JLabel lblAvailableCourses = new JLabel("Available Courses");
		lblAvailableCourses.setBounds(screenSize.width / 2 - 420, 352, 150, 18);
		pnCheck.add(lblAvailableCourses);

		listAvailableCourses = new JList();
		listAvailableCourses.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				listAvailableSessions.setModel(new ListArrayListModel(controller.getListAvailableSessions(listAvailableCourses.getSelectedIndex())));
			}
		});
		listAvailableCourses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listAvailableCourses.setBounds(screenSize.width / 2 - 420, 381, 405,
				174);
		pnCheck.add(listAvailableCourses);

		JLabel lblAvailableSessions = new JLabel("Available Sessions");
		lblAvailableSessions.setBounds(screenSize.width / 2 - 5, 352, 150, 18);
		pnCheck.add(lblAvailableSessions);

/*		listAvailableSessions = new JList();
		listAvailableSessions
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listAvailableSessions
				.setBounds(screenSize.width / 2 - 5, 381, 240, 174);
		pnCheck.add(listAvailableSessions);*/

		JButton btnOk = new JButton("OK");
		btnOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				controller.bookNewSession(listAvailableSessions.getSelectedIndex(), listAvailableCourses.getSelectedIndex());
			}
		});
		btnOk.setBounds(screenSize.width / 2 + 261, 403, 89, 30);
		pnCheck.add(btnOk);

		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(screenSize.width / 2 + 261, 450, 89, 30);
		pnCheck.add(btnReset);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				refreshUI();
			}
		});
		btnRefresh.setBounds(screenSize.width / 2 + 261, 497, 89, 30);
		pnCheck.add(btnRefresh);

		JSeparator separator = new JSeparator();
		separator.setBounds(screenSize.width / 2 - 420, 302, 770, 10);
		pnCheck.add(separator);
		
		JScrollPane scpCurrentBookings = new JScrollPane();
		scpCurrentBookings.setBounds(screenSize.width / 2 - 420, 78, 600, 213);
		pnCheck.add(scpCurrentBookings);		

		JPanel pnReview = new JPanel();
		tabbedPane.addTab("Review", null, pnReview, null);
		pnReview.setLayout(null);

		currentBookingRecords = controller.getTableCurrentBookings(true);
		tableCurrentBookingModel = new TableCurrentBookingsModel(currentBookingRecords);
		tableCurrentBookings = new JTable();
		tableCurrentBookings.setModel(tableCurrentBookingModel);
		scpCurrentBookings.setViewportView(tableCurrentBookings);
		
		JButton btnCheckDetails = new JButton("Check Details");
		btnCheckDetails.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (currentBookingRecords != null) {
					int index = tableCurrentBookings.getSelectedRow();
					if (index != -1) {
						controller.checkDetailsOf((String) tableCurrentBookings.getValueAt(index, 0));
					}
				}
			}
		});
		btnCheckDetails.setBounds(screenSize.width / 2 + 210, 73, 150, 30);
		pnReview.add(btnCheckDetails);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(screenSize.width / 2 - 420, 70, 600, 290);
		pnReview.add(scrollPane);
		
		JPanel pnSetting = new JPanel();
		tabbedPane.addTab("Setting", null, pnSetting, null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, screenSize.width, 25);
		getContentPane().add(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmLogout = new JMenuItem("Logout");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//controller.logout();
				setVisible(false);
				//restart loginUI
			}
		});
		mnFile.add(mntmLogout);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//controller.exit();
				setVisible(false);
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.getAboutMessage();
			}
		});
		mnHelp.add(mntmAbout);

		graphicsDevice.setFullScreenWindow(this);
		validate();
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
			// "Record ID", "Course", "Session", "Grade", "Remark"
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
}
