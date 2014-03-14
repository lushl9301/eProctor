import java.util.HashMap;

import com.googlecode.javacv.CanvasFrame;

public class MainForDisplayer {
    static int port2 = 6002;
    public static HashMap<String, CanvasFrame> canvasList;

    public static void main(String[] args) throws Exception {
        canvasList = new HashMap<String, CanvasFrame>();
        new Displayer(port2, "AAAA", canvasList);
    }
}