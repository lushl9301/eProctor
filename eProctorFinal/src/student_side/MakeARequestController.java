package entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bson.types.ObjectId;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

public class MakeARequestController {

	private ObjectId record_id;
	private DBObject objRecord, objSession, objCourse;

	public MakeARequestController(ObjectId record_id) {
		this.record_id = record_id;
		QueryBuilder qb = new QueryBuilder();
		qb.put("_id").is(record_id);
		objRecord = Main.mongoHQ.record.findOne(qb.get());
		qb.put("_id").is(objRecord.get("course_id"));
		objCourse = Main.mongoHQ.course.findOne(qb.get());
		qb.put("_id").is(objRecord.get("session_id"));
		objSession = Main.mongoHQ.session.findOne(qb.get());
	}
	
	public void deleteRecord() {
		// TODO Auto-generated method stub
//		Main.makeARequestUI.setVisible(false);
	}

/*	public void cancel() {
		Main.makeARequestUI.setVisible(false);
	}*/

	public String getCourseCode() {
		return (String) objCourse.get("code");
	}

	public String getCourseName() {
		return (String) objCourse.get("name");
	}
	
	public String getSessionId() {
		return objRecord.get("session_id").toString();
	}

	public String getLocation() {
		return (String) objSession.get("name");
	}

	public String getStartTime() {
		Date startDate = (Date) objSession.get("start");
		SimpleDateFormat startFormat = new SimpleDateFormat ("dd.MM.yyyy E kk:mm");
		return (String) startFormat.format(startDate);
	}

	public String getEndTime() {
		Date endDate = (Date) objSession.get("end");
		SimpleDateFormat endFormat = new SimpleDateFormat ("dd.MM.yyyy E kk:mm");
		return (String) endFormat.format(endDate);
	}

	public String getRecordId() {
		return record_id.toString();
	}

	public String getProctorId() {
		return (String) objRecord.get("proctor");
	}

}
