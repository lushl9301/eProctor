public class InvigilateExamControl {

    private Time examTime;
    private Exam currentExam;
/*    private ArrayList<Recording> studentRecordingList; 
    private ArrayList<Student> invigilatedStudent;
*/
    public InvigilateExamControl() {

    }

    public invigilateExam() {
        /*
        activate InvigilateExamUI
         */
    
        /*
        timer here
         */
        
        this.getInvigilatedStudent();
        this.fetchStudentRecording();
        /*
        display student recording on UI
         */
        if (examTime.compareTo(currentExam.getStartTime()) > 0) {
            this.examOnGoing();
            this.finishExamUpdateData();
        }
    }

    public void updateTimer() {
        /*
        fetch server time as standard time
         */
        this.examTime = server.getServerTime();
    }

    public void getInvigilatedStudent() {
        /*
        get student list from current exam info
         */
        this.invigilatedStudent = currentExam.getStudentList();
    }
    public void fetchStudentRecording() {
        /*
        fetch student recording from server
         */
        this.studentRecordingList = server.getStudentRecording();
    }

    public void examOnGoing() {
        while (!currentExam.timePassed()) {
            /*
            display exam taker
             */
            Integer selectedStudent;
            this.selectOneStudent(selectedStudent);
            videoDisplayControl.displayRecording(selectedStudent);

            /*
            Server socket interrupt
             */
            this.receiveMessage();
            /*
            UI display message
             */
        }
        /*
        UI display end exam info
         */
        /*
        close invigilate exam UI
         */
    }
    public void sendMessage() {
        /*
        ....
         */

    }
    public void changeStudentStatus()) {
        /*
        ...
         */
    }


}