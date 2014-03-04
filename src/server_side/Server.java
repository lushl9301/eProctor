package server_side;

import java.io.*;

public class Server {
    private static int port1 = 6000;
    private static int port2 = 6001;

    public static void main(String[] args) throws IOException {
        new QueryServerThread(port1);
        new VideoServerThread(port2);
    }

    public boolean verifyUser(String username, String mD5Password, String domain) {
        // TODO
        // 1. select Username and MD5Password
        // 2. verify
        // 3. return
        return true;
    }

}