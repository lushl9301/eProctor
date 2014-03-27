package entity;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.bson.types.ObjectId;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

import static com.googlecode.javacv.cpp.opencv_core.cvFlip;

public class Displayer extends Thread {    
	public static JLabel videoLabel;
	public static TreeMap<String, JLabel> labelList;
	public boolean shouldEnd = false;
	public int port;
	public String proctorId = Main.user_id.toString();
	public ArrayList<ObjectId> requestIdList;
	public static Socket socket;
	private ObjectInputStream sInput;
    private ObjectOutputStream sOutput;
    public Displayer(ArrayList<ObjectId> requestIdList, TreeMap<String, JLabel> labelList, int port) {
    	this.port = port;
        this.requestIdList = requestIdList;
        Displayer.labelList = labelList;
        this.start();
    }

	@Override
    public void run() {
        try {
        	RequestObject requestObject = null;
            
        	System.out.println(requestObject);
			while (!shouldEnd) {
				sleep(100);
				synchronized (this.requestIdList) {
				Iterator<ObjectId> iter = requestIdList.iterator();
				while (iter.hasNext()) {
					ObjectId temp = iter.next();
					requestObject = new RequestObject(proctorId, temp.toString());
					socket = new Socket("localhost", port);
					
					sOutput = new ObjectOutputStream(socket.getOutputStream());
					sOutput.writeObject(requestObject);
					videoLabel = labelList.get(temp.toString());
					sInput = new ObjectInputStream(socket.getInputStream());
					RecordObject recordObject = (RecordObject) sInput.readObject();
					socket.close();
					byte[] imageBytes = recordObject.getImageBytes();
					BufferedImage bufC = ImageIO.read(new ByteArrayInputStream(
							imageBytes));
					IplImage toDisplay = IplImage.createFrom(bufC);
					cvFlip(toDisplay, toDisplay, 1);
					if (videoLabel != null) {
						videoLabel.setIcon(new ImageIcon(toDisplay.getBufferedImage()));
					} else {
						System.out.println("here is Displayer: videoLabel == null");
					}
				}
			} }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
