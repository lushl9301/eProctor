package client_side;

import static com.googlecode.javacv.cpp.opencv_core.cvFlip;

import java.awt.image.BufferedImage;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.imageio.ImageIO;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

import entity.Main;

public class GrabberShow implements Runnable {
    IplImage image;
    public CanvasFrame canvas = new CanvasFrame("Web Cam");
    private ObjectOutputStream sOutput; // to write on the socket
    private int port = Main.port2;
    private entity.User currentUser = Main.currentUser;
    private Socket sendUserIdsocket;

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
            sendUserIdsocket = new Socket("localhost", port);
            sOutput = new ObjectOutputStream(sendUserIdsocket.getOutputStream());
            sOutput.writeObject(currentUser.getUserId());
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
        sOutput = new ObjectOutputStream(socket.getOutputStream());
        sOutput.writeObject(currentUser.getUserId());
        ImageIO.write(buf, "JPG", socket.getOutputStream());
        socket.close();
    }
}