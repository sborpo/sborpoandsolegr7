package cs236369.hw5.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


public class DbManager {

	
	public static class DbConnections
	{
		private String dbName ; 
		private String userName;
		private String password;
		private String url;
		private static DbConnections connection;
		
		
		public void setDbName(String dbName) {
			this.dbName = dbName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public static DbConnections getInstance()
		{
			if (connection==null)
			{
				connection = new DbConnections();
			}
			return connection;
		}
		public Connection getConnection() throws SQLException
		{
				return DriverManager.getConnection (url+"/"+dbName, userName, password); 
		}
	}
	
	
	public static void  constructTables() throws SQLException
	{
		Connection conn=DbConnections.getInstance().getConnection();
		Statement statment=null;
		conn.setAutoCommit(false);
		try{
		statment= conn.createStatement(); 
		String db= "CREATE SCHEMA IF NOT EXISTS `labdb` ;";	
		statment.executeUpdate(db); 
		String instruments="CREATE  TABLE IF NOT EXISTS `labdb`.`instruments` " +
				"(`id` BIGINT NOT NULL ," +
				"`type` VARCHAR(30) NOT NULL ," +
				"`permission` INT NOT NULL ," +
				"`timeslot` INT NOT NULL ," +
				"`description` TEXT NOT NULL ," +
				"PRIMARY KEY (`id`) )" +
				"ENGINE = InnoDB;";
		statment.executeUpdate(instruments); 
		String users= "CREATE  TABLE IF NOT EXISTS `labdb`.`users` " +
				"(`login` varchar(30) NOT NULL," +
				"`password` varchar(32) NOT NULL," +
				"`name` varchar(50) NOT NULL," +
				"`permission` text," +
				"`group` varchar(50) NOT NULL," +
				"`phone` varchar(15) DEFAULT NULL," +
				"`address` varchar(50) DEFAULT NULL," +
				"`photo` blob,`admin` char(1) NOT NULL," +
				"PRIMARY KEY (`login`)) ENGINE=InnoDB" +
				" DEFAULT CHARSET=utf8;";
		 statment.executeUpdate(users); 
		String reservations= "CREATE  TABLE IF NOT EXISTS `labdb`.`reservations`" +
				" (`instid` BIGINT UNSIGNED NOT NULL ," +
				"`year` INT NOT NULL ,`month` INT NOT NULL ," +
				"`day` INT NOT NULL ,`slotbegin` INT NOT NULL ," +
				"`numofslots` INT NOT NULL ," +
				"PRIMARY KEY (`instid`, `year`, `month`, `day`, `slotbegin`) );";
		 statment.executeUpdate(reservations); 
		conn.commit();
		conn.setAutoCommit(true);
		}
		catch (SQLException ex)
		{
			conn.rollback();
			throw ex;
		}
		finally
		{
			if (conn!=null){conn.close();}
		}
		
	}
	

	
	
}
