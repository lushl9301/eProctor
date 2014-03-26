import static com.googlecode.javacv.cpp.opencv_core.*;

import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

import javax.imageio.ImageIO;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class GrabberShow extends Thread {
    IplImage image;
    public CanvasFrame canvas = new CanvasFrame("Web Cam");
    public boolean shouldEnd = false;
    private ObjectOutputStream sOutput;
    private int port = MainForGrabberShow.port2;
    private String userId;
    private Socket socket;

    public GrabberShow(int port, String userId) {
        this.port = port;
        this.userId = userId;
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        this.start();
        //new Thread(this, "send image").start();
    }

    public GrabberShow(int port) {
        this.port = port;
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        new Thread(this, "send image").start();
    }

    public GrabberShow(String studentId) {
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
                    canvas.showImage(img);
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