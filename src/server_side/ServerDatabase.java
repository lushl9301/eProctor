import java.util.*;
import com.mongodb.*;

public class ServerDatabase {
    private static MongoClient mongoClient;
    private static DB db;
    private static DBCollection coll;

    public ServerDatabase() {

    }

    // public static boolean verifyUser(String userID, String md5, String domain) {
    //     try {
    //         mongoClient = new MongoClient("localhost",27017);
    //         db = mongoClient.getDB("testJavaMongo");
    //         coll = db.getCollection("hhh");
    //     } catch (UnknownHostException e) {
    //         System.out.println("fail to connect to database");
    //         return false;
    //     }

    //     try {
    //         DBObject one = coll.findOne(new BasicDBObject("username", "gongy0000")
    //                                         .append("md5", "30e04e5dfa8b1f8b3d56"));
    //         mongoClient.close();

    //         if (!one)
    //             return false;
    //         return true;
    //     } catch (NullPointerException e) {
    //         mongoClient.close();
    //         System.out.println("denied");
    //         return false;
    //     }
    // }
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

    // the method for query, need query helper123
    public String query(String fieldName, String key, ArrayList<String> want) {
        MongoClient mongoClient = new MongoClient("localhost",27017);
        DB db = mongoClient.getDB("testJavaMongo");
        DBCollection user = db.getCollection("user");
        DBCollection examBasic = db.getCollection("examBasic");
        DBCollection examRecords = db.getCollection("examRecords");

        String result = query("fieldName", "key", want, user, examBasic, examRecords);
        mongoClient.close();
        return result;
    }

    // query helper1
    public String query(String fieldName, String key, ArrayList<String> wantList, DBCollection user, DBCollection examBasic, DBCollection examRecords) throws UnknownHostException {
        ArrayList<String> userCollection = new ArrayList<String>();
                            userCollection.add("username");
                            userCollection.add("md5");
                            userCollection.add("realname");
                            userCollection.add("domain");
                            userCollection.add("coursecode");
                            userCollection.add("coursetitle");
                            userCollection.add("examid");
        ArrayList<String> examBasicCollection = new ArrayList<String>();
                            examBasicCollection.add("examid");
                            examBasicCollection.add("starttime");
                            examBasicCollection.add("endtime");
                            examBasicCollection.add("coursecode");
                            examBasicCollection.add("examaddress");
        ArrayList<String> examRecordsCollection = new ArrayList<String>();
                            examRecordsCollection.add("examid");
                            examRecordsCollection.add("username");
                            examRecordsCollection.add("result");
                            examRecordsCollection.add("cameraid");
                            examRecordsCollection.add("screenid");
                            examRecordsCollection.add("conversationid");

        DBCollection coll1;
        DBCollection coll2;
        String queryResult = "";
        
        Iterator<String> i = wantList.iterator();
        while (i.hasNext()) {
            String want = i.next();
            
            coll1 = examRecords;
            if (userCollection.contains(fieldName)) {
                coll1 = user;
            } else if (examBasicCollection.contains(fieldName)) {
                coll1 = examBasic;
            }
            
            coll2 = examRecords;
            if (userCollection.contains(want)) {
                coll2 = user;
            } else if (examBasicCollection.contains(want)) {
                coll2 = examBasic;
            }
            queryResult += query(fieldName, key, want, coll1, coll2);
        }
        
        queryResult += "#";
        return queryResult;
    }

    // query helper2
    public static HashSet<String> query(String one, String key, String two, DBCollection coll) {
        HashSet<String> filter = new HashSet<String>();
        
        BasicDBObject queryfield = new BasicDBObject(one, key);
        BasicDBObject wantfield = new BasicDBObject(two, 1);
        
        DBCursor cursor = coll.find(queryfield, wantfield);

        while (cursor.hasNext()) {
            String temp = (String) cursor.next().get(two);
            filter.add(temp);
        }
        return filter;
    }

    // convert hashset to string
    public static String toto(HashSet<String> xxx) {
        Iterator<String> iter = xxx.iterator();
        String temp = "";
        while (iter.hasNext()) {
            temp += "#" + iter.next();
        }
        return temp;
    }

    // query helper3
    public static String query(String one, String key, String two, DBCollection coll1, DBCollection coll2) {
        HashMap<String, String> userMap = new HashMap<String, String>();
            userMap.put("examBasic", "examid");
            userMap.put("examRecords", "username");
            userMap.put("user", "username");
        HashMap<String, String> examBasicMap = new HashMap<String, String>();
            examBasicMap.put("user", "username");
            examBasicMap.put("examRecords", "examid");
            examBasicMap.put("examBasic", "examid");
        HashMap<String, String> examRecordsMap = new HashMap<String, String>();
            examRecordsMap.put("examBasic", "examid");
            examRecordsMap.put("user", "username");
            examRecordsMap.put("examRecords", "examid");
            
        String link;
        if (coll1.getName().equals("user")) {
            link = userMap.get(coll2.getName());
        } else if (coll1.getName().equals("examBasic")) {
            link = examBasicMap.get(coll2.getName());
        } else {
            link = examRecordsMap.get(coll2.getName());
        }
        
        HashSet<String> bigFilter = new HashSet<String>();
        
        HashSet<String> firstResult = query(one, key, link, coll1);
        HashSet<String> secondResult;
        Iterator<String> iter = firstResult.iterator();
        while (iter.hasNext()) {
            String temp = iter.next();
            secondResult = query(link, temp, two, coll2);
            bigFilter.addAll(secondResult);
        }

        return "#" + bigFilter.size() + toto(bigFilter);
    }

    public static void updateExamInfo(String username, String examId, ArrayList<String> fieldNameToUpdate, ArrayList<String> fieldValueToUpdate) {
        MongoClient mongoClient = new MongoClient("localhost",27017);
        DB db = mongoClient.getDB("testJavaMongo");
        DBCollection user = db.getCollection("user");
        DBCollection examRecords = db.getCollection("examRecords");

        BasicDBObject query = new BasicDBObject("username", username).append("examid", examId);
        BasicDBObject newUserValue = new BasicDBObject("examid", fieldValueToUpdate.get(fieldNameToUpdate.indexOf("examid")));
        BasicDBObject newRecordValue = new BasicDBObject();
                                        Iterator<String> iterN = fieldNameToUpdate.iterator();
                                        Iterator<String> iterV = fieldValueToUpdate.iterator();
                                        while (iterN.hasNext()) {
                                            newRecordValue.append(iterN.next(), iterV.next());
                                        }
        user.update(query, newUserValue, true, true);
        examRecords.update(query, newRecordValue, true, true);

        mongoClient.close();
    }

    public static void storeMessage(String examId, String senderId, String receiverId, String messageContent, String sendTime) {
        MongoClient mongoClient = new MongoClient("localhost",27017);
        DB db = mongoClient.getDB("testJavaMongo");
        DBCollection user = db.getCollection("user");
        DBCollection examRecords = db.getCollection("examRecords");
        
        BasicDBObject oneRecord = new BasicDBObject("senderId", senderId)
                                            .append("receiverid", receiverId==null?"":receiverId)
                                            .append("message", messageContent)
                                            .append("sendtime", sendTime);
        if (receiverId == null) {
            BasicDBObject query = new BasicDBObject("examid", examId);
            examRecords.update(query, new BasicDBObject("$push", new BasicDBObject("conversation", oneRecord)), true, true);
        } else {
            String domain = (String) user.findOne(new BasicDBObject("username", senderId), new BasicDBObject("domain", 1)).get("domain");
            if (domain.equals("student")) {
                BasicDBObject query = new BasicDBObject("examid", examId).append("username", senderId);
                examRecords.update(query, new BasicDBObject("$push", new BasicDBObject("conversation", oneRecord)), true, true);
            } else {
                BasicDBObject query = new BasicDBObject("examid", examId).append("username", receiverId);
                examRecords.update(query, new BasicDBObject("$push", new BasicDBObject("conversation", oneRecord)), true, true);
            }
        }
        
        mongoClient.close();
    }
}