public class Notification extends Conversation {

    private Time sendTime;
    private String notificationId;
    private String courseCode;
    private String examId;
    private String message;

    public Time getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Time sendTime) {
        this.sendTime = sendTime;
    }

    public String getNotificationId() {
        return this.notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getCourseCode() {
        return this.courseId;
    }

    public void setCourseCode(String courseId) {
        this.courseId = courseId;
    }

    public String getExamId() {
        return this.examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getMessage() {
        return this. message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}