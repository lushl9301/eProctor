public class Course {

    private String courseCode;
    private String courseTitle;
    private ArrayList<String> examIdList;
    private ArrayList<String> studentIdList;

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

    public ArrayList<String> getExamIdList() {
        return this.examIdList;
    }

    public void setExamIdList(ArrayList<String> examIdList) {
        this.examIdList = examIdList;
    }

    public ArrayList<String> getStudentIdList() {
        return this.studentIdList;
    }

    public void setStudentIdList(ArrayList<String> studentIdList) {
        this.studentIdList = studentIdList;
    }

    public ArrayList<Integer> getResultList() {
        return this.resultList;
    }

    public void setResultList(ArrayList<Integer> resultList) {
        this.resultList = resultList;
    }

}