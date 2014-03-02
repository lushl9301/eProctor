import java.util.*;

public class Main {
	static int port1 = 6000;
	static int port2 = 6001;
    public static void main(String[] args) throws Exception {
        Client client = new Client();
        ArrayList<String> query = new ArrayList<String>();
        				query.add("studentName");
        				query.add("yeah");
        				query.add("exam");
        String tableName = "StudentINFO";
        String key = "studentID";
        
        client.fetchData(port1, tableName, key, query);
        new GrabberShow(port2);
    }
}