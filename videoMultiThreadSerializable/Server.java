package videoMultiThreadSerializable;

import java.io.*;

public class Server {
//    private static int port1 = 6000;
    private static int port2 = 6001;
    
    public static void main(String[] args) throws IOException {
        // new QueryServerThread(port1);
        new VideoServerThread(port2);
    }

}