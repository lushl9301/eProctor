import static com.googlecode.javacv.cpp.opencv_core.cvFlip;

import java.io.ByteArrayInputStream;
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
    private Socket socket;
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
            while (true) {
                socket = serverSocket.accept();
                sInput = new ObjectInputStream(socket.getInputStream());
                RecordObject recordObject = (RecordObject) sInput.readObject();
                socket.close();
                String userId = recordObject.getUserId();
                ReceiveImg receiveImg = threadHashMap.get(userId);
                if (receiveImg == null) {
                    receiveImg = new ReceiveImg(threadHashMap);
                    threadHashMap.put(userId, receiveImg);
                }
                receiveImg.recordObject = recordObject;
                receiveImg.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ReceiveImg extends Thread {
    public HashMap<String, ReceiveImg> threadHashMap;
    public CanvasFrame canvas;
    public RecordObject recordObject;

    ReceiveImg(HashMap<String, ReceiveImg> threadHashMap) {
        this.threadHashMap = threadHashMap;
        canvas = new CanvasFrame("Web Cam On server");
        System.out.println("newnewnew");
    }

    public void run() {
        try {
            // System.out.print("here");
            byte[] imageBytes = recordObject.getImageBytes();
            BufferedImage bufC = ImageIO.read(new ByteArrayInputStream(
                    imageBytes));
            IplImage toDisplay = IplImage.createFrom(bufC);
            ReceiveImg t = threadHashMap.get(recordObject.getUserId());
            // t.canvas.showImage(toDisplay);
            cvFlip(toDisplay, toDisplay, 1);
            canvas.showImage(toDisplay);

            this.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}