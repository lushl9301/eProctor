class BookingControl {

    private ArrayList<Course> registeredCourse;
    private ArrayList<Exam> bookedExam;
    private ArrayList<Exam> comingExam;
    private Student currentStudent;

    public BookingControl() {
        this.fetchBookedExam();
        this.fetchRegisteredCourse();
        this.comingExam = new ArrayList<Exam>();
        for (Exam e : bookedExams) {
            if (e.timePassed()) {
                this.comingExam.add(e);
            }
        }
    }

    public void fetchBookedExam() {
        /*
        fetch booked exam from server
         */
        this.bookedExam = server.getBookedExam(this.currentStudent)
    }
    public void fetchRegisteredCourse() {
        /*
        fetch registered course from server
         */
        this.registeredCourse = server.getRegisterdCourse(this.currentStudent);
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