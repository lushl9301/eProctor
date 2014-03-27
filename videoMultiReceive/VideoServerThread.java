import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.imageio.ImageIO;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

import entity.RecordObject;

public class VideoServerThread implements Runnable {
//    private HashMap<String, PassImg> passHashMap;
    private HashMap<String, RecordObject> receivedList;
    
    private int port;
    private static ServerSocket serverSocket;
    private Socket socket;
    private ObjectInputStream sInput;
    
    public VideoServerThread(int port, HashMap<String, RecordObject> receivedList) {
        this.port = port;
        this.receivedList = receivedList;
//        passHashMap = new HashMap<String, PassImg>();
        new Thread(this, "videoserver").start();
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);

            System.out.println("start receiveImg...");
            while (true) {
                socket = serverSocket.accept();
                sInput = new ObjectInputStream(socket.getInputStream());
                RecordObject recordObject = (RecordObject) sInput.readObject();
                socket.close();
                
                String userId = recordObject.getUserId();
                receivedList.put(userId, recordObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//
//class PassImg extends Thread {
//	public HashMap<String, RecordObject> receivedList;
//	public String userId;
//	public ObjectOutputStream sOutput;
//	public Socket socket;
//	
//  PassImg(HashMap<String, RecordObject> receivedList, String userId) {
//	  this.receivedList = receivedList;
//	  this.userId = userId;
//  }
//
//  public void run() {
//	  try {
//		Thread.sleep(2000);
//	} catch (InterruptedException e1) {
//		e1.printStackTrace();
//	}
//      try {
//		  while (true) {
//			if (receivedList.containsKey(userId) && receivedList.get(userId) != null) {
//				socket = new Socket("localhost", 6001);
//				sOutput = new ObjectOutputStream(socket.getOutputStream());
//				sOutput.writeObject(receivedList.get(userId));
//				socket.close();
//			}	   
//		  }
//      } catch (Exception e) {
//          e.printStackTrace();
//      }
//  }
//}