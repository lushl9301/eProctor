public class Exam {

  private Time startTime = null;
  private Time endTime = null;
  private ArrayList<String> proctorId = new ArrayList<String>();
  private ArrayList<String> studentId = new ArrayList<String>();
  private ArrayList<Integer> result = new ArrayList<Integer>();
  private String courseCode = null;
  private String examId = null;
  private ArrayList<String> cameraRecordingId = new ArrayList<String>();
  private ArrayList<String> screenRecordingId = new ArrayList<String>();
  private String examAddress = null;
  private ArrayList<String> conversationId = new ArrayList<String>();
  private ArrayList<String> notificationId = new ArrayList<String>();

  public Exam() {

  }

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