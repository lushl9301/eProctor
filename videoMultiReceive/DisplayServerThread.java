import entity.RecordObject;
import entity.RequestObject;

import java.io.ObjectInputStream;
import java.util.*;
import java.io.*;
import java.net.*;


public class DisplayServerThread implements Runnable {

    private HashMap<String, RecordObject> receivedList;

    private int port;
    private static ServerSocket serverSocket;
    private Socket socket;
    private ObjectInputStream sInput;

    public DisplayServerThread(int port, HashMap<String, RecordObject> receivedList) {
        this.port = port;
        this.receivedList = receivedList;
        new Thread(this, "displayServer").start();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Start dispatching image...");
            
            while (true) {
                socket = serverSocket.accept();
                
                sInput = new ObjectInputStream(socket.getInputStream());
                RequestObject requestObject = (RequestObject) sInput.readObject();
                String requestId = requestObject.requestId;
                SendVideoThread t = new SendVideoThread(socket);
                RecordObject recordObject = receivedList.get(requestId);
                if (recordObject != null) {
                    t.recordObject = recordObject;
                    t.start();
                } else {
                    // if no video from student
                    // nothing to do
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class VideoRequest {
    public String proctorId;
    public String requestId;
    public SendVideoThread dt;
    public VideoRequest(String proctorId, String requestId, SendVideoThread t) {
        this.proctorId = proctorId;
        this.requestId = requestId;
        this.dt = t;
    }
    
}

class SendVideoThread extends Thread {
    public RecordObject recordObject;
    public Socket socket;
    public ObjectOutputStream sOutput;
    
    public SendVideoThread(Socket socket) {
        this.socket = socket;
    }
    
    public void run() {
        try {
            sOutput = new ObjectOutputStream(socket.getOutputStream());
            sOutput.writeObject(recordObject);
            socket.close();
            this.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}