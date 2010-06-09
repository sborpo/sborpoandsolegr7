package cs236369.hw5;

import java.sql.SQLException;

import cs236369.hw5.db.DbManager;

public class TestingClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DbManager.DbConnections.getInstance().setDbName("labdb");
		DbManager.DbConnections.getInstance().setUserName("root");
		DbManager.DbConnections.getInstance().setPassword("123456");
		DbManager.DbConnections.getInstance().setUrl("jdbc:mysql://localhost");
		try {
			ReservationManager.Search(new TimeSlot(2010, 1), 5, "a");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
