package student_side;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import entity.Main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StudentHomeUI extends JFrame {

    private JTable tableReview;

    public StudentHomeUI() throws Exception {
        addWindowFocusListener(new WindowFocusListener() {
            public void windowGainedFocus(WindowEvent arg0) {
            }

            public void windowLostFocus(WindowEvent arg0) {
                toFront();
            }
        });
        initialize();
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
        pnStatus.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                // Main.studentHomeController.getInformation();
                // Main.studentHomeController.getRecentMessage();
            }
        });
        tabbedPane.addTab("Status", null, pnStatus, null);
        pnStatus.setLayout(null);

        JTextPane txtpnInfomation = new JTextPane();
        // txtpnInfomation.setText("Welcome, Gong Yue from CE2006 BCE2\r\n  \r\n  You have:   2 exams in the next week.\r\n                      5 exams in the next few months.\r\n                      1 unread exam result.");
        txtpnInfomation.setText(Main.studentHomeController.getInformation());

        txtpnInfomation.setBounds(screenSize.width / 2 - 420, 70, 400, 450);
        pnStatus.add(txtpnInfomation);

        JLabel lblInformation = new JLabel("Information");
        lblInformation.setBounds(screenSize.width / 2 - 420, 50, 150, 18);
        pnStatus.add(lblInformation);

        JLabel lblRecentMessages = new JLabel("Recent Messages");
        lblRecentMessages.setBounds(screenSize.width / 2, 50, 150, 18);
        pnStatus.add(lblRecentMessages);

        JTextPane txtpnRecentMessages = new JTextPane();
        txtpnRecentMessages.setText(Main.studentHomeController
                .getRecentMessage());
        txtpnRecentMessages.setBounds(screenSize.width / 2, 70, 400, 450);
        pnStatus.add(txtpnRecentMessages);

        JPanel pnExam = new JPanel();
        tabbedPane.addTab("Exam", null, pnExam, null);
        pnExam.setLayout(null);

        JEditorPane dtrpnExampaper = new JEditorPane();
        dtrpnExampaper.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dtrpnExampaper.setEditable(false);

        JScrollPane spExampaper = new JScrollPane(dtrpnExampaper);
        spExampaper.setBounds(screenSize.width / 2 - 420, 70, 600,
                screenSize.height - 200);
        pnExam.add(spExampaper);
        try {
            dtrpnExampaper
                    .setPage(Main.studentHomeController.getExamLink());
        } catch (IOException e) {
            dtrpnExampaper.setContentType("text/html");
            dtrpnExampaper.setText("<html>Could not load exam papers</html>");
        }

        JPanel pnBooking = new JPanel();
        tabbedPane.addTab("Booking", null, pnBooking, null);
        pnBooking.setLayout(null);

        JLabel lblCurrentBookings = new JLabel("Current Bookings");
        lblCurrentBookings.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblCurrentBookings.setBounds(screenSize.width / 2 - 420, 50, 150, 22);
        pnBooking.add(lblCurrentBookings);

        final JList<String> listCurrentBookings = new JList<String>();
        listCurrentBookings
                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listCurrentBookings.setModel(new AbstractListModel<String>() {
            String[] values = Main.studentHomeController
                    .getCurrentBookingList();

            public int getSize() {
                return values.length;
            }

            public String getElementAt(int index) {
                return values[index];
            }
        });
        listCurrentBookings.setBounds(screenSize.width / 2 - 420, 78, 600, 213);
        pnBooking.add(listCurrentBookings);

        JButton btnMakeARequest = new JButton("Make A Request");
        btnMakeARequest.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = listCurrentBookings.getSelectedIndex();
                Main.studentHomeController.makeRequestOfABooking(index);
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

        final JList<String> listAvailableCourses = new JList<String>();
        listAvailableCourses
                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listAvailableCourses.setModel(new AbstractListModel<String>() {
            String[] values = Main.studentHomeController
                    .getAvailableCourseList();

            public int getSize() {
                return values.length;
            }

            public String getElementAt(int index) {
                return values[index];
            }
        });
        listAvailableCourses.setBounds(screenSize.width / 2 - 420, 381, 405,
                174);
        pnBooking.add(listAvailableCourses);

        JLabel lblAvailableSessions = new JLabel("Available Sessions");
        lblAvailableSessions.setBounds(screenSize.width / 2 - 5, 352, 150, 18);
        pnBooking.add(lblAvailableSessions);

        JList<String> listAvailableSessions = new JList<String>();
        listAvailableSessions
                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listAvailableSessions.setModel(new AbstractListModel<String>() {
            String[] values = Main.studentHomeController
                    .getAvailableExamSession(listAvailableCourses
                            .getSelectedIndex());

            public int getSize() {
                return values.length;
            }

            public String getElementAt(int index) {
                return values[index];
            }
        });
        listAvailableSessions
                .setBounds(screenSize.width / 2 - 5, 381, 240, 174);
        pnBooking.add(listAvailableSessions);

        JButton btnOk = new JButton("OK");
        btnOk.setBounds(screenSize.width / 2 + 261, 403, 89, 30);
        pnBooking.add(btnOk);

        JButton btnReset = new JButton("Reset");
        btnReset.setBounds(screenSize.width / 2 + 261, 450, 89, 30);
        pnBooking.add(btnReset);

        JButton btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(screenSize.width / 2 + 261, 497, 89, 30);
        pnBooking.add(btnRefresh);

        JSeparator separator = new JSeparator();
        separator.setBounds(screenSize.width / 2 - 420, 302, 770, 10);
        pnBooking.add(separator);

        JPanel pnReview = new JPanel();
        tabbedPane.addTab("Review", null, pnReview, null);
        pnReview.setLayout(null);

        JButton btnCheckDetails = new JButton("Check Details");
        btnCheckDetails.setBounds(screenSize.width / 2 + 210, 73, 150, 30);
        pnReview.add(btnCheckDetails);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(screenSize.width / 2 - 420, 70, 600, 290);
        pnReview.add(scrollPane);

        setTableReview(new JTable());
        scrollPane.setViewportView(getTableReview());
        getTableReview().setModel(
                new DefaultTableModel(new Object[][] {
                        { "43901341534", "CE2006 Software Engineering",
                                "21/4/2014 1530-1730", "A", "N.A." },
                        { null, "", null, null, null }, }, new String[] {
                        "Record ID", "Course", "Session", "Grade", "Remark" }) {
                    Class[] columnTypes = new Class[] { String.class,
                            String.class, String.class, String.class,
                            String.class };

                    public Class getColumnClass(int columnIndex) {
                        return columnTypes[columnIndex];
                    }
                });
        getTableReview().getColumnModel().getColumn(0).setPreferredWidth(100);
        getTableReview().getColumnModel().getColumn(1).setPreferredWidth(200);
        getTableReview().getColumnModel().getColumn(2).setPreferredWidth(170);
        getTableReview().getColumnModel().getColumn(3).setPreferredWidth(40);
        getTableReview().getColumnModel().getColumn(4).setPreferredWidth(90);

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
                Main.studentHomeController.logout();
            }
        });
        mnFile.add(mntmLogout);

        JMenuItem mntmExit = new JMenuItem("Exit");
        mntmExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Main.studentHomeController.exit();
            }
        });
        mnFile.add(mntmExit);

        JMenu mnHelp = new JMenu("Help");
        menuBar.add(mnHelp);

        JMenuItem mntmAbout = new JMenuItem("About");
        mntmAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Main.studentHomeController.getAboutMessage();
            }
        });
        mnHelp.add(mntmAbout);

        graphicsDevice.setFullScreenWindow(this);
        validate();
    }

    public JTable getTableReview() {
        return tableReview;
    }

    public void setTableReview(JTable tableReview) {
        this.tableReview = tableReview;
    }
}
