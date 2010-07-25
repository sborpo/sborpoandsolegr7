package cs236369.hw5.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import cs236369.hw5.User;
import cs236369.hw5.Utils;


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
			String db="CREATE USER 'sborpo'@'%' IDENTIFIED BY 'sborpo';" +
					" GRANT USAGE ON * . * TO 'sborpo'@'%' IDENTIFIED BY 'sborpo' WITH MAX_QUERIES_PER_HOUR 0 MAX_CONNECTIONS_PER_HOUR 0 MAX_UPDATES_PER_HOUR 0 MAX_USER_CONNECTIONS 0 ; " +
					"CREATE DATABASE IF NOT EXISTS `sborpoDB` ;" +
					" GRANT ALL PRIVILEGES ON `sborpoDB` . * TO 'sborpo'@'%';";
			statment.executeUpdate(db); 
		}
		finally
		{
			if (conn!=null){conn.close();}
		}
	}
	
	public static void  constructTables() throws SQLException
	{
		//createDb();
		ResultSet set=null;
		Connection conn=DbConnections.getInstance().getConnection();
		Statement statment=null;
		conn.setAutoCommit(false);
		try{
		statment= conn.createStatement(); 
		String instruments="CREATE TABLE IF NOT EXISTS `instruments` ( `id` bigint(20) NOT NULL, `type` varchar(30) NOT NULL, `permission` int(11) NOT NULL, `timeslot` int(11) NOT NULL, `description` text NOT NULL,`location` varchar(45) DEFAULT NULL, PRIMARY KEY (`id`), KEY `Permission` (`permission`), KEY `Type` (`type`),KEY `loc` (`location`), FULLTEXT KEY `index4` (`type`,`description`)) ENGINE=MyISAM DEFAULT CHARSET=utf8;";
		statment.executeUpdate(instruments); 
		String users= "CREATE TABLE IF NOT EXISTS `users` ( `login` varchar(30) NOT NULL, `password` varchar(32) NOT NULL, `name` varchar(50) NOT NULL,`permission` text, `usergroup` varchar(50) NOT NULL, `phone` varchar(15) DEFAULT NULL, `address` varchar(50) DEFAULT NULL, `photo` longblob, `email` varchar(100) NOT NULL, PRIMARY KEY (`login`), KEY `name` (`name`),KEY `group` (`usergroup`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		statment.executeUpdate(users); 
		String reservations= "CREATE TABLE IF NOT EXISTS `reservations` ( `instid` bigint(20) unsigned NOT NULL, `year` int(11) NOT NULL, `month` int(11) NOT NULL, `day` int(11) NOT NULL, `slotbegin` int(11) NOT NULL, `slotend` int(11) NOT NULL,`userId` varchar(30) NOT NULL , PRIMARY KEY (`instid`,`year`,`month`,`day`,`slotbegin`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		 statment.executeUpdate(reservations); 
		String userRoles="CREATE TABLE IF NOT EXISTS `user_roles` (`login` varchar(30) NOT NULL, `rolename` varchar(45) NOT NULL, PRIMARY KEY (`login`,`rolename`)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		statment.executeUpdate(userRoles); 
		PreparedStatement statement=User.getUserDetails(conn, "root");
		set=statement.executeQuery();
		if (!set.next())
		{
			String query = "INSERT INTO users (`login`,`password`,`name`,`permission`,`usergroup`,`phone`,`address`,`photo`,`email`) VALUES('root','123456','root',NULL,'root',NULL,NULL,NULL,'"+Utils.supportMail+"')";
			statement.execute(query);
			query = "INSERT INTO user_roles VALUES('root','admin');";
			statement.execute(query);
		}
		conn.commit();
		
		}
		catch (SQLException ex)
		{
			conn.rollback();
			throw ex;
		}
		finally
		{
			if (set!=null){set.close();}
			if (conn!=null){conn.close();}
		}
		
	}
	

	
	
}
