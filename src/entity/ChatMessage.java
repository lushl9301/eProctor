package entity;

import java.io.*;
import java.util.*;

public class ChatMessage implements Serializable {
    protected static final long serialVersionUID = 1112122200L;
    public User currentUser = Main.currentUser;
    public static final int QUERY = 0;
    public static final int UPDATE = 1;
    public static final int MESSAGE = 2;
    private int type;
    private ArrayList<ArrayList<String>> message;

    public ChatMessage(int type, ArrayList<ArrayList<String>> message) {
        this.type = type;
        this.message = message;
        ArrayList<String> userId = new ArrayList<String>();
        userId.add(currentUser.getUserId());
        this.message.add(0, userId);
    }

    public String getCurrentUserId() {
        return currentUser.getUserId();
    }

    public int getType() {
        return type;
    }

    public ArrayList<ArrayList<String>> getMessage() {
        return message;
    }
}
