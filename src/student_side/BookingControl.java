class BookingSys {

    private ArrayList<Course> registeredCourse;
    private ArrayList<Exam> comingExam;
    private Student currentStudent;

    public BookingSys() {
        ArrayList<Exam> allExams = server.getBookedExams(currentStudent);
        this.comingExam = new ArrayList<Exam>();
        for (Exam e : allExams) {
            if (e.timePassed()) {
                this.comingExam.add(e);
            }
        }
        this.fetchRegisteredCourse();
    }
//==========check coming exam======================
//=================================================
    public void updateComingExam() {
        for (Exam e : this.comingExam) {
            if (e.timePassed()) {
                this.comingExam.remove(e);
            }
        }
    }
    
    public ArrayList<Exam> checkComingExam(Student currentStudent) {
        this.updateComingExam();
        return this.comingExam;
    }

//==========booking exam===========================
//=================================================

    public void fetchRegisteredCourse(Student currentStudent) {
        /*
        fetch register course from server
         */
        this.registeredCourse = server.getRegisterdCourse(currentStudent);
    }

    public ArrayList<Exam> getCourseExam(Course selectedCourse) {
        /*
        get exam session from certain course
         */
        return selectedCourse.getExamList();
    }

    public boolean bookExam(Exam selectedExam) {
        /*
        register/book exam. update info to server
         */
        //return 
    }

}