//package webCam;
import static com.googlecode.javacv.cpp.opencv_core.*;

import java.awt.image.BufferedImage;
import java.net.Socket;

import javax.imageio.ImageIO;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class GrabberShow implements Runnable {
    IplImage image;
    CanvasFrame canvas = new CanvasFrame("Web Cam");
    private static Socket socket;

    public GrabberShow(int port) {
        socket = new Socket("localhost", port)
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
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
                try {
                    img = grabber.grab();
                    buf = img.getBufferedImage();
                    if (img != null) {
                        cvFlip(img, img, 1);// l-r = 90_degrees_steps_anti_clockwise
                        canvas.showImage(img);
                        send(buf);
                    }
                } catch (Exception e) {
                    break;
                }
            }
            socket.close();
        } catch (Exception e) {
        }
   }
    
  public void send(BufferedImage buf) throws Exception {    
	    System.out.println("start...");
	    ImageIO.write(buf, "JPG", socket.getOutputStream());
  }

  public static void main(String[] args) {
        GrabberShow gs = new GrabberShow();
        Thread th = new Thread(gs);
        th.start();
    }
}