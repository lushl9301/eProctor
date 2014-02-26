public class Exam {

    private Time startTime;
    private Time endTime;
    private ArrayList<String> proctorIdList;
    private ArrayList<String> studentIdList;
    private ArrayList<Integer> resultList;
    private String courseCode;
    private String examId;
    private ArrayList<String> cameraRecordingIdList;
    private ArrayList<String> screenRecordingIdList;
    private String examAddress;
    private ArrayList<String> conversationIdList;
    private ArrayList<String> notificationIdList;

    public ArrayList<String> getProctorIdList() {
        return this.proctorIdList;
    }

    public void setProctorIdList(ArrayList<String> proctorIdList) {
        this.proctorIdList = proctorIdList;
    }

    public ArrayList<String> getStudentIdList() {
        return this.studentIdList;
    }

    public void setStudentIdList(ArrayList<String> studentIdList) {
        this.studentIdList = studentIdList;
    }

    public String getCourseCode() {
        return this.courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getExamId() {
        return this.examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
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

    public String getExamAddress() {
        return this.examAddress;
    }

    public void setExamAddress(String examAddress) {
        this.examAddress = examAddress;
    }

    public ArrayList<String> getConversationIdList() {
        return this.conversationIdList;
    }

    public void setConversationIdList(ArrayList<String> conversationIdList) {
        this.conversationIdList = conversationIdList;
    }

    public ArrayList<String> getNotificationIdList() {
        return this.notificationIdList;
    }

    public void setNotificationIdList(ArrayList<String> notificationIdList) {
        this.notificationIdList = notificationIdList;
    }

}