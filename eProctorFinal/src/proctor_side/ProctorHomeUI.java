package proctor_side;

import static com.googlecode.javacv.cpp.opencv_core.cvFlip;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.AbstractTableModel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.awt.event.MouseListener;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import org.bson.types.ObjectId;

import entity.Main;
import entity.RecordObject;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import javax.swing.border.LineBorder;

public class ProctorHomeUI extends JInternalFrame {

	private ProctorHomeController controller;
	private JTable tableReview;
	private JTextPane txtpnInformation;
	private JTextPane txtpnRecentMessages;
	private JList<String> listCurrentBookings;
	private JTable tableCurrentBookings;
	private JList listAvailableCourses;
	private JList listAvailableSessions;
	
	JPanel pnInvigilate;

	// for invigilate
	Timer timer;
	private TreeMap<ObjectId, DBObject> sessionStudentList = new TreeMap<ObjectId, DBObject>();
	private TreeMap<String, TestVideo> imageLabelThreadList = new TreeMap<String, TestVideo>();
	private TreeMap<String, JLabel> videoBoxList = new TreeMap<String, JLabel>();
	private TreeMap<String, JLabel> videoIdList = new TreeMap<String, JLabel>();
	private int numOfVideoBox = 0;

	public ProctorHomeUI(ProctorHomeController controller) throws Exception {
		getContentPane().setPreferredSize(new Dimension(1024, 768));
		initialize();
		this.controller = controller;
	}

	public void refreshUI() throws java.lang.NullPointerException {
		txtpnInformation.setText(controller.getInformation());
		txtpnRecentMessages.setText(controller.getRecentMessage());
		tableCurrentBookings.setModel(new TableCurrentBookingsModel(controller.getTableCurrentBookings()));
		tableCurrentBookings.getColumnModel().getColumn(0)
				.setPreferredWidth(100);
		tableCurrentBookings.getColumnModel().getColumn(1)
				.setPreferredWidth(200);
		tableCurrentBookings.getColumnModel().getColumn(2)
				.setPreferredWidth(170);
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
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, screenSize.width - 20,
				screenSize.height - 60);
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

		// change to global 
	    // final JPanel pnInvigilate = new JPanel();
		pnInvigilate = new JPanel();
		
		pnInvigilate.setBounds(new Rectangle(0, 0, 1024, 768));
		tabbedPane.addTab("Invigilate", null, pnInvigilate, null);
		pnInvigilate.setLayout(null);
		
//		//=//=//=//=///=//=/=/=/=/=//=//=/=/=/=/==/=/
//		final JLabel idLabel = new JLabel("===" + numOfVideoBox);
//		// for displaying student's name
//		// 4 in a row, each has 10 space to other label in the same row, has 50 space to other in the same column, size = 100 x 30
//		idLabel.setBounds(50 + (4 % 4 - 1) * (10 + 256), 150 + (numOfVideoBox / 4) * (50 + 192) - 30, 100, 30);
//		pnInvigilate.add(idLabel);
//		idLabel.setVisible(true);
//		
//		// for displaying student's image
//		// 4 in a row, each has 10 space to other label in the same row, has 50 space to other in the same column, size = 256 x 192
//		final JLabel videoLabelx = new JLabel("xxx", new ImageIcon("webcam_icon.gif"), JLabel.CENTER);
//		videoLabelx.setBounds(50 + (4 % 4 - 1) * (10 + 256), 150 + (numOfVideoBox / 4) * (50 + 192), 256, 192);
//		videoLabelx.setIcon(new ImageIcon("webcam_icon.gif"));
//		pnInvigilate.add(videoLabelx);
//		videoLabelx.setVisible(true);
//		//=//=//=//=///=//=/=/=/=/=//=//=/=/=/=/==/=/

		
		JButton btnTestAddVideoBox = new JButton("add one video box");
		btnTestAddVideoBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("btnTestAddVideoBox clicked");
				addOneVideoBox("" + numOfVideoBox, pnInvigilate);
			}
		});
		btnTestAddVideoBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnTestAddVideoBox.setBounds(10, 10, 178, 23);
		pnInvigilate.add(btnTestAddVideoBox);
		
		JLabel lblTimeToGo = new JLabel("lblTimeToGo");
		lblTimeToGo.setBounds(219, 14, 324, 15);
		startTimer(5, lblTimeToGo);
//		startTimer(getTimeToMostRecentSession(), lblTimeToGo);
		pnInvigilate.add(lblTimeToGo);
		
		

		JPanel pnCheck = new JPanel();
		tabbedPane.addTab("Check", null, pnCheck, null);
		pnCheck.setLayout(null);

		JLabel lblCurrentBookings = new JLabel("Current Bookings");
		lblCurrentBookings.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblCurrentBookings.setBounds(263, 46, 150, 22);
		pnCheck.add(lblCurrentBookings);

//		listCurrentBookings = new JList<String>();
//		listCurrentBookings
//				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		listCurrentBookings.setBounds(screenSize.width / 2 - 420, 78, 600, 213);
//		pnBooking.add(listCurrentBookings);

		JButton btnMakeARequest = new JButton("Make A Request");
		btnMakeARequest.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tableCurrentBookings != null) {
					int index = tableCurrentBookings.getSelectedRow();
					if (index != -1) {
						controller.makeRequestOfABooking(new ObjectId((String) tableCurrentBookings.getValueAt(index, 0)));
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
		listAvailableCourses
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listAvailableCourses.setBounds(screenSize.width / 2 - 420, 381, 405,
				174);
		pnCheck.add(listAvailableCourses);

		JLabel lblAvailableSessions = new JLabel("Available Sessions");
		lblAvailableSessions.setBounds(screenSize.width / 2 - 5, 352, 150, 18);
		pnCheck.add(lblAvailableSessions);

		listAvailableSessions = new JList();
		listAvailableSessions
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listAvailableSessions
				.setBounds(screenSize.width / 2 - 5, 381, 240, 174);
		pnCheck.add(listAvailableSessions);

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
		pnSetting.setPreferredSize(new Dimension(1024, 768));
		tabbedPane.addTab("Setting", null, pnSetting, null);
		
		final JButton btnStop = new JButton("stop");
		btnStop.setBounds((screenSize.width - 345) / 2, 50, 345, 88);
		btnStop.setIcon(new ImageIcon("E:\\GitHub\\ce2006project\\eProctorFinal\\stop.png"));
		btnStop.setAlignmentY(Component.TOP_ALIGNMENT);
		btnStop.setPreferredSize(new Dimension(345, 88));
		btnStop.setVisible(false);
		pnSetting.setLayout(null);
		
		final JButton btnStart = new JButton("test Camera");
		btnStart.setBounds((screenSize.width - 345) / 2, 50, 345, 88);
		pnSetting.add(btnStart);
		btnStart.setMargin(new Insets(0, 0, 0, 0));
		btnStart.setIcon(new ImageIcon("E:\\GitHub\\ce2006project\\eProctorFinal\\start.png"));
		btnStart.setAlignmentY(Component.TOP_ALIGNMENT);
		btnStart.setPreferredSize(new Dimension(345, 88));
		
		final JLabel videoLabel = new JLabel("", new ImageIcon("webcam_icon.gif"), JLabel.CENTER);
		videoLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		videoLabel.setIcon(new ImageIcon("E:\\GitHub\\ce2006project\\eProctorFinal\\webcam_icon.gif"));
		videoLabel.setPreferredSize(new Dimension(512, 384));
		videoLabel.setBounds((screenSize.width - 512) / 2, 150, 512, 384);
		pnSetting.add(videoLabel);
		
		// start testing camera
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				imageLabelThreadList.put("0", new TestVideo("0", videoLabel));
				imageLabelThreadList.get("0").start();
				btnStart.setVisible(false);
				btnStop.setVisible(true);
			}
		});
		pnSetting.add(btnStop);
		
		// stop testing camera
		btnStop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				synchronized (this) {
					imageLabelThreadList.get("0").shouldEnd = true;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// XXX Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("isAlive: " + imageLabelThreadList.get("0").isAlive()); // kill failed?!
				btnStart.setVisible(true);
				btnStop.setVisible(false);
			}
		});

		
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

	class TestVideo extends Thread {
		
		// replace by repaint() ?
		
		public JLabel videoLabel;
		public volatile boolean shouldEnd;
		public String id;
		
		public TestVideo(String id, JLabel videoLabel) {
			this.id = id;
			this.videoLabel = videoLabel;
			this.shouldEnd = false;
		}

		@Override
		public void run() {
		    IplImage img = new IplImage();	
		    FrameGrabber grabber;
			try {
				grabber = FrameGrabber.createDefault(0);
				grabber.start(); 
				
			    while (!shouldEnd){	
			    	img = grabber.grab();
		            cvFlip(img, img, 1);
		            videoLabel.setIcon(new ImageIcon(img.getBufferedImage()));
			    }
			    grabber.stop();
			    videoLabel.setIcon(new ImageIcon("webcam_icon.gif"));
			    
			    return ;
			} catch (com.googlecode.javacv.FrameGrabber.Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void addOneVideoBox (String id, JPanel panel) {
		int horizontalSpace = 8;
		int verticalSpace = 50;

		final JLabel idLabel = new JLabel("" + numOfVideoBox);
		// for displaying student's name
		// 4 in a row, each has 10 space to other label in the same row, has 50 space to other in the same column, size = 100 x 30
		idLabel.setBounds(30 + (numOfVideoBox % 4) * (horizontalSpace + 256), 150 + (numOfVideoBox / 4) * (verticalSpace + 192) - 30, 100, 30);
		idLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(idLabel);
		idLabel.setVisible(true);
		
		// for displaying student's image
		// 4 in a row, each has 10 space to other label in the same row, has 50 space to other in the same column, size = 256 x 192
		final JLabel videoLabel = new JLabel("", new ImageIcon("webcam_icon.gif"), JLabel.CENTER);
		videoLabel.setBounds(30 + (numOfVideoBox % 4) * (horizontalSpace + 256), 150 + (numOfVideoBox / 4) * (verticalSpace + 192), 256, 192);
		videoLabel.setIcon(new ImageIcon("webcam_icon.gif"));
		videoLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(videoLabel);
		videoLabel.setVisible(true);
		
		panel.repaint();
		
		videoIdList.put(id, idLabel);
		videoBoxList.put(id, videoLabel);
		
		numOfVideoBox++;
	}

	// should be moved to controller later
	class ReceiveShow extends Thread {
		int port = 6002;
		
	    public RecordObject recordObject;
	    public ObjectInputStream sInput;
	    public ServerSocket serverSocket;
	    public Socket socket;
	    
	    public volatile boolean shouldEnd;
	  
	    public ReceiveShow () throws IOException {
	    	this.serverSocket = new ServerSocket(port);
	    	this.shouldEnd = false;
	    }
	    
	    public void run() {
	    	try {
		    	while (!shouldEnd) {  		
					socket = serverSocket.accept();
					sInput = new ObjectInputStream(socket.getInputStream());
		    		RecordObject recordObject = (RecordObject) sInput.readObject();
		    		socket.close();
		    		
		    		BufferedImage buf = ImageIO.read(new ByteArrayInputStream(recordObject.getImageBytes()));
		            IplImage toDisplay = IplImage.createFrom(buf);
		            
		            videoBoxList.get(recordObject.getUserId()).setIcon(new ImageIcon(toDisplay.getBufferedImage()));
		    	}
		    	
		    	return ;
			} catch (Exception e) {
				// XXX Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

	// should be moved to controller later
	class UpdateTimeToGoBeforeExam implements ActionListener {
		public int timeToGo;
		
		public JLabel lblTimeToGo;
		public UpdateTimeToGoBeforeExam(JLabel lblTimeToGo, int timeToGo) {
			this.timeToGo = timeToGo;
			this.lblTimeToGo = lblTimeToGo;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// XXX Auto-generated method stub
			if (timeToGo > 0) {
				timeToGo--;
				lblTimeToGo.setText(secondToString(timeToGo));
			} else if (timeToGo == 0) {
				timer.stop();
								
				sessionStudentList = getStudentList();
				if (sessionStudentList == null) {
					System.out.println("sessionStudentList == null, getStudentList failed");
				} else if (sessionStudentList.size() == 0) {
					System.out.println("sessionStudentList.size() == 0");
					lblTimeToGo.setText("this sesion has no student...");
				} else {
					System.out.println("sessionStudentList != null");
					lblTimeToGo.setText("here you go...");
									
					Iterator<Entry<ObjectId, DBObject>> iter = sessionStudentList.entrySet().iterator();
					Entry<ObjectId, DBObject> temp = null;
					while (iter.hasNext()) {
						System.out.println("add one");
						temp = iter.next();
						addOneVideoBox((String)temp.getValue().get("username"), pnInvigilate);
					}
				}
			} else {
				lblTimeToGo.setText("here is UpdateTimeToGoBeforeExam. something goes wrong");
			}
		}
	}
	class UpdateTimeToGoDuringExam implements ActionListener {
		public int timeToGo;
		public JLabel lblTimeToGo;
		
		public UpdateTimeToGoDuringExam(JLabel lblTimeToGo, int timeToGo) {
			this.timeToGo = timeToGo;
			this.lblTimeToGo = lblTimeToGo;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// XXX Auto-generated method stub
			if (timeToGo > 0) {
				timeToGo--;
				lblTimeToGo.setText(secondToString(timeToGo));
			} else if (timeToGo == 0) {
				timer.stop();
				lblTimeToGo.setText("exam ended. refresh to exit");
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

	public void startTimer(int timeToGo, JLabel lblTimeToGo) throws ParseException {
		if (timeToGo == -1) {
			lblTimeToGo.setText("You don't have upcoming exam:)");
			return ;
		}
			
		UpdateTimeToGoBeforeExam utt = new UpdateTimeToGoBeforeExam(lblTimeToGo, timeToGo);
		
		timer = new Timer(timeToGo, utt);
		timer.setInitialDelay(0);
		timer.setDelay(1000);
		timer.start();
	}
	
	public int getTimeToMostRecentSession() throws ParseException {
		ObjectId mostRecentSession = getMostRecentSession();
		if (mostRecentSession == null)
			return -1;
		
		Date mostRecentStartTime = (Date) Main.mongoHQ.session.findOne(new BasicDBObject("_id", mostRecentSession), new BasicDBObject("start", 1)).get("start");
		
		int timeToGo = (int)((mostRecentStartTime.getTime() - new Date().getTime()) / 1000);
		System.out.println("\nstart: " + mostRecentStartTime.toString());
		System.out.println("now: " + new Date().toString());
		System.out.println("time to go: " + timeToGo);
		return timeToGo;
	}

	public ObjectId getMostRecentSession() throws ParseException {
//		DBCursor sessionsCursor = Main.mongoHQ.record.find(new BasicDBObject("user_id", Main.user_id), new BasicDBObject("session_id", 1));
		DBCursor sessionsCursor = Main.mongoHQ.record.find(new BasicDBObject("user_id", new ObjectId("532541929862bcab1a0000fd")), new BasicDBObject("session_id", 1));
				
		ArrayList<ObjectId> sessions = new ArrayList<ObjectId>();
		
		if (sessionsCursor == null) {
			System.out.println("sessionsCursor == null");
			return null;
		}
		
		while (sessionsCursor.hasNext()) {
			sessions.add((ObjectId)sessionsCursor.next().get("session_id"));
		}
				
		ObjectId mostRecentSession = null;
		Date mostRecentStartTime = new SimpleDateFormat("dd-M-yyyy hh:mm:ss").parse("01-01-2015 00:00:00");
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
		
		return mostRecentSession;
	}
	
	public TreeMap<ObjectId, DBObject> getStudentList() {
		TreeMap<ObjectId, DBObject> sessionStudentList = new TreeMap<ObjectId, DBObject>();
		DBCursor studentCursor = null;
		
		try {
			ObjectId mostRecentSession = getMostRecentSession();
			if (mostRecentSession != null) {
				BasicDBObject query = new BasicDBObject("session_id", getMostRecentSession()).append("domain", "Student");
				studentCursor = Main.mongoHQ.record.find(query);
			}
		} catch (ParseException e) {
			// XXX Auto-generated catch block
			e.printStackTrace();
			System.out.println("failed to get student list");
		}
		
		if (studentCursor == null) {
			System.out.println("studentCursor == null");
			return null;
		}
			
		ObjectId studentTemp = null;
		while (studentCursor.hasNext()) {
			studentTemp = (ObjectId) studentCursor.next().get("student_id");			
			sessionStudentList.put(studentTemp, Main.mongoHQ.student.findOne(new BasicDBObject("_id", studentTemp)));
		}
		
		System.out.println("sessionStudentList: " + sessionStudentList.toString());
		return sessionStudentList;
	}
}

