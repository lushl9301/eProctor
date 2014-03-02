import java.net.*;
import java.util.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class VideoServerThread implements Runnable {
    private static ArrayList<ReceiveImg> ril;
    private int port;
    private static ServerSocket serverSocket;

    public VideoServerThread(int port) {
        this.port = port;
        ril = new ArrayList<ReceiveImg>();
        new Thread(this, "videoserver").start();
    }
    
    public void run() {
        try {
            System.out.println("here");
            serverSocket = new ServerSocket(port);

            System.out.println("start receiveImg...");
            ReceiveImg t = new ReceiveImg(serverSocket);
            ril.add(t);
            new Thread(t).start();
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
    }
}

class ReceiveImg implements Runnable {
    ServerSocket serversocket;
    Socket socket;
    ReceiveImg(ServerSocket serversocket) {
        this.serversocket = serversocket;
        System.out.println("newnewnew");
    }

    public void run() {
    	try {
    		CanvasFrame canvas = new CanvasFrame("Web Cam On server");
            while (true) {     
            	socket = serversocket.accept();
                BufferedImage buf = ImageIO.read(socket.getInputStream());
                socket.close();
                IplImage toDisplay = IplImage.createFrom(buf);
                canvas.showImage(toDisplay);
            }
    	} catch (Exception e) {
            e.printStackTrace();
        }
    }
}