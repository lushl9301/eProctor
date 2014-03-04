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

    private int port = Main.port1;
    private ObjectInputStream sInput; // to read from the socket
    private ObjectOutputStream sOutput; // to write on the socket
    private Socket socket;
    private String server = "localhost";
    public static ArrayList<ArrayList<String>> receivedMsg = null;

    public boolean verifyUser(String username, String mD5Password, String domain) {
        return true;
    }

    public ArrayList<ArrayList<String>> fetchData(String tableName, String key,
            ArrayList<String> inFromControl) throws Exception {
        socket = new Socket(server, port);

        sInput = new ObjectInputStream(socket.getInputStream());
        sOutput = new ObjectOutputStream(socket.getOutputStream());

        ArrayList<String> info = new ArrayList<String>();
        info.add(tableName);
        info.add(key);
        ArrayList<ArrayList<String>> msg = new ArrayList<ArrayList<String>>();
        msg.add(info);
        msg.add(inFromControl);
        sOutput.writeObject(new entity.ChatMessage(entity.ChatMessage.QUERY, msg));

        @SuppressWarnings("unchecked")
        ArrayList<ArrayList<String>> readObject = (ArrayList<ArrayList<String>>) sInput
                .readObject();
        receivedMsg = readObject;

        sInput.close();
        sOutput.close();
        socket.close();
//        for (ArrayList<String> s : receivedMsg)
//            for (String a : s)
//                System.out.println(a);
        return receivedMsg;
    }

    public void sendMessage(String receiver, String message) throws Exception {
        socket = new Socket(server, port);

        String info = "Connection accepted " + socket.getInetAddress() + ":"
                + socket.getPort();
        System.out.println(info);

        sInput = new ObjectInputStream(socket.getInputStream());
        sOutput = new ObjectOutputStream(socket.getOutputStream());
        ArrayList<String> send = new ArrayList<String>();
        ArrayList<ArrayList<String>> msg = new ArrayList<ArrayList<String>>();
        send.add(receiver);
        send.add(message);
        msg.add(send);
        sOutput.writeObject(new entity.ChatMessage(entity.ChatMessage.MESSAGE, msg));

        //assume there is not return;
        sInput.close();
        sOutput.close();
        socket.close();
    }
    
    public void updateData(String tableName, String key, String examId, ArrayList<String> bookingInfo) throws Exception {
        socket = new Socket(server, port);

        sInput = new ObjectInputStream(socket.getInputStream());
        sOutput = new ObjectOutputStream(socket.getOutputStream());

        ArrayList<String> info = new ArrayList<String>();
        info.add(0, examId);
        info.add(0, key);
        info.add(0, tableName);
        ArrayList<ArrayList<String>> msg = new ArrayList<ArrayList<String>>();
        msg.add(info);
        msg.add(bookingInfo);
        sOutput.writeObject(new entity.ChatMessage(entity.ChatMessage.UPDATE, msg));

        //assume there is no reply
        sInput.close();
        sOutput.close();
        socket.close();
    }

}

class GrabberShow implements Runnable {
    IplImage image;
    CanvasFrame canvas = new CanvasFrame("Web Cam");
    int port = Main.port2;

    public GrabberShow() {
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        new Thread(this, "send image").start();
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