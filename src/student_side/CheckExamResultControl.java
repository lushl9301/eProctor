class CheckExamResultControl {

    private ArrayList<Course> registeredCourse;
    private ArrayList<String> examResult = new ArrayList<String>();
    private Student currentStudent;

    public CheckExamResultControl() {
        this.fetchRegisteredCourse();
    }
    public void fetchRegisteredCourse(Student currentStudent) {
        /*
        fetch registered course from server
         */
        this.registeredCourse = server.getRegisterdCourse(currentStudent);
    }
//==========check exam result======================
//=================================================
/*
    public void fetchExamResult(Student currentStudent) {
        /*
        //fetch announced exam results from server
        //
        this.examResult = server.getExamResult(this.registeredCourse);
        for (String e : this.examResult) {
            if (e == null or e.compareTo("") == 0) {
                this.examResult.remove(e);
            }
        }
    }
*/
    public ArrayList<String> getExamResult() {
        /*
        get exam results from all registered course
         */
        for (Course c : this.registeredCourse) {
            String tempExamResult = c.getExamResult();
            if (tempExamResult != null) {
                this.examResult.add(tempExamResult);
            }
        }
        return this.examResult;
    }
}