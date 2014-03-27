package entity;

import java.util.ArrayList;
import java.util.Date;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class Messager {
	public String msg;
	private ObjectId myId;
	private Date date;
	
	public Messager(ObjectId myId){
		this.myId = myId;
	}

	
	public static boolean sendMsg(String str, ObjectId receiverId, ObjectId senderId, ObjectId sessionId, String type, Date date){
		WriteResult wr = null;
		wr = Main.mongoHQ.message.insert(new BasicDBObject().append("senderId", senderId)
														.append("receiverId", receiverId)
														.append("message", str)
														.append("sessionId", sessionId)
														.append("time",date)
														.append("type", type)
														.append("isRead", false));
		if (wr.getError() != null) {
			System.out.println(wr.getError());
			return false;
		}
		// don't know where to update
		
		wr = Main.mongoHQ.proctor.update(new BasicDBObject().append("_id",receiverId), new BasicDBObject("$set", new BasicDBObject().append("isRead",false)), false, false);
		if (wr.getError() == null) {
			wr = Main.mongoHQ.student.update(new BasicDBObject().append("_id",receiverId), new BasicDBObject("$set", new BasicDBObject().append("isRead",false)), false, false);
			if (wr.getError() == null)
				return true;
		}
		return false;
	}
	public static String pollMsg(ObjectId myId, String domain){
		ArrayList<DBObject> msgs = new ArrayList<DBObject>();
		DBObject temp = null;
		if (domain.equals("Proctor")) {
			temp = Main.mongoHQ.proctor.findOne(new BasicDBObject().append("_id",myId),new BasicDBObject().append("isRead",1));
		} else {
			temp = Main.mongoHQ.student.findOne(new BasicDBObject().append("_id",myId),new BasicDBObject().append("isRead",1));
		}
		
		if(temp != null && (boolean)temp.get("isRead") == false) {
			{
				DBCursor cursor = Main.mongoHQ.message.find(new BasicDBObject().append("receiverId",myId).append("isRead",false));
				while(cursor.hasNext()){
					msgs.add(cursor.next());
				}
			}
		}
		
		String str = "";
		
		for(DBObject o : msgs){
			String name = "";
			DBObject mingzi = Main.mongoHQ.proctor.findOne(new BasicDBObject("_id", (ObjectId)o.get("senderId")));
			System.out.println("sender id: " + o.get("senderId"));
			if (mingzi == null)
				mingzi = Main.mongoHQ.student.findOne(new BasicDBObject("_id", (ObjectId)o.get("senderId")));
			if (mingzi == null) {
				System.out.println("Messager here: ???@$%U*^&%%^%$!!@!#!#$");
				continue;
			}
			System.out.println("Messager here: mingzi: " + mingzi);
			name = (String) mingzi.get("name");
			str = str
				+ "\nsender name: " + name
				+ "\ntype: " + o.get("type")
				/*+(String)Main.mongoHQ.student.findOne(new BasicDBObject().append("_id",o.get("senderId")),new BasicDBObject().append("name", 1)).get("name")*/
				+ "\ncontent: " + o.get("message")
				+ "\n";
			
		}
		
		
		////////////////////////////////////
		//this is to set the isRead true
//		Main.mongoHQ.proctor.update(new BasicDBObject().append("_id",myId), new BasicDBObject("$set", new BasicDBObject().append("isRead",true)), false, false);
//		Main.mongoHQ.student.update(new BasicDBObject().append("_id",myId), new BasicDBObject("$set", new BasicDBObject().append("isRead",true)), false, false);
	////////////////////////////////////////////
		
		
		System.out.println("messager here: " + str);
		return str;
	}
//Thread is to poll server to check if have message in database		
//	Thread pollMsg = new Thread(){
//		public void run(){
//			while(true){
//				//add method to visit database
//				msg = pollMsg(myId);
//				try {
//					Thread.sleep(3000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}		
//		}
//	};
}
