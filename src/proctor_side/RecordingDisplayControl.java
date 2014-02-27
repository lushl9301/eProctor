public class RecordingDisplayControl {
    
    private Recording selectedRecording;

    public RecordingDisplayControl() {

    }

    public void displayRecording(Integer selectedRecordingID) {
        this.fetchRecording(Integer selectedRecordingID);
        /*
        display selectedRecording to UI
         */
    }

    public void displayRecording(Student selectedStudent) {
        /*
        display selected student recording during exam
         */
    }

    public void fetchRecording(Integer selectedRecordingID) {
        /*
        fetch selected recording from server according to it's ID
         */
        this.selectedRecording = server.getRecording(selectedRecordingID);
    }

}