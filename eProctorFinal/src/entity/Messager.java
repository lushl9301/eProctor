package entity;

import java.util.ArrayList;
import java.util.Date;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Messager {
	public String msg;
	private ObjectId myId;
	private Date date = new Date();
	
	public Messager(ObjectId myId){
		this.myId = myId;
	}

	
	public void sendMsg(String str,ObjectId receiverId, ObjectId senderId, ObjectId sessionId,String type){
		Main.mongoHQ.message.insert(new BasicDBObject().append("senderId", senderId)
														.append("receiverId", receiverId)
														.append("message", str)
														.append("sessionId", sessionId)
														.append("time",date)
														.append("type", type));
		Main.mongoHQ.student.update(new BasicDBObject().append("-id",receiverId), new BasicDBObject().append("isRead",false));
	}
	public static String pollMsg(ObjectId myId){
		ArrayList<DBObject> msgs = new ArrayList<DBObject>();
		if((boolean)Main.mongoHQ.student.findOne(new BasicDBObject().append("_id",myId),new BasicDBObject().append("isRead",1)).get("isRead")==false)
			{
				DBCursor cursor = Main.mongoHQ.message.find(new BasicDBObject().append("receiverId",myId).append("isRead",false));
				while(cursor.hasNext()){
					msgs.add(cursor.next());
				}
			}
		
		String str = null;
		
		for(DBObject o : msgs){
			str = str +"/n"+ (String)Main.mongoHQ.message.findOne(new BasicDBObject().append("_id",o.get("_id")),new BasicDBObject().append("type",1)).get("type")
					+(String)Main.mongoHQ.student.findOne(new BasicDBObject().append("_d",o.get("senderId")),new BasicDBObject().append("name", 1)).get("name")
					+o.get("message");
		}
			
		return str;
	}
//Thread is to poll server to check if have message in database		
	Thread pollMsg = new Thread(){
		public void run(){
			while(true){
				//add method to visit database
				msg = pollMsg(myId);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}		
		}
	};

}
