package proctor_side;

import java.util.ArrayList;

import entity.Main;

public class ProctorHomeController {

    public ProctorHomeController() {
    }

    public void exit() {
        Main.proctorHomeUI.setVisible(false);
        System.exit(0);
    }

    public void logout() {
        Main.proctorHomeUI.setVisible(false);
        Main.loginUI.setVisible(true);
    }

    public void sendMessage(String message) throws Exception {
        Main.client.sendMessage(message);
    }

    public String getInformation() {

        // ArrayList<ArrayList<String>> information = Main.client.fetchData();
        ArrayList<ArrayList<String>> information = new ArrayList<ArrayList<String>>();
        {
            ArrayList<String> a = new ArrayList<String>();
            String b = "Welcome, Gong Yue from CE2006 BCE2";
            String c = "You have:   2 exams in the next week.";
            String d = "5 exams in the next few months.";
            a.add(b);
            a.add(c);
            ArrayList<String> aa = new ArrayList<String>();
            aa.add(d);
            information.add(a);
            information.add(aa);
        }
        String result = "";
        for (ArrayList<String> s : information) {
            for (String e : s) {
                result += "      " + e;
            }
            result += "\n";
        }
        return result.substring(6);
    }

    public String getRecentMessage() {
        // ArrayList<ArrayList<String>> recentMessage = Main.client.fetchData();
        ArrayList<ArrayList<String>> recentMessage = new ArrayList<ArrayList<String>>();
        {
            ArrayList<String> a = new ArrayList<String>();
            String b = "Laoshi";
            String c = "03/03/2014 13:20";
            a.add(b);
            a.add(c);
            ArrayList<String> aa = new ArrayList<String>();
            String d = "ni jin tian biao xian hen hao";
            String e = "geilaozi qu si";
            String f = "haoba";
            aa.add(d);
            aa.add(e);
            aa.add(f);
            recentMessage.add(a);
            recentMessage.add(aa);
        }

        String result = "";
        result += "Send From: " + recentMessage.get(0).get(0) + "\n";
        result += "Time: " + recentMessage.get(0).get(1) + "\n";
        for (String e : recentMessage.get(1)) {
            result += "      " + e;
        }
        return result;
    }

    public String[] getCurrentBookingList() {
        // ArrayList<ArrayList<String>> currentBookingList =
        // Main.client.fetchData();
        ArrayList<ArrayList<String>> currentBookingList = new ArrayList<ArrayList<String>>();
        {
            ArrayList<String> a = new ArrayList<String>();
            ArrayList<String> aa = new ArrayList<String>();
            String b = "CE3007";
            String bb = "DSP";
            String c = "CE2004";
            String cc = "CSA";
            a.add(b);
            a.add(bb);
            aa.add(c);
            aa.add(cc);
            currentBookingList.add(a);
            currentBookingList.add(aa);
        }

        String[] result = new String[currentBookingList.size()];
        ArrayList<String> currentBookingListInShort = new ArrayList<String>();
        for (ArrayList<String> s : currentBookingList) {
            String temp = "";
            for (String e : s) {
                temp += "   \t   \t" + e;
            }
            currentBookingListInShort.add(temp);
        }
        result = currentBookingListInShort.toArray(result);
        return result;
    }

    public void makeRequestOfABooking(int index) {
        System.out.println("asdfasdfasdf" + index);
        if (index != -1) {
            // TODO call client here
        }
    }

    public String[] getAvailableCourseList() {
        // ArrayList<ArrayList<String>> availableCourseList =
        // Main.client.fetchData();
        ArrayList<ArrayList<String>> availableCourseList = new ArrayList<ArrayList<String>>();
        {
            ArrayList<String> a = new ArrayList<String>();
            ArrayList<String> aa = new ArrayList<String>();
            String b = "CE3007";
            String bb = "DSP";
            String c = "CE2004";
            String cc = "CSA";
            a.add(b);
            a.add(bb);
            aa.add(c);
            aa.add(cc);
            availableCourseList.add(a);
            availableCourseList.add(aa);
        }

        String[] result = new String[availableCourseList.size()];
        ArrayList<String> availableCourseListInShort = new ArrayList<String>();
        for (ArrayList<String> s : availableCourseList) {
            String temp = "";
            for (String e : s) {
                temp += "   \t   \t" + e;
            }
            availableCourseListInShort.add(temp);
        }
        result = availableCourseListInShort.toArray(result);
        return result;
    }

    public String[] getAvailableExamSession(int selectedIndex) {
        String[] result = new String[] { " " };
        if (selectedIndex == -1) {
            return result;
        }
        // ArrayList<ArrayList<String>> availableExamSession =
        // Main.client.fetchData();

        return result;
    }

    public String[] getStudentList() {
        return null;
    }

    public void invigilateAStudent(int index) {
        String studentId = "";
        if (index == -1) {
            return;
        }
        new client_side.GrabberShow(studentId);
    }

    public void getAboutMessage() {
        // Main.about = new entity.About();
        // Main.about.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // Main.about.setVisible(true);
    }

}
