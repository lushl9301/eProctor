package student_side;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.table.AbstractTableModel;

import com.mongodb.*;

import org.bson.types.ObjectId;

import student_side.StudentHomeUI.ExamTab;
import entity.GrabberShow;
import entity.Main;
import entity.MakeARequestUI;
import entity.Messager;

public class StudentHomeController {

	private ArrayList<ObjectId> courseIdRecord;
	private ArrayList<ObjectId> sessionIdRecord;

	public StudentHomeController() throws Exception {
		courseIdRecord = new ArrayList<ObjectId>();
		sessionIdRecord = new ArrayList<ObjectId>();
	}

	public void open() {
		Main.studentHomeUI.setBounds(0, 0, Toolkit.getDefaultToolkit()
				.getScreenSize().width - 10, Toolkit.getDefaultToolkit()
				.getScreenSize().height - 32);
		Main.desktopController.addComponent(Main.studentHomeUI);
		Main.studentHomeUI.setVisible(true);
		Main.studentHomeUI.refreshUI();
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
			// ID
			temp.add(((ObjectId) obj.get("_id")).toString());

			// Course
			QueryBuilder qbQuery = new QueryBuilder();
			qbQuery.put("_id").is(obj.get("course_id"));
			DBObject tObj = Main.mongoHQ.course.findOne(qbQuery.get());
			temp.add((String) tObj.get("name"));

			// Session
			qbQuery = new QueryBuilder();
			qbQuery.put("_id").is(obj.get("session_id"));
			tObj = Main.mongoHQ.session.findOne(qbQuery.get());
			Date startDate = (Date) tObj.get("start");
			Date endDate = (Date) tObj.get("end");
			SimpleDateFormat startFormat = new SimpleDateFormat(
					"dd.MM.yyyy E kk:mm");
			SimpleDateFormat endFormat = new SimpleDateFormat("'-'kk:mm");
			String str = startFormat.format(startDate)
					+ endFormat.format(endDate);

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
			coursesRecord.add((String) obj.get("code") + " "
					+ ((String) obj.get("name")));
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
			Date endDate = (Date) obj.get("end");
			SimpleDateFormat startFormat = new SimpleDateFormat(
					"dd.MM.yyyy E kk:mm");
			SimpleDateFormat endFormat = new SimpleDateFormat("'-'kk:mm");
			String str = startFormat.format(startDate)
					+ endFormat.format(endDate);
			sessionsRecord.add(str);
		}
		return sessionsRecord;
	}

	public ArrayList<ArrayList<String>> getTableReview() {
		// BasicDBObject query = new BasicDBObject();
		QueryBuilder qb = new QueryBuilder();
		qb.put("student_id").is(Main.user_id).put("takenStatus").is(true);
		DBCursor cursor = Main.mongoHQ.record.find(qb.get());
		ArrayList<ArrayList<String>> reviewRecords = new ArrayList<ArrayList<String>>();
		while (cursor.hasNext()) {
			ArrayList<String> temp = new ArrayList<String>();
			DBObject obj = cursor.next();
			// ID
			temp.add(((ObjectId) obj.get("_id")).toString());

			// Course
			QueryBuilder qbQuery = new QueryBuilder();
			qbQuery.put("_id").is(obj.get("course_id"));
			DBObject tObj = Main.mongoHQ.course.findOne(qbQuery.get());
			temp.add((String) tObj.get("name"));

			// Session
			qbQuery = new QueryBuilder();
			qbQuery.put("_id").is(obj.get("session_id"));
			tObj = Main.mongoHQ.session.findOne(qbQuery.get());
			Date startDate = (Date) tObj.get("start");
			Date endDate = (Date) tObj.get("end");
			SimpleDateFormat startFormat = new SimpleDateFormat(
					"dd.MM.yyyy E kk:mm");
			SimpleDateFormat endFormat = new SimpleDateFormat("'-'kk:mm");
			String str = startFormat.format(startDate)
					+ endFormat.format(endDate);
			temp.add(str);

			temp.add((String) obj.get("grade"));
			temp.add((String) obj.get("remark"));
			reviewRecords.add(temp);
		}
		return reviewRecords;

	}

	public void bookNewSession(int selectedCourseIndex, int selectedSessionIndex) {
		if (selectedSessionIndex == -1)
			return;
		ObjectId courseId = courseIdRecord.get(selectedCourseIndex);
		ObjectId sessionId = sessionIdRecord.get(selectedSessionIndex);
		// Student enrolledNotTested
		// Record
		DBObject listItem = new BasicDBObject("enrolledNotTested", courseId);
		DBObject findQuery = new BasicDBObject("_id", Main.user_id);
		DBObject updateQuery = new BasicDBObject("$pull", new BasicDBObject(
				"enrolledNotTested", courseId));

		Main.mongoHQ.student.update(findQuery, updateQuery);

		BasicDBObjectBuilder document = new BasicDBObjectBuilder();
		document.add("course_id", courseId).add("session_id", sessionId)
				.add("grade", "").add("remark", "")
				.add("student_id", Main.user_id).add("takenStatus", false);
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

	public void makeRequestOfABooking(ObjectId object_id) {
		if (object_id == null) {
			return;
		}
		Main.makeARequestController = new entity.MakeARequestController(object_id);
		Main.makeARequestUI = new MakeARequestUI(Main.makeARequestController);
		Main.desktopController.addComponent(Main.makeARequestUI);
		Main.makeARequestUI.setVisible(true);
	}

	public void checkDetailsOf(ObjectId object_id) {
		if (object_id == null) {
			return;
		}
		Main.checkDetailsController = new entity.CheckDetailsController(
				object_id);
		Main.checkDetailsUI = new entity.CheckDetailsUI(
				Main.checkDetailsController);
		Main.desktopController.addComponent(Main.checkDetailsUI);
		Main.checkDetailsUI.setVisible(true);
	}

	public void updateInfoTextPand(ExamTab examTab, JTextPane txtpnInformation) {
		String newInfo = "";
		getMostRecentSession(examTab);
		DBObject user = Main.mongoHQ.student.findOne(new BasicDBObject("_id", (ObjectId)Main.user_id));
		System.out.println("user_id: " + Main.user_id);
		System.out.println(user);
		newInfo += "Hello " + user.get("username") + "\n";
		
		DBCursor recordCur = Main.mongoHQ.record.find(new BasicDBObject("student_id", Main.user_id));
		
		DBObject record;
		DBObject course;
		String temp = "";
		int noExam = 0;
		while (recordCur.hasNext()) {
			record = recordCur.next();
			System.out.println("record: " + record);
			course = Main.mongoHQ.course.findOne(new BasicDBObject("sessions", record.get("session_id")));
			System.out.println("course: " + course);
			Date start = (Date) Main.mongoHQ.session.findOne(new BasicDBObject("_id", record.get("session_id"))).get("start");
			System.out.println("start: " + start);
			System.out.println("new Date: " + new Date());
			if (start.before(new Date())) {
				continue;
			}
			noExam++;
			System.out.println("noExam: " + noExam);
			temp += course.get("code") + " " + course.get("name") + ": " + start + "\n";
		}
		
		newInfo += "You have " + noExam + " exams left.\n\n" + temp;
		
		txtpnInformation.setText(newInfo);
	}
	
	public void updateMsgTextPand(JTextPane txtpnRecentMessages){
		String newMsg ="";
		newMsg = Messager.pollMsg(Main.user_id, "Student");
		txtpnRecentMessages.setText(newMsg);
	}

	public ObjectId getOneProctor(ObjectId sessionId) {
		// XXX Auto-generated method stub
		DBCursor cursor = Main.mongoHQ.record.find(new BasicDBObject("session_id", sessionId).append("user_id", new BasicDBObject("$exists", true)));
		ArrayList<ObjectId> pList = new ArrayList<ObjectId>();
		while (cursor.hasNext()) {
			pList.add((ObjectId) cursor.next().get("user_id"));
		}
		
		Random rd = new Random();
		
		return pList.get(rd.nextInt(pList.size()));
	}
	
	public int getTimeToMostRecentSession(ExamTab examTab) {
		if (examTab.mostRecentSession == null)
			return -1;
		
		Date mostRecentStartTime = (Date) examTab.mostRecentSession.get("start");
		
		int secondToGo = (int)((mostRecentStartTime.getTime() - new Date().getTime()) / 1000);
		System.out.println("\nstart: " + mostRecentStartTime.toString());
		System.out.println("now: " + new Date().toString());
		System.out.println("second to go: " + secondToGo);
		return secondToGo;
	}

	
	class UpdateTimeToGoBeforeExam implements ActionListener {
		public int secondToGo;
		ExamTab examTab;
		
		public UpdateTimeToGoBeforeExam(ExamTab examTab, int secondToGo) {
			this.secondToGo = secondToGo;
			this.examTab = examTab;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// XXX Auto-generated method stub
			if (secondToGo > 0) {
				secondToGo--;
				examTab.lblTimeToGo.setText(secondToString(secondToGo));
			} else if (secondToGo == 0) {
				getMostRecentSession(examTab);
								
				if (examTab.mostRecentSession == null) {
					System.out.println("session disappeared!");
				}
				
				// start GrabberShow
				Main.studentHomeUI.grabberShow = new GrabberShow(6001, Main.user_id.toString(), examTab.lblVideoBox);
				
				// start a countdown timer during the exam
				long duration = ((Date)examTab.mostRecentSession.get("end")).getTime() - ((Date)examTab.mostRecentSession.get("start")).getTime();
				System.out.println("duration: " + secondToString((int)(duration / 1000)));
				
				Main.studentHomeUI.timer.stop();
				Main.studentHomeUI.updateTimeToGoDuringExam = new UpdateTimeToGoDuringExam(examTab, (int)duration / 1000);
				Main.studentHomeUI.timer = new javax.swing.Timer((int)duration, Main.studentHomeUI.updateTimeToGoDuringExam);
				Main.studentHomeUI.timer.setInitialDelay(0);
				Main.studentHomeUI.timer.setDelay(1000);
				Main.studentHomeUI.timer.start();
				
				examTab.showWhenStart();
			} else {
				examTab.lblTimeToGo.setText("here is UpdateTimeToGoBeforeExam. something goes wrong");
			}
		}
	}
	class UpdateTimeToGoDuringExam implements ActionListener {
		public int secondToGo;
		ExamTab examTab;
		
		public UpdateTimeToGoDuringExam(ExamTab examTab, int secondToGo) {
			this.secondToGo = secondToGo;
			System.out.println("new UpdateTimeToGoDuringExam");
			this.examTab = examTab;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// XXX Auto-generated method stub
			if (secondToGo > 0) {
				secondToGo--;
				System.out.println("timeToGo--; left " + secondToString(secondToGo));
				examTab.lblTimeToGo.setText(secondToString(secondToGo));
				
				if (secondToGo % 5 == 0) {
					//
					// fetch message once
					//
					
					// String allTogether = "all student id and message together. " + secondToGo;
					System.out.println(Main.user_id + " fetching message");
					String allTogether = Messager.pollMsg(new ObjectId("532541929862bcab1a0000fd"), "Proctor");
					//
					// compress all message together
					//
					examTab.lblMsgReceived.setText("<html>" + allTogether + "</html>");
				}
			} else if (secondToGo == 0) {
				Main.studentHomeUI.timer.stop();
				examTab.lblTimeToGo.setText("Exam ended.");
				examTab.btnSign.setVisible(true);
				examTab.hideWhenEnd();
				Main.studentHomeUI.grabberShow.shouldEnd = true;
				updateInfoTextPand(examTab, Main.studentHomeUI.txtpnInformation);
			} else {
				examTab.lblTimeToGo.setText("here is UpdateTimeToGoDuringExam. something goes wrong");
			}
		}
	}

	public String secondToString (int second) {
		int minute = second / 60;
		second = second % 60;
		
		int hour = minute / 60;
		minute = minute % 60;
		
		int day = hour / 24;
		hour = hour % 24;
		
		return day + " days " + hour + " hours " + minute + " minutes " + second + " seconds ";
	}

	public static void getMostRecentSession(ExamTab examTab) {
//		DBCursor sessionsCursor = Main.mongoHQ.record.find(new BasicDBObject("user_id", Main.user_id), new BasicDBObject("session_id", 1));
		if (Main.mongoHQ == null) {
			System.out.println("server not found");
			return ;
		}
		DBCursor sessionsCursor = Main.mongoHQ.record.find(new BasicDBObject("student_id", new ObjectId("531ec0d07a0016ee1c000508")), new BasicDBObject("session_id", 1));
				
		ArrayList<ObjectId> sessions = new ArrayList<ObjectId>();
		
		if (sessionsCursor == null) {
			System.out.println("sessionsCursor == null");
			return ;
		}
		
		while (sessionsCursor.hasNext()) {
			sessions.add((ObjectId)sessionsCursor.next().get("session_id"));
		}
				
		ObjectId mostRecentSession = null;
		Date mostRecentStartTime = null;
		try {
			mostRecentStartTime = new SimpleDateFormat("dd-M-yyyy hh:mm:ss").parse("01-01-2015 00:00:00");
		} catch (ParseException e) {
			// XXX Auto-generated catch block
			e.printStackTrace();
			System.out.println("new SimpleDateFormat failed");
			examTab.mostRecentSession = null;
			return ;
		}
		ObjectId tempSession = null;
		Date tempStartTime = null;
		Iterator<ObjectId> iter = sessions.iterator();
		while (iter.hasNext()) {
			tempSession = iter.next();
			
			DBObject tempResult = Main.mongoHQ.session.findOne(new BasicDBObject("_id", tempSession));
			tempStartTime = (Date) tempResult.get("start");
			if ((tempStartTime.before(mostRecentStartTime))) {
				mostRecentSession = tempSession;
				mostRecentStartTime = tempStartTime;
			}
		}
		
		System.out.println("mostRecentSession: " + mostRecentSession);
		examTab.mostRecentSession = Main.mongoHQ.session.findOne(new BasicDBObject("_id", mostRecentSession));
		return ;
	}
}
