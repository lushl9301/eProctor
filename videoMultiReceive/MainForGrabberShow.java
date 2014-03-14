import java.util.HashMap;

import com.googlecode.javacv.CanvasFrame;

public class MainForGrabberShow {
    static int port1 = 6001;
    static int port2 = 6002;
//    public static HashMap<String, CanvasFrame> canvasList;

    public static void main(String[] args) throws Exception {
//        canvasList = new HashMap<String, CanvasFrame>();
        new GrabberShow(port1, "AAAA");
//        new GrabberShow(port2, "BBBB");
//        new Displayer(port2, "AAAA", canvasList);
    }
}