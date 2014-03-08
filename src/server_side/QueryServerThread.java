package server_side;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class QueryServerThread implements Runnable {

    private static int uniqueId; // a unique ID for each connection
    private ArrayList<ClientThread> al; // an ArrayList to keep the list of the
                                        // Client
    private SimpleDateFormat sdf; // to display time
    private int port;
    private static ServerSocket serverSocket;

    public QueryServerThread(int port) {
        this.port = port;
        sdf = new SimpleDateFormat("HH:mm:ss");
        al = new ArrayList<ClientThread>();
        new Thread(this, "queryserverthread").start();
    }

    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("start receive Query");
            while (true) {
                display("Server waiting for Clients on port " + port + ".");
                Socket socket = serverSocket.accept();
                ClientThread t = new ClientThread(socket);
                al.add(t); // add to thread ArrayList // if one thread only process one request, why do we maintain those thread? - cly
                new Thread(t).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void display(String msg) {
        String time = sdf.format(new Date()) + " " + msg;
        System.out.println(time);
    }

    // private void display(ArrayList<ArrayList<String>> msg) {
    // this.display("");
    // for (ArrayList<String> s : msg) {
    // for (String a : s) {
    // System.out.println(a);
    // }
    // }
    // }

    synchronized void remove(int id) {
        for (int i = 0; i < al.size(); ++i) {
            ClientThread ct = al.get(i);
            if (ct.id == id) {
                al.remove(i);
                return;
            }
        }
    }

    class ClientThread implements Runnable {
        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;

        int id;
        entity.ChatMessage cm;
        String date;

        ClientThread(Socket socket) {
            id = ++uniqueId;
            this.socket = socket;
            System.out.println("Thread trying to create Object Input/Output Streams");
            try {
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput = new ObjectInputStream(socket.getInputStream());
                display(id + " just connected.");
            } catch (IOException e) {
                e.printStackTrace();
            }

            date = new Date().toString() + "\n";
        }

        public void run() {
            try {
                cm = (entity.ChatMessage) sInput.readObject();
                int type = cm.getType();
                ArrayList<ArrayList<String>> message = cm.getMessage();

                String userId = message.get(0).get(0);

                switch (type) {
                    case 1: { // UPDATE
                        // String tableName = message.get(1).get(0);
                        // String key = message.get(1).get(1);
                        // String examId = message.get(1).get(2);
                        // ArrayList<String> bookingInfo = message.get(2);


                        //
                        // due to limited
                        // only support updating exam information
                        // which includes booked exam session, exam result, camera recording id, screen recording id
                        //
                        String tableName = message.get(1).get(0); // unused

                        String username = message.get(1).get(1);
                        String examId = message.get(1).get(2);
                        ArrayList<String> fieldNameToUpdate = null;
                        ArrayList<String> fieldValueToUpdate = null;
                        updateExamInfo(username, examId, fieldNameToUpdate, fieldValueToUpdate);
                        break;
                    }

                    case 2: { // MESSAGE
                        String receiverId = message.get(1).get(0);
                        // String sms = message.get(1).get(1);
                        // no reply from server;
                        storeMessage(String examId, String senderId, String receiverId, String messageContent, String sendTime);
                        break;
                    }

                    default: { // QUERY
                        String fieldName = message.get(0).get(0);
                        String fieldValue = message.get(0).get(1);
                        ArrayList<String> want = message.get(1);

                        ArrayList<ArrayList<String>> replyMsg = new ArrayList<ArrayList<String>>();
                        ArrayList<String> oneField = new ArrayList<String>();

                        String queryResult = query(fieldName, fieldValue, want);
                        String[] splitedResult = queryResult.splite("#");
                        for (int i = 0; i < splitedResult.length; i++) {
                            ArrayList<String> oneField = new ArrayList<String>();
                            for (int j = 0; j < splitedResult[Integer.parseInt(splitedResult[i]); j++) {
                                oneField.add(splitedResult[j+i]);
                            }
                            i += j;
                            replyMsg.add(oneField);
                        }
                        writeMsg(replyMsg);
                        break;
                    }
                }
                remove(id);
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private boolean writeMsg(ArrayList<ArrayList<String>> msg) throws IOException {
            if (!socket.isConnected()) {
                socket.close();
                return false;
            }

            sOutput.writeObject(msg);
            return true;
        }
    }
}