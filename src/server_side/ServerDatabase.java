import java.util.*;
import com.mongodb.*;

public class ServerDatabase {
    private static MongoClient mongoClient;
    private static DB db;
    private static DBCollection coll;

    public ServerDatabase() {

    }

    public static boolean verifyUser(String userID, String md5, String domain) {
        try {
            mongoClient = new MongoClient("localhost",27017);
        } catch (UnknownHostException e) {
            System.out.println("fail to connect to database");
            return false;
        }

        db = mongoClient.getDB("testJavaMongo");
        coll = db.getCollection("hhh");

        try {
            DBObject one = coll.findOne(new BasicDBObject("username", "gongy0000")
                                            .append("md5", "30e04e5dfa8b1f8b3d56"));
            mongoClient.close();

            // System.out.println(one);
            // System.out.println("passed");
            
            // ArrayList<String> queryResult = new ArrayList((BasicDBList) one.get("courseCode"));
            // Iterator i = queryResult.iterator();
            // while(i.hasNext())
            //     System.out.println(i.next());

            if (one)
                return false;
            return true;
        } catch (NullPointerException e) {
            mongoClient.close();
            System.out.println("denied");
            return false;
        }
    }

    public static boolean updateExam(Exam newExam) {
        try {
            mongoClient = new MongoClient("localhost",27017);
        } catch (UnknownHostException e) {
            System.out.println("fail to connect to database");
            return false;
        }

        db = mongoClient.getDB("testJavaMongo");
        coll = db.getCollection("exam");

        try {
            DBObject one = coll.update(new BasicDBObject("examId", newExam.examId));
            mongoClient.close();

            coll.update(new BasicDBObject("examId", newExam.examId), 
                        new BasicDBObject("$set", new BasicDBObject("courseCode", newExam.courseCode)
                                                    .append("startTime", newExam.startTime)
                                                    .append("endTime", newExam.endTime)
                                                    .append("examAddress", newExam.examAddress)
                                                    .append("proctorIdList", newExam.proctorIdList));

            mongoClient.close();
            System.out.println("success");
            return true;
        } catch (NullPointerException e) {
            mongoClient.close();
            System.out.println("failed");
            return false;
        }
    }

    
}