package nnn;

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

        //client = new Client();
        System.out.println("========================");
        ArrayList<String> update = new ArrayList<String>();
        update.add("StudentName");update.add("woshishabi");
        update.add("exam");update.add("noexam");
        update.add("ID");update.add("123");
        //client.updateData(tableName, key, update);
    }
}