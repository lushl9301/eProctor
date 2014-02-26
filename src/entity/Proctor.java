public class Proctor {

  private String userName = null;
  private String userPassword = null;
  private ArrayList<String> courseId = new ArrayList<String>();
  private ArrayList<String> examId = new ArrayList<String>();
  private ArrayList<String> conversationId = new ArrayList<String>();
  private String userId = null;
  private ArrayList<String> cameraRecordingId = new ArrayList<String>();
  private ArrayList<String> screenRecordingId = new ArrayList<String>();

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

  public ArrayList<String> getConversationId() {
    return this.conversationId;
  }

  public void setConversationId(ArrayList<String> conversationId) {
    this.conversationId = conversationId;
  }

  public String getUserId() {
    return this.userId;
  }

  public void setUserId(String userId) {
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

}