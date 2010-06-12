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
		
		public static enum SqlError {
			PRIMARY_KEY_ERROR(1062); 
			
			private int errorNum;
			SqlError(int value)
			{errorNum=value;}
			
			public int errorNumber()
			{
				return errorNum;
			}
		}
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
		String instruments=" CREATE TABLE `instruments` " +
				"(`id` bigint(20) NOT NULL, " +
				"`type` varchar(30) NOT NULL," +
				"`permission` int(11) NOT NULL," +
				"`timeslot` int(11) NOT NULL," +
				"`description` text NOT NULL," +
				"PRIMARY KEY (`id`)," +
				"KEY `Permission` (`permission`)," +
				"KEY `Type` (`type`)) " +
				"ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		statment.executeUpdate(instruments); 
		String users= "CREATE TABLE `users` (`login` varchar(30) NOT NULL,`password` varchar(32) NOT NULL,`name` varchar(50) NOT NULL,`permission` text,`usergroup` varchar(50) NOT NULL,`phone` varchar(15) DEFAULT NULL,`address` varchar(50) DEFAULT NULL,`photo` blob,PRIMARY KEY (`login`),KEY `group` (`group`),KEY `name` (`name`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		statment.executeUpdate(users); 
		String reservations= "CREATE TABLE `reservations` (`instid` bigint(20) unsigned NOT NULL,`year` int(11) NOT NULL,`month` int(11) NOT NULL,`day` int(11) NOT NULL,`slotbegin` int(11) NOT NULL,`slotend` int(11) NOT NULL,PRIMARY KEY (`instid`,`year`,`month`,`day`,`slotbegin`),UNIQUE KEY `Instrument` (`instid`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		 statment.executeUpdate(reservations); 
		String userRoles="CREATE TABLE `user_roles` (`login` varchar(30) NOT NULL,`rolename` varchar(45) NOT NULL, PRIMARY KEY (`login`,`rolename`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		statment.executeUpdate(userRoles); 
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
