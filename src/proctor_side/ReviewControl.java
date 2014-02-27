public class ReviewControl {

    private ArrayList<Exam> invigilatedExam = new ArrayList<Exam>();
    private ArrayList<Recording> examRecordingList = new ArrayList<Recording>();
    private Proctor currentProctor;

    public ReviewControl() {
        this.fetchInvigilatedExam();
        /*
        user select an exam
         */
        Exam selectedExam = null;
        this.getExamRecordingList(selectedExam);
        Integer selectedRecordingID;

        recordingDisplayControl.displayRecording(selectedRecordingID);
    }

    public void fetchInvigilatedExam() {
        /*
        fetch proctor invigilated exam list from server
         */
        this.invigilatedExam = server.getInvigilatedExam(this.currentProctor);
    }

    public void getExamRecordingList(Exam selectedExam) {
        /*
        get exam Recording list from selectedExam
         */
        this.examRecordingList = selectedExam.getCameraRecordingIdList();
    }

}