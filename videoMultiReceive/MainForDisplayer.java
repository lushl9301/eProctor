//import java.awt.EventQueue;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.awt.BorderLayout;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.border.EmptyBorder;
//import javax.swing.JLabel;
//
//import com.googlecode.javacv.CanvasFrame;
//
//public class MainForDisplayer extends JFrame {
//    static int port2 = 6002;
//    public static HashMap<String, JLabel> labelList;
//    public static ArrayList<String> requestIdList;
//    private JPanel contentPane;
//    private static Displayer d;
//    
//    public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					MainForDisplayer frame = new MainForDisplayer();
//			        d = new Displayer(port2, requestIdList, labelList);
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
//    
//    public MainForDisplayer() {
//    	
//    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 604, 569);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
//		contentPane.setLayout(null);
//		
//		JLabel lbl1 = new JLabel("New label");
//		lbl1.setBounds(12, 12, 592, 545);
//		contentPane.add(lbl1);
//    	
//        labelList = new HashMap<String, JLabel>();
//        labelList.put("AAAA", lbl1);
//        requestIdList = new ArrayList<String>();
//        requestIdList.add("AAAA");
//    }
//}