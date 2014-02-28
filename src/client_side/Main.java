package client;

import java.util.*;


public class Main {
    
    public static void main(String[] args) throws Exception {
        System.out.println("start!!!");
        Client client = new Client();
        ArrayList<String> query = new ArrayList<String>();
        query.add("studentName");
        query.add("yeah");
        query.add("exam");
        String tableName = "StudentINFO";
        String key = "studentID";
        client.fetchData(tableName, key, query);
    }
}