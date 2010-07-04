package cs236369.hw5.users;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.sql.rowset.serial.SerialBlob;

import cs236369.hw5.Administrator;
import cs236369.hw5.Researcher;
import cs236369.hw5.User;
import cs236369.hw5.User.UserType;
import cs236369.hw5.db.DbManager;
import cs236369.hw5.db.DbManager.DbConnections.SqlError;

public class UserManager {
	public static class UserExists extends Exception{}
	public static class UserNotExists extends Exception{}
	public static class TryDeleteAdmin extends Exception{}
	public static String Usern="username";
	public static String Password="password";
	public static String PassConfirm="c_password";
	public static String Name="name";
	public static String Group="group";
	public static String Address="address";
	public static String PhoneNumber="phonenumber";
	public static String Permission="permission";
	public static String Photo="userpicture";
	public static String UserTypen="usertpe";
	public static String Captcha="jcaptcha";
	public static int FileSizeInBytes=300000;
	public static String NotSpecified="Not Specified";
	
	
	public static byte[] getPhoto(String username) throws SQLException
	{
		 Connection conn=DbManager.DbConnections.getInstance().getConnection();
		 PreparedStatement statementUsers= User.getUserPicture(conn,username);
		 ResultSet set= statementUsers.executeQuery();
		 if (set.next())
		 {
			Blob b= set.getBlob("photo");
			if (set.wasNull())
			{
				return null;
			}
			int length  = (int) b.length();
			byte[] array = new byte[length];
			array = b.getBytes(1,length);
			return array;
		 }
		return null;
	}
	
	public static void updateUser(String login,String pass,String group,String permission,String name,String phone,String address,Blob stream,UserType type) throws SQLException, UserNotExists
	{
		User user= null;
		if (type.equals(UserType.ADMIN))
		{
			user = new Administrator(login,pass,name,permission,group,phone,address,stream);
		}
		else
		{
			user = new Researcher(login,pass,name,permission,group,phone,address,stream);
		}
		Connection conn=null;
		ResultSet set=null;
		try{
		conn=DbManager.DbConnections.getInstance().getConnection();
		//TODO: verify this is the needed isolation level.
		//When we read data , we don't aquire locks , so we don't care if someone has deleted
		//the row or change it. Becuase update is done only on existing rows. (the admin could have been deleted the
		//user after he changed his details.
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		conn.setAutoCommit(false);
		 PreparedStatement userExists= User.getUserDetails(conn, login);
		 set= userExists.executeQuery();
		 if (!set.next())
		 {
			throw new UserNotExists();
		 }
		 PreparedStatement statementUsers= user.setUpdateUserDet(conn);
		 PreparedStatement statementRoles = user.setUpdateRole(conn);
		 statementUsers.execute();
		 statementRoles.execute();
		 conn.commit();
		}
		catch (UserNotExists ex)
		{
			conn.rollback();
			throw ex;
		}
		catch (SQLException ex)
		{
			conn.rollback();
			throw ex;
		}
		finally{
			if (set!=null)
			{
				set.close();
			}
			if (conn!=null)
			{
				conn.close();
			}
		}
	}
	
	public static void removeUser(String login) throws UserNotExists, SQLException, TryDeleteAdmin
	{
		Connection conn=null;
		 ResultSet set= null;
		try{
		conn=DbManager.DbConnections.getInstance().getConnection();
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		conn.setAutoCommit(false);
		PreparedStatement userExists= User.getUserDetails(conn, login);
		set= userExists.executeQuery();
		if (!set.next())
		{
				throw new UserNotExists();
		}
		else
		{
			User user=UserManager.setUserFromRow(set);
			if (user.getRole().equals(UserType.ADMIN))
			{
				throw new TryDeleteAdmin();
			}
		}

		User user= new Researcher(login);
		 PreparedStatement statementUsers= user.setDeleteUser(conn);
		 PreparedStatement statementRoles = user.setDeleteUserRole(conn);
		 statementUsers.execute();
		 statementRoles.execute();
		 conn.commit();
		}
		catch (SQLException ex)
		{
			conn.rollback();
			throw  ex;
		}
		finally{
			if (set!=null)
			{
				set.close();
			}
			if (conn!=null)
			{
				conn.close();
			}
		}
		 
	}
	public static void AddUser(String login,String pass,String group,String permission,String name,String phone,String address,Blob stream,UserType type) throws SQLException, UserExists
	{
		User user= null;
		if (type.equals(UserType.ADMIN))
		{
			user = new Administrator(login,pass,name,permission,group,phone,address,stream);
		}
		else
		{
			user = new Researcher(login,pass,name,permission,group,phone,address,stream);
		}
		Connection conn=null;
		 
		try{
		conn=DbManager.DbConnections.getInstance().getConnection();
		conn.setAutoCommit(false);
		 PreparedStatement statementUsers= user.setInsertUser(conn);
		 PreparedStatement statementRoles = user.setInsertRole(conn);
		 statementUsers.execute();
		 statementRoles.execute();
		 conn.commit();
		}
		catch (SQLException ex)
		{
			conn.rollback();
			if (SqlError.PRIMARY_KEY_ERROR.errorNumber()==ex.getErrorCode())
			{
				throw new UserExists();
			}
			throw ex;
		}
		finally{
			if (conn!=null)
			{
				conn.close();
			}
		}
	}
	
	private static User setUserFromRow(ResultSet set) throws SQLException
	{
		User user=null;
		 Blob b=new SerialBlob(new byte[0]); 
		 set.getBlob("photo");
		 if (set.wasNull())
		 {
			b=null;
		 }
		
		 if (set.getString("rolename").equals(UserType.REASEARCHER.toString()))
		 {
			
			 
			 
			 user = new Researcher(set.getString("login"), set.getString("password"), set.getString("name"), set.getString("permission"), set.getString("usergroup"), set.getString("phone"), set.getString("address"),b);
		 }
		 else
		 {
			 user = new Administrator(set.getString("login"), set.getString("password"), set.getString("name"), set.getString("permission"), set.getString("usergroup"), set.getString("phone"), set.getString("address"),b);
		 }
		 return user;
	}
	
	public static User getUserDetails(String username) throws SQLException
	{
		Connection conn=null;ResultSet set=null;
		try{
		conn=DbManager.DbConnections.getInstance().getConnection();
		 PreparedStatement statementUsers= User.getUserDetails(conn, username);
		 set= statementUsers.executeQuery();
		 User user=null;
		 if (set.next())
		 {
			user=setUserFromRow(set);
		 }
		 return user;
		}
		finally{
			if (set!=null)
			{
				set.close();
			}
			if (conn!=null)
			{
				conn.close();
			}
		}
	}
	
	public static LinkedList<User> getUsers() throws SQLException{ 
		Connection conn=null;ResultSet set=null;
		LinkedList<User> usersList = new LinkedList<User>();
		try{
		conn=DbManager.DbConnections.getInstance().getConnection();
		 PreparedStatement statementUsers= User.getAllUsersNoPicture(conn);
		 set= statementUsers.executeQuery();
		 while (set.next())
		 {
			Blob b= set.getBlob("photo");
			if (set.wasNull())
			{
				System.out.println("NULL");
			}
			else
			{
				System.out.println(b.length());
			}
			 User user=null;
			 user=setUserFromRow(set);
			 usersList.add(user);
		 }
		 return usersList;
		}
		finally{
			if (set!=null)
			{
				set.close();
			}
			if (conn!=null)
			{
				conn.close();
			}
		
		}
	}
}
