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
                al.add(t); // add to thread ArrayList
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
    private void display(ArrayList<ArrayList<String>> msg) {
        this.display("");
        for (ArrayList<String> s : msg) {
            for (String a : s) {
                System.out.println(a);
            }
        }
    }

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
        ChatMessage cm;
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
                cm = (ChatMessage) sInput.readObject();
	            // the messaage part of the ChatMessage
	            ArrayList<ArrayList<String>> message = cm.getMessage();
	            display(message);
	
	            ArrayList<ArrayList<String>> replyMsg = new ArrayList<ArrayList<String>>();
	            ArrayList<String> s = new ArrayList<String>();
	            					s.add("1");
	            					s.add("2");
	            replyMsg.add(s);
	            ArrayList<String> b = new ArrayList<String>();
	            					s.add("3");
	            					s.add("4");
	            replyMsg.add(b);
	            writeMsg(replyMsg);
	            // broadcast(id + ": " + message);
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