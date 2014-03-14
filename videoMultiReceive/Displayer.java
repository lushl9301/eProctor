import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

import static com.googlecode.javacv.cpp.opencv_core.cvFlip;

public class Displayer implements Runnable {    
	public static CanvasFrame canvas;
	public static HashMap<String, CanvasFrame> canvasList;
	public int port;
	public String proctorId = "aaaa";
	public String requestId;
	public static Socket socket;
	private ObjectInputStream sInput;
    private ObjectOutputStream sOutput;
	
    public Displayer(int port, String requestId, HashMap<String, CanvasFrame> canvasList) {
        this.port = port;
        this.requestId = requestId;
        Displayer.canvasList = canvasList;
        new Thread(this, requestId).start();
    }

    @Override
    public void run() {
        try {
            RequestObject requestObject = new RequestObject(proctorId,requestId);
            System.out.println(requestObject);
            while (true) {
                socket = new Socket("localhost", port);
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sOutput.writeObject(requestObject);
                // sOutput.close();
                if ((canvas = canvasList.get(requestId)) == null) {
                    canvas = new CanvasFrame(requestId);
                    canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
                    canvasList.put(requestId, canvas);
                }
                sInput = new ObjectInputStream(socket.getInputStream());
                RecordObject recordObject = (RecordObject) sInput.readObject();
                //sInput.close();
                socket.close();
                byte[] imageBytes = recordObject.getImageBytes();
                BufferedImage bufC = ImageIO.read(new ByteArrayInputStream(imageBytes));
                IplImage toDisplay = IplImage.createFrom(bufC);
                cvFlip(toDisplay, toDisplay, 1);
                canvas.showImage(toDisplay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


/*
class DisplayerThread extends Thread {
    public CanvasFrame canvas;
    public RecordObject recordObject;
    public ObjectInputStream sInput;
    public String requestId;
    public ServerSocket serverSocket;
    public Socket socket;

    DisplayerThread(CanvasFrame canvas, int port) throws IOException {
        this.canvas = canvas;
        serverSocket = new ServerSocket(port);
        System.out.println("dislpayer starting...");
    }

    public DisplayerThread(Socket socket2) {
        // TODO Auto-generated constructor stub
    }

    public void run() {
        try {
            while (true) {
              socket = serverSocket.accept();
              
              sInput = new ObjectInputStream(socket.getInputStream());
              RecordObject recordObject = (RecordObject) sInput.readObject();
              socket.close();
              
              BufferedImage buf = ImageIO.read(new ByteArrayInputStream(recordObject.getImageBytes()));
              IplImage toDisplay = IplImage.createFrom(buf);
              cvFlip(toDisplay, toDisplay, 1);
              canvas.showImage(toDisplay);
            }
          } catch (Exception e) {
              e.printStackTrace();
          }
    }
  }*/
