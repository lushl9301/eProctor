public class Exam {

  private Time startTime;
  private Time endTime;
  private ArrayList<String> proctorId;
  private ArrayList<String> studentId;
  private ArrayList<Integer> result;
  private String courseCode;
  private String examId;
  private ArrayList<String> cameraRecordingId;
  private ArrayList<String> screenRecordingId;
  private String examAddress;
  private ArrayList<String> conversationId;
  private ArrayList<String> notificationId;

  public ArrayList<String> getProctorId() {
    return this.proctorId;
  }

  public void setProctorId(ArrayList<String> proctorId) {
    this.proctorId = proctorId;
  }

  public ArrayList<String> getStudentId() {
    return this.studentId;
  }

  public void setStudentId(ArrayList<String> studentId) {
    this.studentId = studentId;
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

  public ArrayList<String> getCameraRecordingId() {
    return this.cameraRecordingId;
  }

  public void setCameraRecordingId(ArrayList<String> cameraRecordingId) {
    this.cameraRecordingId = cameraRecordingId;
  }

  public ArrayList<String> getScreenRecordingId() {
    return this.screenRecordingId;
  }

  public void setScreenRecordingId(ArrayList<String> screenRecordingId) {
    this.screenRecordingId = screenRecordingId;
  }

  public String getExamAddress() {
    return this.examAddress;
  }

  public void setExamAddress(String examAddress) {
    this.examAddress = examAddress;
  }

  public ArrayList<String> getConversationId() {
    return this.conversationId;
  }

  public void setConversationId(ArrayList<String> conversationId) {
    this.conversationId = conversationId;
  }

  public ArrayList<String> getNotificationId() {
    return this.notificationId;
  }

  public void setNotificationId(ArrayList<String> notificationId) {
    this.notificationId = notificationId;
  }

}