public class Server {

    public Server() {

    }

    public ArrayList<String> fetchData(String tableName, String key, ArrayList<String> query) {
        /*
        use socket to change this request to
        "number of string" "table name" "key" "query1" "query2" ....
         */
        
        /*
        use socket to change this return
        "number of returned string" "string1" "string2"...
         */
    }


//==========LoginControl===========================
//=================================================
    public boolean verifyUser(String userID, String md5, String domain) {
        return true;
    }

//==========BookingControl=========================
//=================================================
    public ArrayList<Exam> getBookedExam(Student currentStudent) {
        /*
        query here
         */
    }
    public ArrayList<Course> getRegisteredCourse(Student currentStudent) {
        /*
        query here
         */
    }
    public boolean bookExam(Student currentStudent, Exam selectedExam) {
        /*
        query here
         */
    }

//==========CheckExamResultControl=================
//=================================================
    //public ArrayList<Course> getRegisteredCourse()


//==========ReviewControl==========================
//=================================================
    public ArrayList<Exam> getInvigilatedExam(this.currentProctor) {
        /*
        query here
         */
    }

//==========RecordingDisplayControl================
//=================================================
    public Recording getRecording(Integer selectedRecordingID) {
        /*
        query here
         */
    }

//==========MessageControl=========================
//=================================================


//==========ProctorHomeControl=====================
//=================================================

//==========InvigilateExamControl==================
//=================================================

//==========StudentHomeControl=====================
//=================================================

//==========TakeExamControl========================
//=================================================


}
