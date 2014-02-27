package student_side;

import java.util.ArrayList;
import java.util.Arrays;

import entity.Main;

public class StudentReviewController {
	public static void fetchData() {
		ArrayList<String> query = new ArrayList<String>();
		query.addAll(Arrays.asList(new String[] {"recordID"}));
		ArrayList<String> result = Main.server.fetch("User", "UID"/*currentUser.ID*/, query);
		//write recordID column
		int nResult = result.size();
		for (int i = 0; i < nResult; i++) {
			Main.studentHomeUI.getTableReview().setValueAt(result.get(i), i, 0);
		}
		query.addAll(Arrays.asList(new String[] {"courseID", "sessionID", "grade", "remark"}));
		for (int i = 0; i < nResult; i++) {
			result = Main.server.fetch("record", (String) Main.studentHomeUI.getTableReview().getValueAt(i, 0), query);
			Main.studentHomeUI.getTableReview().setValueAt(result.get(0), i, 1);
			Main.studentHomeUI.getTableReview().setValueAt(result.get(1), i, 2);
			Main.studentHomeUI.getTableReview().setValueAt(result.get(2), i, 3);
			Main.studentHomeUI.getTableReview().setValueAt(result.get(3), i, 4);
		}
		query.addAll(Arrays.asList(new String[] {"name"}));
		for (int i = 0; i < nResult; i++) {
			result = Main.server.fetch("course", (String) Main.studentHomeUI.getTableReview().getValueAt(i, 1), query);
			Main.studentHomeUI.getTableReview().setValueAt(result.get(0), i, 1);
		}
		query.addAll(Arrays.asList(new String[] {"string"}));
		for (int i = 0; i < nResult; i++) {
			result = Main.server.fetch("session", (String) Main.studentHomeUI.getTableReview().getValueAt(i, 2), query);
			Main.studentHomeUI.getTableReview().setValueAt(result.get(0), i, 2);
		}
		//result = Main.server.fetch("record", , query);
	}
}
// 	record ID, course, session, grade, remark