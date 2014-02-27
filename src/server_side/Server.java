public class Server {

    public Server() {

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
