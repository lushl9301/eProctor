import java.io.*;
import java.util.*;

public class ChatMessage implements Serializable {
    protected static final long serialVersionUID = 1112122200L;
    static final int WHOISIN = 0, MESSAGE = 1, LOGOUT = 2;
    private int type;
    private ArrayList<ArrayList<String>> message;
    
    ChatMessage(int type, ArrayList<ArrayList<String>> message) {
        this.type = type;
        this.message = message;
    }
    
    public int getType() {
        return type;
    }
    public ArrayList<ArrayList<String>> getMessage() {
        return message;
    }
}

