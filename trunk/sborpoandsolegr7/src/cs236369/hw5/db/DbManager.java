package cs236369.hw5.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DbManager {

	
	public static class DbConnections
	{
		public String getDbName() {
			return dbName;
		}

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
		
		public Connection getNoDbConnection() throws SQLException
		{
			return DriverManager.getConnection (url, userName, password); 
		}
	}
	
	private static void createDb() throws SQLException {
		Connection conn=DbConnections.getInstance().getNoDbConnection();
		Statement statment=null;
		try{
			statment= conn.createStatement(); 
			String db= "CREATE SCHEMA IF NOT EXISTS `labdb` ;";	
			statment.executeUpdate(db); 
		}
		finally
		{
			if (conn!=null){conn.close();}
		}
	}
	
	public static void  constructTables() throws SQLException
	{
		createDb();
		Connection conn=DbConnections.getInstance().getConnection();
		Statement statment=null;
		conn.setAutoCommit(false);
		try{
		statment= conn.createStatement(); 
		String db= "CREATE SCHEMA IF NOT EXISTS `"+DbConnections.getInstance().getDbName()+"` ;";	
		statment.executeUpdate(db); 
		String instruments="CREATE TABLE IF NOT EXISTS `instruments` (`id` bigint(20) NOT NULL,`type` varchar(30) NOT NULL,`permission` int(11) NOT NULL,`timeslot` int(11) NOT NULL,`description` text NOT NULL,PRIMARY KEY (`id`),KEY `Permission` (`permission`),KEY `Type` (`type`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		statment.executeUpdate(instruments); 
		String users= "CREATE TABLE IF NOT EXISTS `users` ( `login` varchar(30) NOT NULL, `password` varchar(32) NOT NULL, `name` varchar(50) NOT NULL, `permission` text,`usergroup` varchar(50) NOT NULL, `phone` varchar(15) DEFAULT NULL,`address` varchar(50) DEFAULT NULL,`photo` blob,`email` varchar(100) NOT NULL, PRIMARY KEY (`login`), KEY `name` (`name`),KEY `group` (`usergroup`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		statment.executeUpdate(users); 
		String reservations= "CREATE TABLE `reservations` IF NOT EXISTS (`instid` bigint(20) unsigned NOT NULL,`year` int(11) NOT NULL,`month` int(11) NOT NULL,`day` int(11) NOT NULL,`slotbegin` int(11) NOT NULL,`slotend` int(11) NOT NULL,PRIMARY KEY (`instid`,`year`,`month`,`day`,`slotbegin`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		 statment.executeUpdate(reservations); 
		String userRoles="CREATE TABLE `user_roles` IF NOT EXISTS (`login` varchar(30) NOT NULL, `rolename` varchar(45) NOT NULL, PRIMARY KEY (`login`,`rolename`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
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
