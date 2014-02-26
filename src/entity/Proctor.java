public class Proctor {

    private String userName;
    private String userPassword;
    private ArrayList<String> courseIdList;
    private ArrayList<String> examIdList;
    private ArrayList<String> conversationIdList;
    private String userId;
    private ArrayList<String> cameraRecordingIdList;
    private ArrayList<String> screenRecordingIdList;

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

    public ArrayList<String> getConversationIdList() {
        return this.conversationIdList;
    }

    public void setConversationIdList(ArrayList<String> conversationIdList) {
        this.conversationIdList = conversationIdList;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

}