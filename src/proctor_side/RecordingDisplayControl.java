public class RecordingDisplayControl {
    
    private Recording selectedRecording;

    public RecordingDisplayControl() {

    }

    public void displayRecordingList(Integer selectedRecordingID) {
        ArrayList<String> query = new ArrayList<String>();
        query.addAll(Arrays.asList(new String[] {"recordingID"}));
        /*
        display Recording list to UI
         */
        //this.fetchRecording(Integer selectedRecordingID);
        
    }

    public void displayRecording(Student selectedStudent) {
        /*
        display selected student recording during exam
         */
    }

//    public void fetchRecording(Integer selectedRecordingID) {
//        /*
//        fetch selected recording from server according to it's ID
//         */
//        this.selectedRecording = server.getRecording(selectedRecordingID);
//    }

}