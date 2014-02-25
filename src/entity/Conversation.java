public class Conversation {

  private ArrayList<Time> sendTime;
  private String senderId;
  private String receiverId;
  private ArrayList<String> messageId;
  private ArrayList<String> message;
  private String courseId;
  private String conversationId;
  private String examId;

  public ArrayList<Time> getSendTime() {
    return this.sendTime;
  }

  public void setSendTime(ArrayList<Time> sendTime) {
    this.sendTime = sendTime;
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

  public ArrayList<String> getMessageId() {
    return this.messageId;
  }

  public void setMessageId(ArrayList<String> messageId) {
    this.messageId = messageId;
  }

  public ArrayList<String> getMessage() {
    return this.message;
  }

  public void setMessage(ArrayList<String> message) {
    this.message = message;
  }

  public String getCourseId() {
    return this.courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
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