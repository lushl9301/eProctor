import java.io.*;
import java.net.*;

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

        int numOfString = 2 + query.size();
        query.add(0, key);
        query.add(0, tableName);


        clientSocket = new Socket(host, port);
        inFromServer = new BufferedReader(new InputStream(clientSocket.getInputStream()));
        outToServer = new PrintStream(clientSocket.getOutputStream());
        
        if (clientSocket != null) {
            new Thread(new Client()).start();
            for (String s : inFromControl) {
                outToServer.println(s);
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
        } catch (IOException e) {
            System.err.println(e);
        }
    }


    public ArrayList<String> fetchData(String tableName, String key, ArrayList<String> query) {
        /*
        use socket to change this request to
        "number of string" "table name" "key" "query1" "query2" ....
         */
        /*
        use socket to change this return
        "number of returned string" "string1" "string2"...
         */
    }

/*
//==========LoginControl===========================
//=================================================
    public boolean verifyUser(String userID, String md5, String domain) {
        return true;
    }

//==========BookingControl=========================
//=================================================
    public ArrayList<Exam> getBookedExam(Student currentStudent) {
        /*
        query here
         */
    }
    public ArrayList<Course> getRegisteredCourse(Student currentStudent) {
        /*
        query here
         */
    }
    public boolean bookExam(Student currentStudent, Exam selectedExam) {
        /*
        query here
         */
    }

//==========CheckExamResultControl=================
//=================================================
    //public ArrayList<Course> getRegisteredCourse()


//==========ReviewControl==========================
//=================================================
    public ArrayList<Exam> getInvigilatedExam(this.currentProctor) {
        /*
        query here
         */
    }

//==========RecordingDisplayControl================
//=================================================
    public Recording getRecording(Integer selectedRecordingID) {
        /*
        query here
         */
    }

//==========MessageControl=========================
//=================================================


//==========ProctorHomeControl=====================
//=================================================

//==========InvigilateExamControl==================
//=================================================

//==========StudentHomeControl=====================
//=================================================

//==========TakeExamControl========================
//=================================================

*/
}
