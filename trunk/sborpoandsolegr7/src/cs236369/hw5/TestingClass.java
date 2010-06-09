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
			ReservationManager.ReservationTable table = new ReservationManager.ReservationTable(2010, 2,1, 5, "a");
			String [][] arr= table.getReservationTable();
			for (String[] strings : arr) {
				for (String string : strings) {
					System.out.print(string+"  ");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
