package server_side;

import java.net.UnknownHostException;

import com.mongodb.*;

public class MongoHQ {
	public DBCollection record;
	public DBCollection course;
	public DBCollection session;
	public DBCollection student;
	public DBCollection proctor;
	public DBCollection message;
	
	private boolean connected = false;
	
	public MongoHQ(String strUrl, String strDb) throws Exception {
		System.out.println("MongoHQ connecting");
		MongoClientURI uri = new MongoClientURI(strUrl);
		MongoClient mongoClient = new MongoClient(uri);
		DB db = mongoClient.getDB(strDb);
		record = db.getCollection("Record");
		course = db.getCollection("Course");
		session = db.getCollection("Session");
		student = db.getCollection("Student");
		proctor = db.getCollection("Proctor");
		message = db.getCollection("Message");
		connected = true;
		System.out.println("MongoHQ connected");
	}

	public void disconnect() {

	}

	public boolean isConnected() {
		return connected;
	}
}
