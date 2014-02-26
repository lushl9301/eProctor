public class Course {

  private String courseCode;
  private String courseTitle;
  private ArrayList<String> examId;
  private ArrayList<String> studentId;
  private ArrayList<Integer> result;

  public String getCourseCode() {
    return this.courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }

  public String getCourseTitle() {
    return this.courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  public ArrayList<String> getExamId() {
    return this.examId;
  }

  public void setExamId(ArrayList<String> examId) {
    this.examId = examId;
  }

  public ArrayList<String> getStudentId() {
    return this.studentId;
  }

  public void setStudentId(ArrayList<String> studentId) {
    this.studentId = studentId;
  }

  public ArrayList<Integer> getResult() {
    return this.result;
  }

  public void setResult(ArrayList<Integer> result) {
    this.result = result;
  }

}