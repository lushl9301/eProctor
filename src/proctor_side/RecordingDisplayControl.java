public class RecordingDisplayControl {
    
    private Recording selectedRecording;

    public RecordingDisplayControl() {

    }

    public displayRecording(Integer selectedRecordingID) {
        this.fetchRecording(Integer selectedRecordingID);
        /*
        display selectedRecording to UI
         */
    }

    public void fetchRecording(Integer selectedRecordingID) {
        /*
        fetch selected recording from server according to it's ID
         */
        this.selectedRecording = server.getRecording(selectedRecordingID);
    }


}