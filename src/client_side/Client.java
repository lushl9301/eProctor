package client;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client implements Runnable {

    private static Socket clientSocket = null;
    private static BufferedReader inFromServer = null;
    private static PrintStream outToServer = null;
    //private static BufferedReader inFromControl = null;
    private static boolean closed = false;
    private ArrayList<String> replyString;

    public ArrayList<String> fetchData(String tableName, String key,
                                       ArrayList<String> inFromControl) throws Exception {
        int port = 3000;
        String host = "localhost";

        int numOfString = inFromControl.size();
        inFromControl.add(0, key);
        inFromControl.add(0, tableName);


        clientSocket = new Socket(host, port);
        inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        outToServer = new PrintStream(clientSocket.getOutputStream());
        
        if (clientSocket != null) {
            new Thread(new Client()).start();
            outToServer.println(numOfString);
            for (String s : inFromControl) {
                outToServer.println(s);
            }
            while (!closed) {
                ;
            }
            clientSocket.close();
        }
        return this.replyString;
    }

    public void run() {
        String reply;
        try {
            int numOfReplyString = Integer.parseInt(inFromServer.readLine());
            this.replyString = new ArrayList<String>();
            while (numOfReplyString > 0) {
                reply = inFromServer.readLine();
                this.replyString.add(reply);
                numOfReplyString--;
            }
            closed = true;
            for (String s : replyString) {
                System.out.println(s);
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }

}
