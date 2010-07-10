package cs236369.hw5.users;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.sql.rowset.serial.SerialBlob;

import com.jhlabs.image.StampFilter;

import cs236369.hw5.Administrator;
import cs236369.hw5.Researcher;
import cs236369.hw5.User;
import cs236369.hw5.User.UserType;
import cs236369.hw5.db.DbManager;
import cs236369.hw5.db.DbManager.DbConnections.SqlError;
import cs236369.hw5.users.SendMail.SendingMailError;

public class UserManager {
	public static class Unauthenticated extends Exception{}
	public static enum AuthenticationMode {ADMIN_AND_OWNER,ANY_USER,ADMIN_ONLY};
	public static class LeaderNotExists extends Exception{}
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
	public static String AdminAuth="admin_auth";
	public static String Captcha="jcaptcha";
	public static String Email="email";
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
	
	public static void updateUser(String login,String pass,String group,String permission,String name,String phone,String address,Blob stream,UserType type,String email) throws SQLException, UserNotExists, LeaderNotExists
	{
		User user= null;
		if (type.equals(UserType.ADMIN))
		{
			user = new Administrator(login,pass,name,permission,group,phone,address,stream,email);
		}
		else
		{
			user = new Researcher(login,pass,name,permission,group,phone,address,stream,email);
		}
		Connection conn=null;
		ResultSet set=null;
		try{
		conn=DbManager.DbConnections.getInstance().getConnection();
		//we want to lock the current user , and the new group leader , that
		//someone else cannot do something to this record
		conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
		conn.setAutoCommit(false);
		//here the user will be locked
		 PreparedStatement userExists= User.getUserDetails(conn, login);
		 set= userExists.executeQuery();
		 User previousUser=null;
		 if (!set.next())
		 {
			throw new UserNotExists();
		 }
		 else
		 {
			 previousUser=setUserFromRow(set);
		 }
		 //here the group leader will be locked
		 PreparedStatement statementUsersLeader= user.setGetUserGroupLeader(conn,user);
		 set=statementUsersLeader.executeQuery();
		 if (!set.next())
		 {
			 throw new LeaderNotExists();
		 }
		 PreparedStatement[] statementUsers= user.setUpdateUserDet(conn,previousUser);
		 for (PreparedStatement preparedStatement : statementUsers) {
			 preparedStatement.execute();
		}
		 conn.commit();
		}
		catch (UserNotExists ex)
		{
			conn.rollback();
			throw ex;
		}
		catch (LeaderNotExists ex)
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
	public static ArrayList<String> getGroups() throws SQLException
	{
		Connection conn=null;
		 ResultSet set= null;
		 ArrayList<String> groups= new ArrayList<String>();
		try{
		conn=DbManager.DbConnections.getInstance().getConnection();
		PreparedStatement userGroups= User.getUserGroups(conn);
		set= userGroups.executeQuery();
		while (set.next())
		{
			groups.add(set.getString("usergroup"));
		}
		return groups;
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
		
		//no matter which situation , that db will stay consistent.
		//suppose that X is not a group leader , so deleting it will delete 
		//only his record. (even if we try to update concurently , reading X will lock it,
		//then moving it to group Y and his members will succeed , now trying to update members which
		//were belong to his first group will lead to nothing (they shouldn't be updated)
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		conn.setAutoCommit(false);
		PreparedStatement userExists= User.getUserDetails(conn, login);
		set= userExists.executeQuery();
		User user=null;
		if (!set.next())
		{
				throw new UserNotExists();
		}
		else
		{
			 user=UserManager.setUserFromRow(set);
			if (user.getRole().equals(UserType.ADMIN))
			{
				throw new TryDeleteAdmin();
			}
		}
		if (user.isGroupLeader())
		{
			//update all group members to be in 1 person group
			 PreparedStatement updateGroupMembers=user.setUpdateGroupsOnDelete(conn, user.getGroup());
			 updateGroupMembers.execute();
		}
		//delete the user and his role
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
	public static void AddUser(String login,String pass,String group,String permission,String name,String phone,String address,Blob stream,UserType type,String email) throws SQLException, UserExists, LeaderNotExists
	{
		User user= null;
		if (type.equals(UserType.ADMIN))
		{
			user = new Administrator(login,pass,name,permission,group,phone,address,stream,email);
		}
		else
		{
			user = new Researcher(login,pass,name,permission,group,phone,address,stream,email);
		}
		Connection conn=null;
		ResultSet set=null; 
		try{
		conn=DbManager.DbConnections.getInstance().getConnection();
		conn.setAutoCommit(false);
		//we need the repeatable read in order to obtain shared lock
		//on the group leader. We don't want that when inserting a new member to the group,
		//the group leader will be deleted or moved to other group.
		conn.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
		if (!user.isGroupLeader())
		{
			 PreparedStatement statementUsersLeader= user.setGetUserGroupLeader(conn,user);
			 set=statementUsersLeader.executeQuery();
			 if (!set.next())
			 {
				 throw new LeaderNotExists();
			 }
		 }
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
		catch (LeaderNotExists ex)
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
			
			 
			 
			 user = new Researcher(set.getString("login"), set.getString("password"), set.getString("name"), set.getString("permission"), set.getString("usergroup"), set.getString("phone"), set.getString("address"),b,set.getString("email"));
		 }
		 else
		 {
			 user = new Administrator(set.getString("login"), set.getString("password"), set.getString("name"), set.getString("permission"), set.getString("usergroup"), set.getString("phone"), set.getString("address"),b,set.getString("email"));
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

	public static void recoverPassword(String login,String newPass) throws UserNotExists, SQLException, SendingMailError {
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
		//send the mail
		String  email=setUserFromRow(set).getEmail();
		SendMail mail = new SendMail(email,login,newPass);
		mail.send();
		//update the database (if the mail delivered fine )
		User user= new Researcher(login);
		user.setPassword(newPass);
		PreparedStatement statementUsers= user.setUpdatePassword(conn);
		statementUsers.execute();
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
	
	public static void resetPassword(String login, String newPass) throws UserNotExists, SQLException {
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
		User user= new Researcher(login);
		user.setPassword(newPass);
		PreparedStatement statementUsers= user.setUpdatePassword(conn);
		statementUsers.execute();
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
}
