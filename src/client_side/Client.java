package client_side;

import java.io.*;
import java.util.*;

import static com.googlecode.javacv.cpp.opencv_core.*;

import java.awt.image.BufferedImage;
import java.net.Socket;

import javax.imageio.ImageIO;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class Client {

    private ObjectInputStream sInput;       // to read from the socket
    private ObjectOutputStream sOutput;     // to write on the socket
    private Socket socket;
    private String server = "localhost";
    public static ArrayList<ArrayList<String>> receivedMsg = null;

    public boolean verifyUser(String username, String mD5Password, String domain) {
        return true;
    }
    public ArrayList<ArrayList<String>> fetchData(int port, String tableName, String key, ArrayList<String> inFromControl) throws IOException {
        try {
            socket = new Socket(server, port);
        
            String info = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
            System.out.println(info);
    
            sInput  = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
            
	        inFromControl.add(0, key);
	        inFromControl.add(0, tableName);
	        ArrayList<ArrayList<String>> msg = new ArrayList<ArrayList<String>>();
	        msg.add(inFromControl);
	        sOutput.writeObject(new ChatMessage(ChatMessage.MESSAGE, msg));
        
            @SuppressWarnings("unchecked")
            ArrayList<ArrayList<String>> readObject = (ArrayList<ArrayList<String>>) sInput.readObject();
            receivedMsg = readObject;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        sInput.close();
        sOutput.close();
        socket.close();
        for (ArrayList<String> s : receivedMsg)
            for (String a : s)
                System.out.println(a);
        return receivedMsg;
    }

}

class GrabberShow implements Runnable {
    IplImage image;
    CanvasFrame canvas = new CanvasFrame("Web Cam");
    int port;
    public GrabberShow(int port) {
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        new Thread(this, "send image").start();
        this.port = port;
    }
    
    public void run() {
    	FrameGrabber grabber = null;
		try {
			grabber = FrameGrabber.createDefault(0);
		} catch (com.googlecode.javacv.FrameGrabber.Exception e1) {
			e1.printStackTrace();
		}
        try {
            grabber.start();
            IplImage img;
            BufferedImage buf;
            while (true) {
                img = grabber.grab();
                buf = img.getBufferedImage();
                if (img != null) {
                    cvFlip(img, img, 1);// l-r = 90_degrees_steps_anti_clockwise
                    canvas.showImage(img);
                    send(buf);
                }
            }
        } catch (Exception e) {
        }
   }
    
    public void send(BufferedImage buf) throws Exception {
	    Socket socket = new Socket("localhost", port);
	    ImageIO.write(buf, "JPG", socket.getOutputStream());
	    socket.close();
  }
}