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
import entity.Messager;
import entity.RecordObject;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;

public class ProctorHomeUI extends JInternalFrame {

	private ProctorHomeController controller;
	private JTable tableReview;
	private JTextPane txtpnInformation;
	private JTextPane txtpnRecentMessages;
	private JList<String> listCurrentBookings;
	private JTable tableCurrentBookings;
	private JList listAvailableCourses;
	
	private JPanel pnInvigilate;

	// for invigilation
	private Timer timer;
	private ReceiveShow videoReceiveShowThread;
	// for testing camera in setting tab
	private TestVideo testVideoThread;


	public ProctorHomeUI(ProctorHomeController controller) throws Exception {
		getContentPane().setPreferredSize(new Dimension(1024, 768));
		initialize();
		this.controller = controller;
	}

	public void XrefreshUI() throws java.lang.NullPointerException {
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
//		listAvailableSessions.setModel(new ListArrayListModel(controller.getListAvailableSessions(-1)));
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
		pnInvigilate.setBackground(Color.WHITE);
		
		pnInvigilate.setBounds(new Rectangle(0, 0, 1024, 768));
		tabbedPane.addTab("Invigilate", null, pnInvigilate, null);
		pnInvigilate.setLayout(null);

		
		// button in invigilation tab for testing function
		JButton btnTestAddVideoBox = new JButton("add one video box");
		btnTestAddVideoBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				System.out.println("btnTestAddVideoBox clicked");
				addOneVideoBox("" + InvigilateTab.numOfVideoBox, pnInvigilate);
			}
		});
		btnTestAddVideoBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnTestAddVideoBox.setBounds(10, 10, 178, 23);
		pnInvigilate.add(btnTestAddVideoBox);
		
		// label in invigilation tab
		final JLabel lblTimeToGo = new JLabel("lblTimeToGo");
		lblTimeToGo.setBounds(198, 14, 324, 15);
		lblTimeToGo.setVisible(false);
		startTimer(3, lblTimeToGo);
//		startTimer(getTimeToMostRecentSession(), lblTimeToGo);
		pnInvigilate.add(lblTimeToGo);
		
		// textField in invigilation tab
		JTextField textField = new JTextField();
		textField.setBounds(1112, 348, 197, 233);
		textField.setVisible(false);
		pnInvigilate.add(textField);
		textField.setColumns(10);
		InvigilateTab.textField = textField;
		
		// button in invigilation tab
		JButton btnSend = new JButton("Send");
		btnSend.setBounds(1216, 591, 93, 23);
		btnSend.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String username = InvigilateTab.lblStudentId.getText().split(" ")[1];
				String type = InvigilateTab.lblType.getText().split(" ")[1];
				String content = InvigilateTab.textField.getText();
				System.out.println("cofirmed to #" + type + "# id #" + username + "# content #" + content + "#");

				//
				// send to server
				//
				ObjectId id = null;
				Iterator<ObjectId> iter = InvigilateTab.sessionStudentList.keySet().iterator();
				while (iter.hasNext()) {
					id = iter.next();
					if (InvigilateTab.sessionStudentList.get(id).get("username").equals(username))
						break;
				}
				Messager.sendMsg(content, id, Main.user_id, (ObjectId)InvigilateTab.mostRecentSession.get("_id"), type, new Date());
				if ("send" != "failed") {
					InvigilateTab.lblStudentId.setText("Sent_to " + id);
					InvigilateTab.lblType.setText("");
					InvigilateTab.textField.setText("");
					InvigilateTab.btnSend.setText("Send");
				} else {
					InvigilateTab.lblStudentId.setText("Failed_sending_to " + id);
					InvigilateTab.btnSend.setText("Re-send");
				}
			}
		});
		btnSend.setVisible(false);
		pnInvigilate.add(btnSend);
		InvigilateTab.btnSend = btnSend;
		
		// label in invigilation tab
		JLabel lblStudentId = new JLabel("Student: ");
		lblStudentId.setBounds(1112, 298, 197, 15);
		lblStudentId.setVisible(false);
		pnInvigilate.add(lblStudentId);
		InvigilateTab.lblStudentId = lblStudentId;
		
		// label in invigilation tab
		JLabel lblType = new JLabel("Type: ");
		lblType.setBounds(1112, 323, 197, 15);
		lblType.setVisible(false);
		pnInvigilate.add(lblType);
		InvigilateTab.lblType = lblType;
		
		// button in invigilate tab
		JButton btnSign = new JButton("Sign");
		btnSign.setBounds(532, 10, 93, 23);
		btnSign.setVisible(false);
		btnSign.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (videoReceiveShowThread != null && videoReceiveShowThread.isAlive()) {
					videoReceiveShowThread.shouldEnd = true;
					videoReceiveShowThread = null;
				}
				
				InvigilateTab.hideWhenEnd();
				InvigilateTab.btnSign.setVisible(false);
				
				getMostRecentSession();
				startTimer(getTimeToMostRecentSession(), lblTimeToGo);
				
				//
				// send the sign to server
				// terminate connection to video server
				//
				
				System.out.println("An exam ended with sign by proctor.");
			}
		});
		pnInvigilate.add(btnSign);
		InvigilateTab.btnSign = btnSign;
		
		// label in invigilate tab
		JLabel lblIncomingMsg = new JLabel("Received message");
		lblIncomingMsg.setBounds(1112, 50, 197, 238);
		lblIncomingMsg.setVisible(false);
		pnInvigilate.add(lblIncomingMsg);
		InvigilateTab.lblIncomingMsg = lblIncomingMsg;
		

		//
		// check tab
		//
		JPanel pnCheck = new JPanel();
		tabbedPane.addTab("Check", null, pnCheck, null);
		pnCheck.setLayout(null);

		JLabel lblCurrentBookings = new JLabel("Course assigned to");
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
		btnMakeARequest.setBounds(873, 262, 150, 30);
		pnCheck.add(btnMakeARequest);

		JLabel lblNewBooking = new JLabel("Exams of Course");
		lblNewBooking.setFont(new Font("Segoe UI", Font.BOLD, 16));
		lblNewBooking.setBounds(screenSize.width / 2 - 420, 323, 150, 22);
		pnCheck.add(lblNewBooking);

		listAvailableCourses = new JList();
//		listAvailableCourses.addListSelectionListener(new ListSelectionListener() {
//			public void valueChanged(ListSelectionEvent arg0) {
//				listAvailableSessions.setModel(new ListArrayListModel(controller.getListAvailableSessions(listAvailableCourses.getSelectedIndex())));
//			}
//		});
		listAvailableCourses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listAvailableCourses.setBounds(263, 355, 600, 174);
		pnCheck.add(listAvailableCourses);

		JButton btnOk = new JButton("OK");
//		btnOk.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				controller.bookNewSession(listAvailableSessions.getSelectedIndex(), listAvailableCourses.getSelectedIndex());
//			}
//		});
		btnOk.setBounds(873, 419, 89, 30);
		pnCheck.add(btnOk);

		JButton btnReset = new JButton("Reset");
		btnReset.setBounds(873, 459, 89, 30);
		pnCheck.add(btnReset);

		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
//				refreshUI();
			}
		});
		btnRefresh.setBounds(873, 499, 89, 30);
		pnCheck.add(btnRefresh);

		JSeparator separator = new JSeparator();
		separator.setBounds(screenSize.width / 2 - 420, 302, 770, 10);
		pnCheck.add(separator);
		
		JScrollPane scpCurrentBookings = new JScrollPane();
		scpCurrentBookings.setBounds(screenSize.width / 2 - 420, 78, 600, 213);
		pnCheck.add(scpCurrentBookings);
		
		tableCurrentBookings = new JTable();
		scpCurrentBookings.setViewportView(tableCurrentBookings);
		

		// panel for result review
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
		
		// panel for setting
		JPanel pnSetting = new JPanel();
		pnSetting.setBackground(Color.WHITE);
		pnSetting.setBorder(null);
		pnSetting.setPreferredSize(new Dimension(1024, 768));
		tabbedPane.addTab("Setting", null, pnSetting, null);
		
		// button to end camera test in setting panel
		final JButton btnStop = new JButton("");
		btnStop.setHorizontalTextPosition(SwingConstants.CENTER);
		btnStop.setForeground(Color.BLACK);
		btnStop.setBackground(Color.WHITE);
		btnStop.setBorder(null);
		btnStop.setBounds(510, 52, 345, 88);
		btnStop.setIcon(new ImageIcon("icon\\stop.png"));
		btnStop.setAlignmentY(Component.CENTER_ALIGNMENT);
		btnStop.setPreferredSize(new Dimension(345, 88));
		btnStop.setVisible(false);
		pnSetting.setLayout(null);
		
		// button to start camera test in setting panel
		final JButton btnStart = new JButton("");
		btnStart.setHorizontalTextPosition(SwingConstants.CENTER);
		btnStart.setBorder(null);
		btnStart.setBackground(new Color(255, 255, 255));
		btnStart.setForeground(Color.BLACK);
		btnStart.setBounds(510, 52, 345, 88);
		pnSetting.add(btnStart);
		btnStart.setMargin(new Insets(0, 0, 0, 0));
		btnStart.setIcon(new ImageIcon("E:\\GitHub\\ce2006project\\eProctorFinal\\icon\\start.png"));
		btnStart.setAlignmentY(Component.CENTER_ALIGNMENT);
		btnStart.setPreferredSize(new Dimension(345, 88));
		
		final JLabel videoLabel = new JLabel("", new ImageIcon("icon\\webcam_icon.gif"), JLabel.CENTER);
		videoLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		videoLabel.setIcon(new ImageIcon("icon\\webcam_icon.gif"));
		videoLabel.setPreferredSize(new Dimension(512, 384));
		videoLabel.setBounds((screenSize.width - 512) / 2, 150, 512, 384);
		pnSetting.add(videoLabel);
		
		// start testing camera
		btnStart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				testVideoThread = new TestVideo(videoLabel);
				testVideoThread.start();
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
					testVideoThread.shouldEnd = true;
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// XXX Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("isAlive: " + testVideoThread.isAlive()); // kill failed?!
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
	
	// thread for testing camera, in setting tab
	
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
		
		public TestVideo(JLabel videoLabel) {
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
			    videoLabel.setIcon(new ImageIcon("icon\\webcam_icon.gif"));
			    
			    return ;
			} catch (com.googlecode.javacv.FrameGrabber.Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void addOneVideoBox (String id, JPanel panel) {
		int horizontalSpace = 8;
		int verticalSpace = 50;
		
		int btnSpace = 5;
		
		int upperLeftX = 30 + (InvigilateTab.numOfVideoBox % 4) * (horizontalSpace + 256);
		int upperLeftY = 120 + (InvigilateTab.numOfVideoBox / 4) * (verticalSpace + 192);
		
		int btnSize = 30;

		final JLabel idLabel = new JLabel("" + InvigilateTab.numOfVideoBox);
		// for displaying student's name
		// 4 in a row, each has 10 space to other label in the same row, has 50 space to other in the same column, size = 100 x 30
		idLabel.setBounds(upperLeftX, upperLeftY, 70, 30);
		idLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(idLabel);
		idLabel.setVisible(true);
		
		JButton btnWarn = new JButton("Warn");
		btnWarn.setBounds(upperLeftX + 256 - (btnSize + btnSpace) * 3, upperLeftY, btnSize, btnSize);
		idLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		btnWarn.setIcon(new ImageIcon("icon\\appbar.warning.small.jpg"));
		btnWarn.setBorder(BorderFactory.createEmptyBorder());
		btnWarn.setContentAreaFilled(false);
		btnWarn.setName(id + "#btnWarn#");
		btnWarn.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				InvigilateTab.lblStudentId.setText("StudentId: " + arg0.getComponent().getName().split("#")[0]);
				System.out.println("going to wanr: " + arg0.getComponent().getName().split("#")[0]);
				InvigilateTab.lblType.setText("Type: Warning");
			}
		});
		pnInvigilate.add(btnWarn);
		
		JButton btnEnd = new JButton("End");
		btnEnd.setBounds(upperLeftX + 256 - (btnSize + btnSpace) * 2, upperLeftY, btnSize, btnSize);
		btnEnd.setIcon(new ImageIcon("icon\\appbar.close.small.jpg"));
		btnEnd.setBorder(BorderFactory.createEmptyBorder());
		btnEnd.setContentAreaFilled(false);
		btnEnd.setName(id + "#btnEnd#");
		btnEnd.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				InvigilateTab.lblStudentId.setText("StudentId: " + arg0.getComponent().getName().split("#")[0]);
				System.out.println("going to end: " + arg0.getComponent().getName().split("#")[0]);
				InvigilateTab.lblType.setText("Type: Ending");
			}
		});
		pnInvigilate.add(btnEnd);
		
		JButton btnMsg = new JButton("Msg");
		btnMsg.setBounds(upperLeftX + 256 - (btnSize + btnSpace), upperLeftY, btnSize, btnSize);
		btnMsg.setIcon(new ImageIcon("icon\\appbar.message.small.jpg"));
		btnMsg.setBorder(BorderFactory.createEmptyBorder());
		btnMsg.setContentAreaFilled(false);
		btnMsg.setName(id + "#btnMsg#");
		btnMsg.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				InvigilateTab.lblStudentId.setText("StudentId: " + arg0.getComponent().getName().split("#")[0]);
				System.out.println("going to msg: " + arg0.getComponent().getName().split("#")[0]);
				InvigilateTab.lblType.setText("Type: Message");
			}
		});
		pnInvigilate.add(btnMsg);
		
		// for displaying student's image
		// 4 in a row, each has 10 space to other label in the same row, has 50 space to other in the same column, size = 256 x 192
		final JLabel videoLabel = new JLabel("", new ImageIcon("icon\\webcam_icon.gif"), JLabel.CENTER);
		videoLabel.setBounds(upperLeftX, upperLeftY + 30, 256, 192);
		videoLabel.setIcon(new ImageIcon("icon\\webcam_icon.gif"));
		videoLabel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(videoLabel);
		videoLabel.setVisible(true);
		
		panel.repaint();
		
		InvigilateTab.videoIdList.put(id, idLabel);
		InvigilateTab.videoBoxList.put(id, videoLabel);
		InvigilateTab.videoWarnList.put(id, btnWarn);
		InvigilateTab.videoEndList.put(id, btnEnd);
		InvigilateTab.videoMsgList.put(id, btnMsg);
		
		InvigilateTab.numOfVideoBox++;
	}

	// should be moved to controller later
	class ReceiveShow extends Thread {
		int port = 6002;
		
	    public RecordObject recordObject;
	    public ObjectInputStream sInput;
	    public ServerSocket serverSocket;
	    public Socket socket;
	    
	    public volatile boolean shouldEnd;
	  
	    public ReceiveShow () {
	    	this.shouldEnd = false;
	    	try {
				this.serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				// XXX Auto-generated catch block
				e.printStackTrace();
				System.out.println("new ServerSocket(port) failed");
				this.shouldEnd = true;
			}
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
		            
		            InvigilateTab.videoBoxList.get(recordObject.getUserId()).setIcon(new ImageIcon(toDisplay.getBufferedImage()));
		    	}
		    	socket.close();
		    	serverSocket.close();
		    	return ;
			} catch (Exception e) {
				// XXX Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}

	// here are timers
	// including periodic messages fetch
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
				
				InvigilateTab.sessionStudentList = getStudentList();
				
				if (InvigilateTab.mostRecentSession == null) {
					System.out.println("session disappeared!");
				}
				
				// start a countdown timer during the exam
				long duration = ((Date)InvigilateTab.mostRecentSession.get("end")).getTime() - ((Date)InvigilateTab.mostRecentSession.get("start")).getTime();
				System.out.println("duration: " + secondToString((int)(duration / 1000)));
				UpdateTimeToGoDuringExam updateTimeToGoDuringExam = new UpdateTimeToGoDuringExam(lblTimeToGo, (int)duration / 1000);
				timer = new Timer((int)duration, updateTimeToGoDuringExam);
				timer.setInitialDelay(0);
				timer.setDelay(1000);
				timer.start();
				
				//
				// put here for test purpose
				//
				InvigilateTab.showWhenStart();		
				
				if (InvigilateTab.sessionStudentList == null) {
					System.out.println("sessionStudentList == null, getStudentList failed");
				} else if (InvigilateTab.sessionStudentList.size() == 0) {
					System.out.println("sessionStudentList.size() == 0");
					lblTimeToGo.setText("this sesion has no student...");
				} else {
					System.out.println("sessionStudentList != null");
					lblTimeToGo.setText("here you go...");
									
					Iterator<Entry<ObjectId, DBObject>> iter = InvigilateTab.sessionStudentList.entrySet().iterator();
					Entry<ObjectId, DBObject> temp = null;
					while (iter.hasNext()) {
						System.out.println("add one");
						temp = iter.next();
						addOneVideoBox((String)temp.getValue().get("username"), pnInvigilate);
					}
					
//					videoReceiveShowThread = new ReceiveShow();
					
//					InvigilateTab.textField.setVisible(true);
//					InvigilateTab.btnSend.setVisible(true);
//					InvigilateTab.lblStudentId.setVisible(true);
//					InvigilateTab.lblType.setVisible(true);
				}
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
					InvigilateTab.lblIncomingMsg.setText("<html>" + allTogether + "</html>");
				}
			} else if (secondToGo == 0) {
				timer.stop();
				
				lblTimeToGo.setText("exam ended. please sign");
				InvigilateTab.btnSign.setVisible(true);
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
		if (InvigilateTab.mostRecentSession == null)
			return -1;
		
		Date mostRecentStartTime = (Date) InvigilateTab.mostRecentSession.get("start");
		
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
		DBCursor sessionsCursor = Main.mongoHQ.record.find(new BasicDBObject("user_id", new ObjectId("532541929862bcab1a0000fd")), new BasicDBObject("session_id", 1));
				
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
			InvigilateTab.mostRecentSession = null;
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
		InvigilateTab.mostRecentSession = Main.mongoHQ.session.findOne(new BasicDBObject("_id", mostRecentSession));
		return ;
	}
	public TreeMap<ObjectId, DBObject> getStudentList() {
		TreeMap<ObjectId, DBObject> sessionStudentList = new TreeMap<ObjectId, DBObject>();
		DBCursor studentCursor = null;
		
		getMostRecentSession();
		DBObject mostRecentSession = InvigilateTab.mostRecentSession;
		if (mostRecentSession != null) {
			BasicDBObject query = new BasicDBObject("session_id", mostRecentSession.get("_id")).append("domain", "Student");
			studentCursor = Main.mongoHQ.record.find(query);
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


	// here are components of invigilate tab
	static class InvigilateTab {
		public static DBObject mostRecentSession;
		public static TreeMap<ObjectId, DBObject> sessionStudentList = new TreeMap<ObjectId, DBObject>();

		public static int numOfVideoBox = 0;
		
		public static TreeMap<String, JLabel> videoBoxList = new TreeMap<String, JLabel>();
		public static TreeMap<String, JLabel> videoIdList = new TreeMap<String, JLabel>();
		public static TreeMap<String, JButton> videoWarnList = new TreeMap<String, JButton>();
		public static TreeMap<String, JButton> videoEndList = new TreeMap<String, JButton>();
		public static TreeMap<String, JButton> videoMsgList = new TreeMap<String, JButton>();
		
		public static JTextField textField;
		public static JButton btnSend;
		public static JLabel lblType;
		public static JLabel lblStudentId;
		public static JButton btnSign;
		public static JLabel lblIncomingMsg;
		
		public static void hideWhenEnd() {
			InvigilateTab.textField.setText("");
			InvigilateTab.textField.setVisible(false);
			InvigilateTab.btnSend.setVisible(false);
			InvigilateTab.lblType.setText("");
			InvigilateTab.lblType.setVisible(false);
			InvigilateTab.lblStudentId.setText("");
			InvigilateTab.lblStudentId.setVisible(false);
			InvigilateTab.lblIncomingMsg.setText("");
			InvigilateTab.lblIncomingMsg.setVisible(false);
			
			Iterator<String> iter = videoBoxList.keySet().iterator();
			String temp = null;
			while (iter.hasNext()) {
				temp = iter.next();
				videoBoxList.get(temp).setVisible(false);
				videoBoxList.remove(temp);
			}

			iter = videoIdList.keySet().iterator();
			while (iter.hasNext()) {
				temp = iter.next();
				videoIdList.get(temp).setVisible(false);
				videoIdList.remove(temp);
			}
			
			iter = videoWarnList.keySet().iterator();
			while (iter.hasNext()) {
				temp = iter.next();
				videoWarnList.get(temp).setVisible(false);
				videoWarnList.remove(temp);
			}
			
			iter = videoEndList.keySet().iterator();
			while (iter.hasNext()) {
				temp = iter.next();
				videoEndList.get(temp).setVisible(false);
				videoEndList.remove(temp);
			}
			
			iter = videoMsgList.keySet().iterator();
			while (iter.hasNext()) {
				temp = iter.next();
				videoMsgList.get(temp).setVisible(false);
				videoMsgList.remove(temp);
			}
		}
		
		public static void showWhenStart() {
			InvigilateTab.textField.setVisible(true);
			InvigilateTab.btnSend.setVisible(true);
			InvigilateTab.lblType.setVisible(true);
			InvigilateTab.lblStudentId.setVisible(true);
			InvigilateTab.lblIncomingMsg.setVisible(true);
		}
	}

	class Message {
		
	}
}

