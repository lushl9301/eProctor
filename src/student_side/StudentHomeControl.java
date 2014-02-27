public class StudentHomeControl {

    private Student currrentStudent;

    public StudentHomeControl() {

    }

    public void getUserInfo() {
        ArrayList<String> query = new ArrayList<String>();
        query.addAll(Arrays.asList(new String[] {"realName", "courseIdList", "examIdList"}));
        ArrayList<String> result = Main.server.fetch("User", "UID", query);

        /*
        display user info here...
         */
    }
}