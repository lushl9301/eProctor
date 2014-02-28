package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static ServerSocket welcomeSocket = null;
    private static Socket serverSocket = null;
    private static final int maxClientsCount = 10;
    private static final clientThread[] threads = new clientThread[maxClientsCount];
    
    public static void main(String[] args) throws Exception {
        int port = 3000;
        String host = "localhost";
        System.out.println("server started");        
        welcomeSocket = new ServerSocket(port);
        
        while (true) {
            serverSocket = welcomeSocket.accept();
            int i;
            for (i = 0; i < maxClientsCount; i++) {
                if (threads[i] == null) {
                    System.out.println("number " + i + " :");
                    (threads[i] = new clientThread(serverSocket, threads)).start();
                    break;
                }
            }
            if (i >= maxClientsCount) {
                PrintStream outToClient = new PrintStream(serverSocket.getOutputStream());
                outToClient.println("Server too busy. Try later.");
                outToClient.close();
                serverSocket.close();
            }
        }
    }
}

class clientThread extends Thread {
    private BufferedReader inFromClient = null;
    private PrintStream outToClient = null;
    private Socket serverSocket = null;
    private final clientThread[] threads;
    private int maxClientsCount;
    private String name;
    
    
    public clientThread(Socket serverSocket, clientThread[] threads) {
        this.serverSocket = serverSocket;
        this.threads = threads;
        this.maxClientsCount = threads.length;
    }
    
    public void run() {
        int maxClientsCount = this.maxClientsCount;
        clientThread[] threads = this.threads;
        int numOfQueryString;

        try {
            inFromClient = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            outToClient = new PrintStream(serverSocket.getOutputStream());

            numOfQueryString = Integer.parseInt(inFromClient.readLine());

            System.out.println("length of query: " + numOfQueryString);
            String tableName = inFromClient.readLine();
            String key = inFromClient.readLine();
            ArrayList<String> query = new ArrayList<String>();
            while (numOfQueryString > 0) {
                String line = inFromClient.readLine();
                query.add(line);
                System.out.println(line);
                numOfQueryString--;
            }
            /*
            query to data base
            (String tableName, String key, ArrayList<String> query) 
             */
            ArrayList<String> replyString = new ArrayList<String>();
            replyString.add("2");
            replyString.add("success1");
            replyString.add("success2");
            synchronized(this) {
                for (String s : replyString) {
                outToClient.println(s);
               }
            }
            serverSocket.close();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
