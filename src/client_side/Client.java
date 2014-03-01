package nnn;
//http://www.dreamincode.net/forums/topic/259777-a-simple-chat-program-with-clientserver-gui-optional/
import java.net.*;
import java.io.*;
import java.util.*;

public class Client implements Runnable {

    private static ObjectInputStream sInput;       // to read from the socket
    private static ObjectOutputStream sOutput;     // to write on the socket
    private static Socket socket;
    private static String server = "localhost";
    private static int port = 3000;
    public static ArrayList<ArrayList<String>> receivedMsg = null;

    public ArrayList<ArrayList<String>> fetchData(String tableName, String key,
                                                  ArrayList<String> inFromControl) {

        try {
            socket = new Socket(server, port);
        } catch(Exception ec) {
            display("Error connectiong to server:" + ec);
            return null;
        }
        
        String info = "Connection accepted " + socket.getInetAddress() + ":" + socket.getPort();
        display(info);
    
        /* Creating both Data Stream */
        try {
            sInput  = new ObjectInputStream(socket.getInputStream());
            sOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException eIO) {
            display("Exception creating new Input/output Streams: " + eIO);
            return null;
        }
        

        inFromControl.add(0, key);
        inFromControl.add(0, tableName);
        ArrayList<ArrayList<String>> msg = new ArrayList<ArrayList<String>>();
        msg.add(inFromControl);
        sendMessage(new ChatMessage(ChatMessage.MESSAGE, msg));
        
        new Thread(new Client()).run();
        while (this.receivedMsg == null) {
            ;
        }
        display(this.receivedMsg);
        return this.receivedMsg;
    }


    private void display(String msg) {
        System.out.println(msg);
    }
    private void display(ArrayList<ArrayList<String>> msg) {
        for (ArrayList<String> s : msg) {
            for (String a : s) {
                System.out.println(a);
            }
        }
    }
    
    /*
     * To send a message to the server
     */
    public void sendMessage(ChatMessage msg) {
        try {
            sOutput.writeObject(msg);
        } catch(IOException e) {
            display("Exception writing to server: " + e);
        }
    }

    public void disconnect() {
        try { 
            if(sInput != null) sInput.close();
        } catch(Exception e) {} // not much else I can do
        try {
            if(sOutput != null) sOutput.close();
        } catch(Exception e) {} // not much else I can do
        try{
            if(socket != null) socket.close();
        } catch(Exception e) {} // not much else I can do
    }
    
    public void run() {       
        try {
            receivedMsg = (ArrayList<ArrayList<String>>) sInput.readObject();
            while (receivedMsg == null) {
                ;
            }
        } catch(IOException e) {
            display("Server has close the connection: " + e);
        } catch (Exception e) {
            ;
        }
        disconnect();
    }
}