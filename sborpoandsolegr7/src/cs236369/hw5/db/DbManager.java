package cs236369.hw5.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DbManager {

	
	
	public static void  constructTables() throws SQLException
	{
		Connection conn=getConnection();
		Statement statment=null;
		try{
		String Db= "CREATE SCHEMA IF NOT EXISTS `labdb` ;";	
			
		String users="CREATE  TABLE IF NOT EXISTS `db`.`users` " +
				"(`username` VARCHAR(100) NOT NULL ," +
				"`password` VARCHAR(100) NOT NULL ," +
				"PRIMARY KEY (`username`) );";
		 statment= conn.createStatement(); 
		 statment.executeUpdate(users); 
		 
		 String games="CREATE  TABLE IF NOT EXISTS `db`.`games` " +
		 		"(`gameid` VARCHAR(250) NOT NULL " +
		 		",`user1` VARCHAR(45) NOT NULL " +
		 		",`user2` VARCHAR(45) NOT NULL " +
		 		",`user1rep` VARCHAR(45) NULL " +
		 		",`user2rep` VARCHAR(45) NULL " +
		 		",PRIMARY KEY (`gameid`) );";
		 statment.executeUpdate(games); 
		}
		finally
		{
			if (conn!=null){conn.close();}
		}
		
	}
	
	public static void makeReport(String gameId,String username,String report) throws SQLException
	{
		Connection conn=null;
		try{
		conn = getConnection();
		conn.setAutoCommit(false);
		String []query= {"UPDATE games SET user1rep=? WHERE gameid=? AND user1=?;","UPDATE games SET user2rep=? WHERE gameid=? AND user2=?;"};
		synchronized (gameslock) {
		for (int i=0; i<query.length; i++)
		{
		PreparedStatement prepareStatement = conn.prepareStatement(query[i]);
		prepareStatement.setString(1,report);
		prepareStatement.setString(2,gameId);
		prepareStatement.setString(3, username);
		
				prepareStatement.executeUpdate();
		}
		conn.commit();
		conn.setAutoCommit(true);
		}
		}
		catch (SQLException ex)
		{
			conn.rollback();
		}
		finally
		{
			if (conn!=null){conn.close();}
		}	
		
	}
	
	
}
