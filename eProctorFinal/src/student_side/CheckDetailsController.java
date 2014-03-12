package student_side;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.bson.types.ObjectId;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.QueryBuilder;

import entity.Main;

public class CheckDetailsController {

	private ObjectId record_id;
	private DBObject obj, tObj;

	public CheckDetailsController() {
		//Main.checkDetailsUI.setVisible(true);
		this.record_id = new ObjectId("531dc95e2ad939b68700001f");
		QueryBuilder qb = new QueryBuilder();
		qb.put("_id").is(record_id);
		DBCursor cursor = Main.mongoHQ.record.find(qb.get());
		if (cursor.hasNext()) {
			obj = cursor.next();
		}
		if (obj == null) {
			System.out.println("hehe");
		}
	}
	
	public void deleteRecord() {
		// TODO Auto-generated method stub
		Main.checkDetailsUI.setVisible(false);
	}

	public void cancel() {
		Main.checkDetailsUI.setVisible(false);
	}

	public String getCourseCode() {
		QueryBuilder qbQuery = new QueryBuilder();
		qbQuery = new QueryBuilder();
		qbQuery.put("_id").is(obj.get("course_id"));
		tObj = Main.mongoHQ.course.findOne(qbQuery.get());
		return (String) tObj.get("code");
	}

	public String getCourseName() {
		QueryBuilder qbQuery = new QueryBuilder();
		qbQuery = new QueryBuilder();
		qbQuery.put("_id").is(obj.get("course_id"));
		tObj = Main.mongoHQ.course.findOne(qbQuery.get());
		return (String) tObj.get("name");
	}
	
	public String getSessionId() {
		return obj.get("session_id").toString();
	}

	public String getLocation() {
		QueryBuilder qbQuery = new QueryBuilder();
		qbQuery = new QueryBuilder();
		qbQuery.put("_id").is(obj.get("session_id"));
		tObj = Main.mongoHQ.session.findOne(qbQuery.get());
		return (String) tObj.get("name");
	}

	public String getStartTime() {
		QueryBuilder qbQuery = new QueryBuilder();
		qbQuery = new QueryBuilder();
		qbQuery.put("_id").is(obj.get("session_id"));
		tObj = Main.mongoHQ.session.findOne(qbQuery.get());
		Date startDate = new Date(Long.parseLong((String) tObj.get("start")));
		SimpleDateFormat startFormat = new SimpleDateFormat ("dd.MM.yyyy E kk:mm");
		return (String) startFormat.format(startDate);
	}

	public String getEndTime() {
		QueryBuilder qbQuery = new QueryBuilder();
		qbQuery = new QueryBuilder();
		qbQuery.put("_id").is(obj.get("session_id"));
		tObj = Main.mongoHQ.session.findOne(qbQuery.get());
		Date endDate = new Date(Long.parseLong((String) tObj.get("end")));
		SimpleDateFormat endFormat = new SimpleDateFormat ("dd.MM.yyyy E kk:mm");
		return (String) endFormat.format(endDate);
	}

	public String getRecordId() {
		return record_id.toString();
	}

	public String getProctorId() {
		return (String) obj.get("proctor");
	}

	public String getGrade() {
		return (String) obj.get("grade");
	}

	public String getRemark() {
		return (String) obj.get("remark");
	}

}
