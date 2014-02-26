class BookingControl {

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

    public void fetchRegisteredCourse(Student currentStudent) {
        /*
        fetch registered course from server
         */
        this.registeredCourse = server.getRegisterdCourse(currentStudent);
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

    //same as current booking in UI windows
    public ArrayList<Exam> checkComingExam(Student currentStudent) {
        this.updateComingExam();
        return this.comingExam;
    }

//==========book exam==============================
//=================================================

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
        return server.bookExam(currentStudent, selectedExam);
    }

}