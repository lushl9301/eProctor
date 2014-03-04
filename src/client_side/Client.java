package client_side;

import java.io.*;
import java.util.*;

import java.net.Socket;

import entity.Main;

public class Client {

    private int port = Main.port1;
    private ObjectInputStream sInput; // to read from the socket
    private ObjectOutputStream sOutput; // to write on the socket
    private Socket socket;
    private String server = "localhost";
    public static ArrayList<ArrayList<String>> receivedMsg = null;

    public boolean verifyUser(String username, String mD5Password, String domain) {
        return true;
    }

    public ArrayList<ArrayList<String>> fetchData(String tableName, String key,
            ArrayList<String> inFromControl) throws Exception {
        socket = new Socket(server, port);

        sInput = new ObjectInputStream(socket.getInputStream());
        sOutput = new ObjectOutputStream(socket.getOutputStream());

        ArrayList<String> info = new ArrayList<String>();
        info.add(tableName);
        info.add(key);
        ArrayList<ArrayList<String>> msg = new ArrayList<ArrayList<String>>();
        msg.add(info);
        msg.add(inFromControl);
        sOutput.writeObject(new entity.ChatMessage(entity.ChatMessage.QUERY, msg));

        @SuppressWarnings("unchecked")
        ArrayList<ArrayList<String>> readObject = (ArrayList<ArrayList<String>>) sInput
                .readObject();
        receivedMsg = readObject;

        sInput.close();
        sOutput.close();
        socket.close();
//        for (ArrayList<String> s : receivedMsg)
//            for (String a : s)
//                System.out.println(a);
        return receivedMsg;
    }

    public void sendMessage(String message) throws Exception {
        socket = new Socket(server, port);

        String info = "Connection accepted " + socket.getInetAddress() + ":"
                + socket.getPort();
        System.out.println(info);

        sOutput = new ObjectOutputStream(socket.getOutputStream());
        ArrayList<String> send = new ArrayList<String>();
        ArrayList<ArrayList<String>> msg = new ArrayList<ArrayList<String>>();
        send.add(message);
        msg.add(send);
        sOutput.writeObject(new entity.ChatMessage(entity.ChatMessage.MESSAGE, msg));

        //assume there is not return;
        sOutput.close();
        socket.close();
    }
    
    public void updateData(String tableName, String key, String examId, ArrayList<String> bookingInfo) throws Exception {
        socket = new Socket(server, port);

        sOutput = new ObjectOutputStream(socket.getOutputStream());

        ArrayList<String> info = new ArrayList<String>();
        info.add(0, examId);
        info.add(0, key);
        info.add(0, tableName);
        ArrayList<ArrayList<String>> msg = new ArrayList<ArrayList<String>>();
        msg.add(info);
        msg.add(bookingInfo);
        sOutput.writeObject(new entity.ChatMessage(entity.ChatMessage.UPDATE, msg));

        //assume there is no reply
        sOutput.close();
        socket.close();
    }

}