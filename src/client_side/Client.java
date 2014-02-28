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
    private ArrayList<ArrayList<String>> replyString;
    private int port = 3000;
    private String host = "localhost";
    private int numOfString;

    public ArrayList<ArrayList<String>> fetchData(String tableName, String key,
                                                  ArrayList<String> inFromControl) throws Exception {

        numOfString = inFromControl.size();
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
            this.replyString = new ArrayList<ArrayList<String>>();
            while (numOfString > 0) {
                int currentLength = Integer.parseInt(inFromServer.readLine());
                ArrayList<String> currentList = new ArrayList<String>();
                while (currentLength > 0) {
                    reply = inFromServer.readLine();
                    currentList.add(reply);
                    currentLength--;
                }
                replyString.add(currentList);
                numOfString--;
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
