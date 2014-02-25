public class Notification {

  private Time sendTime;
  private String messageId;
  private String notificationId;
  private String courseId;
  private String examId;

  public Time getSendTime() {
    return this.sendTime;
  }

  public void setSendTime(Time sendTime) {
    this.sendTime = sendTime;
  }

  public String getMessageId() {
    return this.messageId;
  }

  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }

  public String getNotificationId() {
    return this.notificationId;
  }

  public void setNotificationId(String notificationId) {
    this.notificationId = notificationId;
  }

  public String getCourseId() {
    return this.courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getExamId() {
    return this.examId;
  }

  public void setExamId(String examId) {
    this.examId = examId;
  }

}