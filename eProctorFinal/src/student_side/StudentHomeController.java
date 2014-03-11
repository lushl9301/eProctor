package student_side;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.AbstractListModel;
import javax.swing.table.AbstractTableModel;

import com.mongodb.*;

import org.bson.types.ObjectId;

import entity.Main;

public class StudentHomeController {

	private ArrayList<ArrayList<String>> currentBookingsRecords;
	private ArrayList<ArrayList<String>> reviewRecords;
	private ArrayList<String> coursesRecord;
	private ArrayList<String> sessionsRecord;

	public StudentHomeController(String username) throws Exception {
		Main.studentHomeUI = new StudentHomeUI();
		Main.studentHomeUI.setVisible(true);
		fetchPnReview();
		fetchPnBooking();
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

	public void fetchPnStatus() {
		Main.studentHomeUI.txtpnInformation.setText(Main.studentHomeController
				.getInformation());
		Main.studentHomeUI.txtpnRecentMessages
				.setText(Main.studentHomeController.getRecentMessage());
	}

	public void fetchPnBooking() {
		fetchTableCurrentBookings();
		fetchListAvailableCourses();
		ObjectId tmp = new ObjectId("531d9cd953e6061101dddc91");
		fetchListAvailableSessions(tmp);
	}

	public void fetchTableCurrentBookings() {
		QueryBuilder qb = new QueryBuilder();
		qb.put("student_id").is(Main.user_id.toString()).put("takenStatus").is(false);
		DBCursor cursor = Main.mongoHQ.record.find(qb.get());
		currentBookingsRecords = new ArrayList<ArrayList<String>>();
		while (cursor.hasNext()) {
			ArrayList<String> temp = new ArrayList<String>();
			DBObject obj = cursor.next();
			//ID
			temp.add(((ObjectId) obj.get("_id")).toString());

			// Course
			QueryBuilder qbQuery = new QueryBuilder();
			qbQuery.put("_id").is(new ObjectId((String) obj.get("course_id")));
			DBObject tObj = Main.mongoHQ.course.findOne(qbQuery.get());
			temp.add((String) tObj.get("name"));
			
			//Session
			qbQuery = new QueryBuilder();
			qbQuery.put("_id").is(new ObjectId((String) obj.get("session_id")));
			tObj = Main.mongoHQ.session.findOne(qbQuery.get());
			
			Date startDate = new Date(Long.parseLong((String) tObj.get("start")));
			Date endDate = new Date(Long.parseLong((String) tObj.get("end")));
			SimpleDateFormat startFormat = new SimpleDateFormat ("dd.MM.yyyy E kk:mm");
			SimpleDateFormat endFormat = new SimpleDateFormat ("'-'kk:mm");
			String str = startFormat.format(startDate) + endFormat.format(endDate);
			
			temp.add(str);
			
			currentBookingsRecords.add(temp);
		}
		TableCurrentBookingsModel tableCurrentBookingsModel = new TableCurrentBookingsModel(
				currentBookingsRecords);
		Main.studentHomeUI.tableCurrentBookings
				.setModel(tableCurrentBookingsModel);
		Main.studentHomeUI.tableReview.getColumnModel().getColumn(0)
				.setPreferredWidth(100);
		Main.studentHomeUI.tableReview.getColumnModel().getColumn(1)
				.setPreferredWidth(200);
		Main.studentHomeUI.tableReview.getColumnModel().getColumn(2)
				.setPreferredWidth(170);
	}

	public void fetchListAvailableCourses() {
		BasicDBObject query = new BasicDBObject();
		query.put("_id", Main.user_id);
		DBObject obj = Main.mongoHQ.student.findOne(query);
		BasicDBList e = (BasicDBList) obj.get("enrolledCourses");
		coursesRecord = new ArrayList<String>();
		for (int i = 0; i < e.size(); i++) {
			ObjectId t = new ObjectId(e.get(i).toString());
			query.clear();
			query.put("_id", t);
			obj = Main.mongoHQ.course.findOne(query);
			coursesRecord.add((String) obj.get("code") + " " + ((String) obj.get("name")));
		}
		
		ListArrayListModel listArrayListModel = new ListArrayListModel(
				coursesRecord);
		Main.studentHomeUI.listAvailableCourses.setModel(listArrayListModel);
	}

	public void fetchListAvailableSessions(ObjectId courseId) {
		BasicDBObject query = new BasicDBObject();
		query.put("_id", courseId);
		DBObject obj = Main.mongoHQ.course.findOne(query);
		BasicDBList e = (BasicDBList) obj.get("sessions");
		sessionsRecord = new ArrayList<String>();
		for (int i = 0; i < e.size(); i++) {
			ObjectId t = new ObjectId(e.get(i).toString());
			query.clear();
			query.put("_id", t);
			obj = Main.mongoHQ.session.findOne(query);
			Date startDate = new Date(Long.parseLong((String) obj.get("start")));
			Date endDate = new Date(Long.parseLong((String) obj.get("end")));
			SimpleDateFormat startFormat = new SimpleDateFormat ("dd.MM.yyyy E kk:mm");
			SimpleDateFormat endFormat = new SimpleDateFormat ("'-'kk:mm");
			String str = startFormat.format(startDate) + endFormat.format(endDate);
			sessionsRecord.add(str);
//			sessionsRecord.add((String) obj.get("name"));// here to modify
		}
		ListArrayListModel listArrayListModel = new ListArrayListModel(
				sessionsRecord);
		Main.studentHomeUI.listAvailableSessions.setModel(listArrayListModel);
	}

	public void fetchPnReview() {
		//BasicDBObject query = new BasicDBObject();
		QueryBuilder qb = new QueryBuilder();
		qb.put("student_id").is(Main.user_id.toString()).put("takenStatus").is(true);
		DBCursor cursor = Main.mongoHQ.record.find(qb.get());
		reviewRecords = new ArrayList<ArrayList<String>>();
		while (cursor.hasNext()) {
			ArrayList<String> temp = new ArrayList<String>();
			DBObject obj = cursor.next();
			//ID
			temp.add(((ObjectId) obj.get("_id")).toString());

			// Course
			QueryBuilder qbQuery = new QueryBuilder();
			qbQuery.put("_id").is(new ObjectId((String) obj.get("course_id")));
			DBObject tObj = Main.mongoHQ.course.findOne(qbQuery.get());
			temp.add((String) tObj.get("name"));
			
			//Session
			qbQuery = new QueryBuilder();
			qbQuery.put("_id").is(new ObjectId((String) obj.get("session_id")));
			tObj = Main.mongoHQ.session.findOne(qbQuery.get());
			Date startDate = new Date(Long.parseLong((String) tObj.get("start")));
			Date endDate = new Date(Long.parseLong((String) tObj.get("end")));
			SimpleDateFormat startFormat = new SimpleDateFormat ("dd.MM.yyyy E kk:mm");
			SimpleDateFormat endFormat = new SimpleDateFormat ("'-'kk:mm");
			String str = startFormat.format(startDate) + endFormat.format(endDate);
			temp.add(str);
			
			temp.add((String) obj.get("grade"));
			temp.add((String) obj.get("remark"));
			reviewRecords.add(temp);
		}
		TableReviewModel tableReviewModel = new TableReviewModel(reviewRecords);
		Main.studentHomeUI.tableReview.setModel(tableReviewModel);
//		Main.studentHomeUI.tableReview.getColumnModel().getColumn(0)
//				.setPreferredWidth(50);
//		Main.studentHomeUI.tableReview.getColumnModel().getColumn(1)
//				.setPreferredWidth(220);
//		Main.studentHomeUI.tableReview.getColumnModel().getColumn(2)
//				.setPreferredWidth(300);
//		Main.studentHomeUI.tableReview.getColumnModel().getColumn(3)
//				.setPreferredWidth(40);
//		Main.studentHomeUI.tableReview.getColumnModel().getColumn(4)
//				.setPreferredWidth(90);
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

	// updateExamInfo(examId); called by UI listener
	// public void updateExamInfo(String examId) throws Exception {
	// // TODO
	// // get text from boxes
	// // set as an arrayList
	// ArrayList<String> newBookingInfo = new ArrayList<String>();
	// Main.client.updateData("username", Main.currentUser.getUserName(),
	// examId, newBookingInfo);
	// }

	// public String[] getAvailableCourseList() {
	// // ArrayList<String> neededInfoList = new
	// // ArrayList<String>(Arrays.asList("realname", "coursecode",
	// // "courestitle"));
	// // ArrayList<ArrayList<String>> currentBookingList =
	// // Main.client.fetchData("username", Main.currentUser.getUserName(),
	// // neededInfoList);
	// ArrayList<ArrayList<String>> availableCourseList = new
	// ArrayList<ArrayList<String>>();
	// {
	// ArrayList<String> a = new ArrayList<String>();
	// ArrayList<String> aa = new ArrayList<String>();
	// String b = "CE3007";
	// String bb = "DSP";
	// String c = "CE2004";
	// String cc = "CSA";
	// a.add(b);
	// a.add(bb);
	// aa.add(c);
	// aa.add(cc);
	// availableCourseList.add(a);
	// availableCourseList.add(aa);
	// }
	//
	// String[] result = new String[availableCourseList.size()];
	// ArrayList<String> availableCourseListInShort = new ArrayList<String>();
	// for (ArrayList<String> s : availableCourseList) {
	// String temp = "";
	// for (String e : s) {
	// temp += "   \t   \t" + e;
	// }
	// availableCourseListInShort.add(temp);
	// }
	// result = availableCourseListInShort.toArray(result);
	// return result;
	// }
	//
	// public String[] getAvailableExamSession(int selectedIndex) {
	// String[] result = new String[] { " " };
	// if (selectedIndex == -1) {
	// return result;
	// }
	// // ArrayList<ArrayList<String>> availableExamSession =
	// // Main.client.fetchData();
	//
	// return result;
	// }
	//
	// // and one more choice/ Use overriding
	// public String[] getAvailabelExamSession(String coursecode) throws
	// Exception {
	// ArrayList<String> neededInfoList = new ArrayList<String>(Arrays.asList(
	// "examid", "starttime", "endtime", "examaddress"));
	// ArrayList<ArrayList<String>> availableExamSession = Main.client
	// .fetchData("coursecode", coursecode, neededInfoList);
	// String[] result = new String[] { " " };
	// // convert availableExamSession to String[]
	// // write to UI
	// return result;
	// }

	// public String[] getExamResultList() throws Exception {
	// ArrayList<String> neededInfoList = new ArrayList<String>(Arrays.asList(
	// "examid", "coursecode", "coursetitle", "result"));
	// ArrayList<ArrayList<String>> examResultList = Main.client.fetchData(
	// "username", Main.currentUser.getUserName(), neededInfoList);
	// String[] result = new String[] { " " };
	// // convert examResultList to String[]
	// // write to UI
	// return result;
	// }

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

	public class TableReviewModel extends AbstractTableModel {

		private ArrayList<ArrayList<String>> records;

		public TableReviewModel(ArrayList<ArrayList<String>> records) {
			this.records = records;
		}

		public int getRowCount() {
			return records.size();
		}

		public int getColumnCount() {
			return 5;
		}

		public String getColumnName(int column) {
			switch (column) {
			case 0:
				return "Record ID";
			case 1:
				return "Course";
			case 2:
				return "Session";
			case 3:
				return "Grade";
			case 4:
				return "Remark";
			default:
				return "";
			}
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			return records.get(rowIndex).get(columnIndex);
		}
	}

	public class TableCurrentBookingsModel extends AbstractTableModel {

		private ArrayList<ArrayList<String>> records;

		public TableCurrentBookingsModel(ArrayList<ArrayList<String>> records) {
			this.records = records;
		}

		@Override
		public int getRowCount() {
			return records.size();
		}

		@Override
		public int getColumnCount() {
			return 3;
		}

		@Override
		public String getColumnName(int column) {
			switch (column) {
			// "Record ID", "Course", "Session", "Grade", "Remark"
			case 0:
				return "Record ID";
			case 1:
				return "Course";
			case 2:
				return "Session";
			default:
				return "";
			}
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			return records.get(rowIndex).get(columnIndex);
		}
	}

	public class ListArrayListModel extends AbstractListModel {

		private ArrayList<String> records;

		public ListArrayListModel(ArrayList<String> records) {
			this.records = records;
		}

		public Object getElementAt(int index) {
			return records.get(index);
		}

		public int getSize() {
			return records.size();
		}

	}
}
