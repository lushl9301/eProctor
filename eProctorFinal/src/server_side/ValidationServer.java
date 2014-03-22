package server_side;

import java.net.UnknownHostException;

import com.mongodb.*;

public class ValidationServer {
	public DBCollection user;

	private boolean connected = false;

	public ValidationServer(String strUrl, String strDb) throws Exception {
		System.out.println("ValidationServer connecting");
		MongoClientURI uri = new MongoClientURI(strUrl);
		MongoClient mongoClient = new MongoClient(uri);
		DB db = mongoClient.getDB(strDb);
		user = db.getCollection("User");
		connected = true;
		System.out.println("ValidationServer connected");
	}

	public void disconnect() {

	}

	public boolean isConnected() {
		return connected;
	}
}
