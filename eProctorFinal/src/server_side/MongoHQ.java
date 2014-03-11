package server_side;

import java.net.UnknownHostException;

import com.mongodb.*;

import entity.Main;

public class MongoHQ {
	public DBCollection record;
	public DBCollection course;
	public DBCollection session;
	public DBCollection student;
	
	public boolean connected = false;

	public MongoHQ() throws Exception {
		connected = connect();
		System.out.println("connected");
	}

	public boolean connect() throws UnknownHostException {
		System.out.println("Starting connection");
		MongoClientURI uri = new MongoClientURI(
				"mongodb://admin:admin@emma.mongohq.com:10051/real_like_doc");
		MongoClient mongoClient = new MongoClient(uri);
		DB db = mongoClient.getDB("real_like_doc");
		record = db.getCollection("Record");
		course = db.getCollection("Course");
		session = db.getCollection("Session");
		student = db.getCollection("Student");
		return true;
	}

	public void disconnect() {

	}
}
