package videoMultiThreadSerializable;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import static com.googlecode.javacv.cpp.opencv_core.cvFlip;

public class Displayer {    
	public static CanvasFrame canvas1;
	
	public HashMap<String, DisplayerThread> displayerList = new HashMap<String, DisplayerThread>();
	public static HashMap<String, CanvasFrame> canvasList = new HashMap<String, CanvasFrame>();

//	public static ServerSocket serverSocket;
//	public static Socket socket;
//    public static ObjectInputStream sInput;
	
	public static void main(String[] args) throws Exception {
		CanvasFrame aaa = new CanvasFrame("AAA");
		canvasList.put("AAA", aaa);
		DisplayerThread displayer1 = new DisplayerThread(aaa, 6002);
		displayer1.start();
		
		CanvasFrame bbb = new CanvasFrame("BBB");
		canvasList.put("BBB", bbb);
		DisplayerThread displayer2 = new DisplayerThread(bbb, 6003);
		displayer2.start();
		
//		serverSocket = new ServerSocket(6002);
//		
//		while (true) {
//			socket = serverSocket.accept();
//			sInput = new ObjectInputStream(socket.getInputStream());
//            RecordObject recordObject = (RecordObject) sInput.readObject();
//            socket.close();
//            
//            String userId = recordObject.getUserId();
//            
//            if (canvasList.get(userId) == null) {
//            	CanvasFrame canvas = new CanvasFrame(userId);
//            	canvasList.put(userId, canvas);
//            }
//            BufferedImage buf = ImageIO.read(new ByteArrayInputStream(recordObject.getImageBytes()));
//		    IplImage toDisplay = IplImage.createFrom(buf);
//			canvasList.get(userId).showImage(toDisplay);
//		}
	}
}

class DisplayerThread extends Thread {
  public CanvasFrame canvas;
  public RecordObject recordObject;
  public ObjectInputStream sInput;
    
  public ServerSocket serverSocket;
  public Socket socket;

  DisplayerThread(CanvasFrame canvas, int port) throws IOException {
	  this.canvas = canvas;
	  serverSocket = new ServerSocket(port);
	  System.out.println("dislpayer starting...");
  }

  public void run() {
	  try {
	      while (true) {
			socket = serverSocket.accept();
			
	        sInput = new ObjectInputStream(socket.getInputStream());
	        RecordObject recordObject = (RecordObject) sInput.readObject();
	        socket.close();
	        	        
//	        if (canvasIndex == null) {
//		        canvasIndex = recordObject.getUserId();
//		        canvas = new CanvasFrame(canvasIndex);
//	      	}
	        
            BufferedImage buf = ImageIO.read(new ByteArrayInputStream(recordObject.getImageBytes()));
            IplImage toDisplay = IplImage.createFrom(buf);
            cvFlip(toDisplay, toDisplay, 1);
            canvas.showImage(toDisplay);
	      }
		} catch (Exception e) {
			e.printStackTrace();
		}
  }
}