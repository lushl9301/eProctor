class BookingSys {

    private ArrayList<Exam> comingExam;
    private Student currentStudent;

    public BookingSys() {
        ArryaList<Exam> allExams = server.getBookedExams(currentStudent);
        this.comingExam = new ArrayList<Exam>();
        for (Exam e : allExams) {
            if (e.timePassed()) {
                this.comingExam.add(e);
            }
        }
    }

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


}