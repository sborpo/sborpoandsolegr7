package cs236369.hw5;

import java.sql.SQLException;
import java.util.HashMap;

import cs236369.hw5.ReservationManager.Period;
import cs236369.hw5.db.DbManager;
import cs236369.hw5.users.UserManager;

public class TestingClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	DbManager.DbConnections.getInstance().setDbName("labdb");
		DbManager.DbConnections.getInstance().setUserName("root");
		DbManager.DbConnections.getInstance().setPassword("123456");
	DbManager.DbConnections.getInstance().setUrl("jdbc:mysql://localhost");
//		try {
//			ReservationManager.ReservationTable table = new ReservationManager.ReservationTable(2010, 2,1, 5, "a");
//			String [][] arr= table.getReservationTable();
//			for (String[] strings : arr) {
//				for (String string : strings) {
//					System.out.print(string+"  ");
//				}
//				System.out.println();
//			}
		//UserManager.AddUser("stam1", "abc", "asf", "asf", "safasf", "", "", null, UserType.ADMIN);
//			for (User user : UserManager.getUsers()) {
//				System.out.println(user.getName());
//			}
//		}
//		catch (UserExists e)
//		{
//			System.out.println("user already exists");
//		}
//		catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		try {
//			HashMap<Long, ReservationManager.Period> periods = ReservationManager.searchForSlotsAv(new TimeSlot(2009, 11, 2),new String[] { "abcdefg","stam"}, 8640);
//			searchWS sr = new searchWS();
//			String [] str =sr.search(new String[] { "abcdefg","stam"}, 8640);
//			for (String per :str) {
//				System.out.println(per);
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	String z= "asfasf||aaaaa||bbbbb";
	String[] arr=z.split("\\|\\|");
	for (String string : arr) {
		System.out.println(string);
	}

	}
	

}
