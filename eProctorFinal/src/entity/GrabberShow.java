package entity;

import static com.googlecode.javacv.cpp.opencv_core.*;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

import entity.RecordObject;

public class GrabberShow extends Thread {
    IplImage image;
    //public CanvasFrame canvas = new CanvasFrame("Web Cam");
    public boolean shouldEnd = false;
    private ObjectOutputStream sOutput;
    private int port;
    private String userId;
    private Socket socket;
    private JLabel videoBox;

    public GrabberShow(int port, String student_id, JLabel videoBox) {
        this.port = port;
        this.userId = student_id;
        this.videoBox = videoBox;
        this.start();
        //canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        //new Thread(this, "send image").start();
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
            while (!shouldEnd) {
                img = grabber.grab();
                buf = img.getBufferedImage();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(buf, "jpg", baos);
                baos.flush();
                byte[] imageBytes = baos.toByteArray();
                baos.close();

                if (img != null) {
                    cvFlip(img, img, 1);// l-r = 90_degrees_steps_anti_clockwise
                    videoBox.setIcon(new ImageIcon(img.getBufferedImage()));
                    //canvas.showImage(img);
                    send(imageBytes);
                }
            }
        } catch (Exception e) {
        }
    }

    public void send(byte[] imageBytes) throws Exception {
        socket = new Socket("localhost", port);
        sOutput = new ObjectOutputStream(socket.getOutputStream());
        RecordObject recordObject = new RecordObject(this.userId, imageBytes);
        sOutput.writeObject(recordObject);
        socket.close();
    }
}