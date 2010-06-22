package cs236369.hw5.users;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import cs236369.hw5.Administrator;
import cs236369.hw5.Researcher;
import cs236369.hw5.User;
import cs236369.hw5.User.UserType;
import cs236369.hw5.db.DbManager;
import cs236369.hw5.db.DbManager.DbConnections.SqlError;

public class UserManager {
	public static class UserExists extends Exception{}
	public static String Usern="username";
	public static String Password="password";
	public static String PassConfirm="c_password";
	public static String Name="name";
	public static String Group="group";
	public static String Address="address";
	public static String PhoneNumber="phonenumber";
	public static String Photo="userpicture";
	public static String UserTypen="usertpe";
	public static String Captcha="jcaptcha";
	
	
	
	public static void AddUser(String login,String pass,String group,String permission,String name,String phone,String address,InputStream stream,UserType type) throws SQLException, UserExists
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
		 PreparedStatement statementRoles = user.setUpdateRole(conn);
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
			 if (set.getString("rolename").equals(UserType.ADMIN.toString()))
			 {
				 
				 user = new Researcher(set.getString("login"), set.getString("password"), set.getString("name"), set.getString("permission"), set.getString("usergroup"), set.getString("phone"), set.getString("address"),null);
			 }
			 else
			 {

				 user = new Administrator(set.getString("login"), set.getString("password"), set.getString("name"), set.getString("permission"), set.getString("usergroup"), set.getString("phone"), set.getString("address"),null);
			 }
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
