package student_side;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.table.AbstractTableModel;

import com.mongodb.*;

import org.bson.types.ObjectId;

import entity.Main;

public class StudentHomeController {

	private ArrayList<ObjectId> courseIdRecord;
	private ArrayList<ObjectId>	sessionIdRecord;

	public StudentHomeController(String username) throws Exception {
		courseIdRecord = new ArrayList<ObjectId>();
		sessionIdRecord = new ArrayList<ObjectId>();
	}

	public void exit() {
		Main.studentHomeUI.setVisible(false);
		System.exit(0);
	}

	public void logout() {
		Main.studentHomeUI.setVisible(false);
	}

	public void about() {

	}

	public ArrayList<ArrayList<String>> getTableCurrentBookings() {
		QueryBuilder qb = new QueryBuilder();
		qb.put("student_id").is(Main.user_id).put("takenStatus").is(false);
		DBCursor cursor = Main.mongoHQ.record.find(qb.get());
		ArrayList<ArrayList<String>> currentBookingsRecords = new ArrayList<ArrayList<String>>();
		while (cursor.hasNext()) {
			ArrayList<String> temp = new ArrayList<String>();
			DBObject obj = cursor.next();
			//ID
			temp.add(((ObjectId) obj.get("_id")).toString());

			// Course
			QueryBuilder qbQuery = new QueryBuilder();
			qbQuery.put("_id").is(obj.get("course_id"));
			DBObject tObj = Main.mongoHQ.course.findOne(qbQuery.get());
			temp.add((String) tObj.get("name"));

			//Session
			qbQuery = new QueryBuilder();
			qbQuery.put("_id").is(obj.get("session_id"));
			tObj = Main.mongoHQ.session.findOne(qbQuery.get());
			Date startDate = (Date) tObj.get("start");
			Date endDate = (Date) tObj.get("end");
			SimpleDateFormat startFormat = new SimpleDateFormat ("dd.MM.yyyy E kk:mm");
			SimpleDateFormat endFormat = new SimpleDateFormat ("'-'kk:mm");
			String str = startFormat.format(startDate) + endFormat.format(endDate);

			temp.add(str);

			currentBookingsRecords.add(temp);
		}
		return currentBookingsRecords;
	}

	public ArrayList<String> getListAvailableCourses() {
		BasicDBObject query = new BasicDBObject();
		query.put("_id", Main.user_id);
		DBObject obj = Main.mongoHQ.student.findOne(query);
		BasicDBList e = (BasicDBList) obj.get("enrolledNotTested");
		ArrayList<String> coursesRecord = new ArrayList<String>();
		for (int i = 0; i < e.size(); i++) {
			ObjectId t = new ObjectId(e.get(i).toString());
			query.clear();
			query.put("_id", t);
			obj = Main.mongoHQ.course.findOne(query);
			courseIdRecord.add((ObjectId) obj.get("_id"));
			coursesRecord.add((String) obj.get("code") + " " + ((String) obj.get("name")));
		}
		return coursesRecord;
	}

	public ArrayList<String> getListAvailableSessions(int selectedCourseIndex) {
		if (selectedCourseIndex == -1)
			return null;
		ObjectId courseId = courseIdRecord.get(selectedCourseIndex);
		QueryBuilder qb = new QueryBuilder();
		qb.put("_id").is(courseId);

		DBObject obj = Main.mongoHQ.course.findOne(qb.get());
		BasicDBList e = (BasicDBList) obj.get("sessions");
		ArrayList<String> sessionsRecord = new ArrayList<String>();
		for (int i = 0; i < e.size(); i++) {
			ObjectId t = new ObjectId(e.get(i).toString());			
			qb.put("_id").is(t);
			obj = Main.mongoHQ.session.findOne(qb.get());
			sessionIdRecord.add((ObjectId) obj.get("_id"));
			Date startDate = (Date) obj.get("start");
			Date endDate = (Date)obj.get("end");
			SimpleDateFormat startFormat = new SimpleDateFormat ("dd.MM.yyyy E kk:mm");
			SimpleDateFormat endFormat = new SimpleDateFormat ("'-'kk:mm");
			String str = startFormat.format(startDate) + endFormat.format(endDate);
			sessionsRecord.add(str);
		}
		return sessionsRecord;
	}

	public ArrayList<ArrayList<String>> getTableReview() {
		//BasicDBObject query = new BasicDBObject();
		QueryBuilder qb = new QueryBuilder();
		qb.put("student_id").is(Main.user_id).put("takenStatus").is(true);
		DBCursor cursor = Main.mongoHQ.record.find(qb.get());
		ArrayList<ArrayList<String>> reviewRecords = new ArrayList<ArrayList<String>>();
		while (cursor.hasNext()) {
			ArrayList<String> temp = new ArrayList<String>();
			DBObject obj = cursor.next();
			//ID
			temp.add(((ObjectId) obj.get("_id")).toString());

			// Course
			QueryBuilder qbQuery = new QueryBuilder();
			qbQuery.put("_id").is(obj.get("course_id"));
			DBObject tObj = Main.mongoHQ.course.findOne(qbQuery.get());
			temp.add((String) tObj.get("name"));

			//Session
			qbQuery = new QueryBuilder();
			qbQuery.put("_id").is(obj.get("session_id"));
			tObj = Main.mongoHQ.session.findOne(qbQuery.get());
			Date startDate = (Date) tObj.get("start");
			Date endDate = (Date) tObj.get("end");
			SimpleDateFormat startFormat = new SimpleDateFormat ("dd.MM.yyyy E kk:mm");
			SimpleDateFormat endFormat = new SimpleDateFormat ("'-'kk:mm");
			String str = startFormat.format(startDate) + endFormat.format(endDate);
			temp.add(str);

			temp.add((String) obj.get("grade"));
			temp.add((String) obj.get("remark"));
			reviewRecords.add(temp);
		}
		return reviewRecords;


	}

	public String getInformation() {
		// ArrayList<String> neededInfoList = new
		// ArrayList<String>(Arrays.asList("realname", "coursecode", "examid"));
		// ArrayList<ArrayList<String>> information =
		// Main.client.fetchData("username", Main.currentUser.getUserId(),
		// neededInfoList);

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
		// implement way: polling request
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

	public void makeRequestOfABooking(int index) {
		if (index == -1) {
			return;
		}
		// String examId = "";
		// get examId for textbox!!!
		// set up a new windows
		// fill the windows with old data

		// I assume all the data already fetched during getCurrentBookingList();
	}

	public void bookNewSession(int selectedCourseIndex, int selectedSessionIndex) {
		if (selectedSessionIndex == -1)
			return;
		ObjectId courseId = courseIdRecord.get(selectedCourseIndex);
		ObjectId sessionId = sessionIdRecord.get(selectedSessionIndex);
//		Student enrolledNotTested
//		Record
		DBObject listItem = new BasicDBObject("enrolledNotTested", courseId);
		DBObject findQuery = new BasicDBObject("_id", Main.user_id);
		DBObject updateQuery = new BasicDBObject("$pull", new BasicDBObject("enrolledNotTested", courseId));

		Main.mongoHQ.student.update(findQuery, updateQuery);

		BasicDBObjectBuilder document = new BasicDBObjectBuilder();
		document.add("course_id", courseId).add("session_id", sessionId).add("grade", "").add("remark", "").add("student_id", Main.user_id).add("takenStatus", false);
		Main.mongoHQ.record.insert(document.get());
	}

	public void getAboutMessage() {
		// TODO Auto-generated method stub

	}

	public void sendRecording() {
		// new client_side.GrabberShow();
	}

	public URL getExamLink() throws Exception {
		String link = null;
		// link = Main.client.fetchData("examId", examId, ArrayList<String>);
		link = "https://docs.google.com/forms/d/1rEgKT7uRoRrxqenORs5aKo8wIkcsb1waph28glVWF1s/viewform";
		return (new URL(link));
	}

}
