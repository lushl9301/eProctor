package server_side;

import java.io.ObjectInputStream;
import java.net.*;
import java.util.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class VideoServerThread implements Runnable {
    private HashMap<String, ReceiveImg> threadHashMap;
    private int port;
    private static ServerSocket serverSocket;
    private Socket receiveUserIdSocket;
    private ObjectInputStream sInput;

    public VideoServerThread(int port) {
        this.port = port;
        threadHashMap = new HashMap<String, ReceiveImg>();
        new Thread(this, "videoserver").start();
    }
    
    public void run() {
        try {
            System.out.println("here");
            serverSocket = new ServerSocket(port);

            System.out.println("start receiveImg...");
            receiveUserIdSocket = serverSocket.accept();
            sInput = new ObjectInputStream(receiveUserIdSocket.getInputStream());
            String userId = (String) sInput.readObject();
            
            ReceiveImg receiveImg = new ReceiveImg(serverSocket, threadHashMap);
            threadHashMap.put(userId, receiveImg);
            receiveImg.start();
        }
        catch (Exception e) {
        	e.printStackTrace();
        }
    }
}

class ReceiveImg extends Thread {
    public HashMap<String, ReceiveImg> threadHashMap; 
    private ServerSocket serversocket;
    private Socket socket;
    private ObjectInputStream sInput;
    public CanvasFrame canvas;
    ReceiveImg(ServerSocket serversocket, HashMap<String, ReceiveImg> threadHashMap) {
        this.serversocket = serversocket;
        this.threadHashMap = threadHashMap;
        System.out.println("newnewnew");
    }

    public void run() {
    	try {
    		canvas = new CanvasFrame("Web Cam On server");
            while (true) {
            	socket = serversocket.accept();
            	sInput = new ObjectInputStream(socket.getInputStream());
                String userId = (String) sInput.readObject();
                BufferedImage buf = ImageIO.read(socket.getInputStream());
                socket.close();
                IplImage toDisplay = IplImage.createFrom(buf);
                ReceiveImg t = threadHashMap.get(userId);
                t.canvas.showImage(toDisplay);
            }
    	} catch (Exception e) {
            e.printStackTrace();
        }
    }
}