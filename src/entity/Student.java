public class Student {

  private String userName;
  private String userPassword;
  private ArrayList<String> courseId;
  private ArrayList<String> examId;
  private ArrayList<String> userId;
  private ArrayList<String> cameraRecordingId;
  private ArrayList<String> screenRecordingId;
  private ArrayList<Integer> examResult;
  private ArrayList<String> conversationId;

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

  public ArrayList<String> getCourseId() {
    return this.courseId;
  }

  public void setCourseId(ArrayList<String> courseId) {
    this.courseId = courseId;
  }

  public ArrayList<String> getExamId() {
    return this.examId;
  }

  public void setExamId(ArrayList<String> examId) {
    this.examId = examId;
  }

  public ArrayList<String> getUserId() {
    return this.userId;
  }

  public void setUserId(ArrayList<String> userId) {
    this.userId = userId;
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

  public ArrayList<Integer> getExamResult() {
    return this.examResult;
  }

  public void setExamResult(ArrayList<Integer> examResult) {
    this.examResult = examResult;
  }

  public ArrayList<String> getConversationId() {
    return this.conversationId;
  }

  public void setConversationId(ArrayList<String> conversationId) {
    this.conversationId = conversationId;
  }

}