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
            db = mongoClient.getDB("testJavaMongo");
            coll = db.getCollection("hhh");
        } catch (UnknownHostException e) {
            System.out.println("fail to connect to database");
            return false;
        }

        try {
            DBObject one = coll.findOne(new BasicDBObject("username", "gongy0000")
                                            .append("md5", "30e04e5dfa8b1f8b3d56"));
            mongoClient.close();

            if (!one)
                return false;
            return true;
        } catch (NullPointerException e) {
            mongoClient.close();
            System.out.println("denied");
            return false;
        }
    }
    // public static boolean updateExam(Exam newExam) {
    //     try {
    //         mongoClient = new MongoClient("localhost",27017);
    //         db = mongoClient.getDB("testJavaMongo");
    //         coll = db.getCollection("exam");
    //     } catch (UnknownHostException e) {
    //         System.out.println("fail to connect to database");
    //         return false;
    //     }
        

    //     try {
    //         DBObject one = coll.update(new BasicDBObject("examId", newExam.examId));
    //         mongoClient.close();

    //         coll.update(new BasicDBObject("examId", newExam.examId), 
    //                     new BasicDBObject("$set", new BasicDBObject("courseCode", newExam.courseCode)
    //                                                 .append("startTime", newExam.startTime)
    //                                                 .append("endTime", newExam.endTime)
    //                                                 .append("examAddress", newExam.examAddress)
    //                                                 .append("proctorIdList", newExam.proctorIdList));

    //         mongoClient.close();
    //         System.out.println("success");
    //         return true;
    //     } catch (NullPointerException e) {
    //         mongoClient.close();
    //         System.out.println("failed");
    //         return false;
    //     }
    // }
    // public static ArrayList<Exam> getBookedExam(Student currentStudent) {
    //     try {
    //         mongoClient = new MongoClient("localhost",27017);
    //         db = mongoClient.getDB("testJavaMongo");
    //         coll = db.getCollection("exam");
    //     } catch (UnknownHostException e) {
    //         System.out.println("fail to connect to database");
    //         return false;
    //     }

    //     try {
    //         DBCursor cursor = coll.find(new BasicDBObject("data", new BasicDBObject("$elemMatch", new BasicDBObject("username", currentStudent.username))),
    //                                 new BasicDBObject("startTime", 1)
    //                                 .append("endTime", 1)
    //                                 .append("courseCode", 1)
    //                                 .append("examId", 1)
    //                                 .append("examAddress", 1));
    //         mongoClient.close();

    //         ArrayList<Exam> bookedExam = new ArrayList();
    //         DBObject exam;
    //         while (cursor.hasNext()) {
    //             exam = cursor.next();
    //             bookedExam.add(new Exam(exam.get("examId"),
    //                                     exam.get("courseCode"),
    //                                     exam.get("startTime"),
    //                                     exam.get("endTime"),
    //                                     exam.get("examAddress")));
    //         }

    //         System.out.println("success");
    //         return bookedExam;
    //     } catch (NullPointerException e) {
    //         mongoClient.close();
    //         System.out.println("failed");
    //         return null;
    //     }
    // }
    // public ArrayList<Course> getRegisteredCourse(Student currentStudent) {
    //     try {
    //         mongoClient = new MongoClient("localhost",27017);
    //         db = mongoClient.getDB("testJavaMongo");
    //         coll = db.getCollection("hhh");
    //     } catch (UnknownHostException e) {
    //         System.out.println("fail to connect to database");
    //         return false;
    //     }

    //     try {
    //         DBObject one = coll.findOne(new BasicDBObject("username", "gongy0000"),
    //                                     new BasicDBObject("courseCode", 1));
    //         mongoClient.close();

    //         if (!one)
    //             return null;

    //         ArrayList<String> registeredCourse = (ArrayList)(BasicDBList)one.get("courseCode");
    //         return registeredCourse;
    //     } catch (NullPointerException e) {
    //         mongoClient.close();
    //         System.out.println("denied");
    //         return null;
    //     }
    // }

    // public static boolean verifyUser(String userID, String md5, String domain) {
    //     try {
    //         mongoClient = new MongoClient("localhost",27017);
    //         db = mongoClient.getDB("testJavaMongo");
    //         user = db.getCollection("user");

    //         if (!user.findOne(new BasicDBObject("userID", userID)
    //                         .append("md5", md5)
    //                         .append("domain", domain)))
    //             return false;
    //         return true;
    //     } catch (Exception e) {
    //         System.out.println("failed");
    //         return false;
    //     }
    // }


    //     try {
    //         mongoClient = new MongoClient("localhost",27017);
    //         db = mongoClient.getDB("testJavaMongo");
    //         user = db.getCollection("user");

            

    //         return true;
    //     } catch (Exception e) {
    //         System.out.println("failed");
    //         return false;
    //     }
}