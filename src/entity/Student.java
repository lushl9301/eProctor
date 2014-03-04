package entity;

import java.util.ArrayList;

public class Student implements User{

    private String userName;
    private String userPassword;
    private ArrayList<String> courseIdList;
    private ArrayList<String> examIdList;
    private String realName;
    private ArrayList<String> cameraRecordingIdList;
    private ArrayList<String> screenRecordingIdList;
    private ArrayList<Integer> examResultList;
    private ArrayList<String> conversationIdList;

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public ArrayList<String> getCourseIdList() {
        return this.courseIdList;
    }

    public void setCourseIdList(ArrayList<String> courseIdList) {
        this.courseIdList = courseIdList;
    }

    public ArrayList<String> getExamIdList() {
        return this.examIdList;
    }

    public void setExamIdList(ArrayList<String> examIdList) {
        this.examIdList = examIdList;
    }

    public String getRealName() {
        return this.realName;
    }

    public void setRealName(String userId) {
        this.realName = realName;
    }

    public ArrayList<String> getCameraRecordingIdList() {
        return this.cameraRecordingIdList;
    }

    public void setCameraRecordingIdList(ArrayList<String> cameraRecordingIdList) {
        this.cameraRecordingIdList = cameraRecordingIdList;
    }

    public ArrayList<String> getScreenRecordingIdList() {
        return this.screenRecordingIdList;
    }

    public void setScreenRecordingIdList(ArrayList<String> screenRecordingIdList) {
        this.screenRecordingIdList = screenRecordingIdList;
    }

    public ArrayList<Integer> getExamResultList() {
        return this.examResultList;
    }

    public void setExamResultList(ArrayList<Integer> examResultList) {
        this.examResultList = examResultList;
    }

    public ArrayList<String> getConversationIdList() {
        return this.conversationIdList;
    }

    public void setConversationIdList(ArrayList<String> conversationIdList) {
        this.conversationIdList = conversationIdList;
    }

}