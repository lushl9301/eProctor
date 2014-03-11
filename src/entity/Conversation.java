package entity;

import java.util.ArrayList;

public class Conversation {

    private ArrayList<Time> sendTimeList;
    private String senderId;
    private String receiverId;
    private ArrayList<String> messageIdList;
    private ArrayList<String> messageList;
    private String courseCode;
    private String conversationId;
    private String examId;

    public ArrayList<Time> getSendTimeList() {
        return this.sendTimeList;
    }

    public void setSendTimeList(ArrayList<Time> sendTimeList) {
        this.sendTimeList = sendTimeList;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return this.receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public ArrayList<String> getMessageIdList() {
        return this.messageIdList;
    }

    public void setMessageIdList(ArrayList<String> messageIdList) {
        this.messageIdList = messageIdList;
    }

    public ArrayList<String> getMessageList() {
        return this.messageList;
    }

    public void setMessageList(ArrayList<String> messageList) {
        this.messageList = messageList;
    }

    public String getCourseCode() {
        return this.courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getConversationId() {
        return this.conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getExamId() {
        return this.examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

}