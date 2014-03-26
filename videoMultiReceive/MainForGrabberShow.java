import java.util.HashMap;

import com.googlecode.javacv.CanvasFrame;

public class MainForGrabberShow {
    static int port1 = 6001;
    static int port2 = 6002;

    public static void main(String[] args) throws Exception {
        GrabberShow gs = new GrabberShow(port1, "AAAA");
    }
}