package nnn;
//http://www.dreamincode.net/forums/topic/259777-a-simple-chat-program-with-clientserver-gui-optional/
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Server {
    
    private static int uniqueId; // a unique ID for each connection
    private ArrayList<ClientThread> al; // an ArrayList to keep the list of the Client
    private SimpleDateFormat sdf; // to display time
    private int port;
    private boolean keepGoing;
    

    public Server(int port) {
        this.port = port;
        sdf = new SimpleDateFormat("HH:mm:ss");
        al = new ArrayList<ClientThread>();
    }
    
    /*
     *  To run as a console application just open a console window and: 
     * > java Server
     * > java Server portNumber
     * If the port number is not specified 3000 is used
     */ 
    public static void main(String[] args) {
        int portNumber = 3000;
        switch(args.length) {
            case 1:
                try {
                    portNumber = Integer.parseInt(args[0]);
                }
                catch(Exception e) {
                    System.out.println("Invalid port number.");
                    System.out.println("Usage is: > java Server [portNumber]");
                    return;
                }
            case 0:
                break;
            default:
                System.out.println("Usage is: > java Server [portNumber]");
                return;
                
        }

        Server server = new Server(portNumber);
        server.start();
    }

    public void start() {
        keepGoing = true;

        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while(keepGoing) 
            {
                display("Server waiting for Clients on port " + port + ".");
                
                Socket socket = serverSocket.accept();

                if(!keepGoing)
                    break;
                ClientThread t = new ClientThread(socket);
                al.add(t); //add to thread ArrayList
                t.start();
            }
            //stop
            try {
                serverSocket.close();
                for(int i = 0; i < al.size(); ++i) {
                    ClientThread tc = al.get(i);
                    try {
                        tc.sInput.close();
                        tc.sOutput.close();
                        tc.socket.close();
                    } catch(IOException ioE) {
                        ;
                    }
                }
            }
            catch(Exception e) {
                display("Exception closing the server and clients: " + e);
            }
        }

        catch (IOException e) {
            String msg = sdf.format(new Date()) + " Exception on ServerSocket: " + e + "\n";
            display(msg);
        }
    }       

    //GUI can stop server --maybe
    protected void stop() {
        keepGoing = false;
        // connect to myself as Client to exit statement 
        // Socket socket = serverSocket.accept();
        try {
            new Socket("localhost", port);
        } catch(Exception e) {
            // nothing I can really do
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

    /*
     *  to broadcast a message to all Clients
     */
    /*private synchronized void broadcast(String message) {
        // add HH:mm:ss and \n to the message
        String time = sdf.format(new Date());
        String messageLf = time + " " + message + "\n";
        System.out.print(messageLf);
        
        // we loop in reverse order in case we would have to remove a Client
        // because it has disconnected
        for(int i = al.size(); --i >= 0;) {
            ClientThread ct = al.get(i);
            // try to write to the Client if it fails remove it from the list
            if(!ct.writeMsg(messageLf)) {
                al.remove(i);
                display("Disconnected Client " + ct.id + " removed from list.");
            }
        }
    }*/


    //received cm and write back msg already    
    synchronized void remove(int id) {
        for(int i = 0; i < al.size(); ++i) {
            ClientThread ct = al.get(i);
            if(ct.id == id) {
                al.remove(i);
                return;
            }
        }
    }


    /** One instance of this thread will run for each client */
    class ClientThread extends Thread {
        Socket socket;
        ObjectInputStream sInput;
        ObjectOutputStream sOutput;

        int id;
        ChatMessage cm;
        // the date I connect
        String date;

        ClientThread(Socket socket) {
            id = ++uniqueId;
            this.socket = socket;
            System.out.println("Thread trying to create Object Input/Output Streams");
            try {
                sOutput = new ObjectOutputStream(socket.getOutputStream());
                sInput  = new ObjectInputStream(socket.getInputStream());
                display(id + " just connected.");
            } catch (IOException e) {
                display("Exception creating new Input/output Streams: " + e);
                return;
            }

            date = new Date().toString() + "\n";
        }

        //run forever
        public void run() {
            try {
                cm = (ChatMessage) sInput.readObject();
            } catch (IOException e) {
                display(id + " Exception reading Streams: " + e);           
            } catch(ClassNotFoundException e2) {
            }
            // the messaage part of the ChatMessage
            ArrayList<ArrayList<String>> message = cm.getMessage();
            display(message);
            
            ArrayList<ArrayList<String>> replyMsg = new ArrayList<ArrayList<String>>();
            ArrayList<String> s = new ArrayList<String>();
            s.add("1"); s.add("2");
            replyMsg.add(s);
            ArrayList<String> b = new ArrayList<String>();
            s.add("3"); s.add("4");
            replyMsg.add(b);
            writeMsg(replyMsg);
            //broadcast(id + ": " + message);
            
            // remove myself from the arrayList containing the list of the
            // connected Clients
            remove(id);
            close();
        }
        
        private void close() {
            try {
                if(sOutput != null) sOutput.close();
            }
            catch(Exception e) {}
            try {
                if(sInput != null) sInput.close();
            }
            catch(Exception e) {};
            try {
                if(socket != null) socket.close();
            }
            catch (Exception e) {}
        }

        private boolean writeMsg(ArrayList<ArrayList<String>> msg) {
            if(!socket.isConnected()) {
                close();
                return false;
            }

            try {
                sOutput.writeObject(msg);
            } catch(IOException e) {
                display("Error sending message to " + id);
                display(e.toString());
            }
            return true;
        }
    }
}