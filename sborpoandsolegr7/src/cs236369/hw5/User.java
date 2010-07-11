package cs236369.hw5;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;





/**
 * Each user has the following properties:
 *
 * Login - Identifies the user.
 * Password - A password for administrators (Optional for ordinary users).
 * Name - The name of the user. You may split this field into two fields: Given name and Surname.
 * Permissions - A list of permission codes specifying the instruments the user is permitted to reserve according to his/her training. The default should be an empty list.
 * Group - The research group that the user belongs to.
 * Phone number - The phone number of the user.
 * Active - Specifies if the user is still active (if you chose to implement this optional feature). Only active users should be able to reserve instruments. The default status should be 'active'.
 * Address - The address of the user.
 * Photo - A photo of the user. You may store the photo as a BLOB in the database.
 * 
 * @author Oleg
 *
 */
public abstract class User {
	
	


	public void setPassword(String password) {
		this.password = password;
	}


	public User(String login, String password, String name, String premissions,
			String group, String phoneNumber, String address, Blob photo,
			String email) {
		super();
		this.login = login;
		this.password = password;
		this.name = name;
		this.premissions = premissions;
		this.group = group;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.photo = photo;
		this.email = email;
	}
	public static enum UserType { ADMIN,REASEARCHER;
	
	public String toString()
	{
		if (this.equals(ADMIN))
		{
			return "admin";
		}
		else
		{
			return "researcher";
		}
	}
	}
	protected String login;
	protected String password;
	protected String name;
	protected String premissions;
	protected String group;
	protected String phoneNumber;
	protected String address;
	protected Blob  photo;
	protected String email;

	
	
	public User()
	{
		
	}
	
	
	public void
	updateUser(String login, String password, String name, String premissions,
			String group, String phoneNumber, String address,
			Blob photo) {
		this.login = login;
		this.password = password;
		this.name = name;
		this.premissions = premissions;
		this.group = group;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.photo = photo;
	}
	
	/**
	 * create a user from an existing DB entry
	 * @param login
	 */
	public User(String login) {
		this.login=login;
	}
	
	public String getEmail() {
		return email;
	}
	
	
	
	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the premissions
	 */
	public String getPremissions() {
		return premissions;
	}
	/**
	 * @return the group
	 */
	public String getGroup() {
		return group;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @return the photo
	 */
	public Blob getPhoto() {
		return photo;
	}
	
	

	
	private PreparedStatement GroupLeaderUpdate(Connection conn) throws SQLException
	{
		String isPhotoUpdate=((photo!=null)? " , photo=? " :"");
		String isPermissionUpdate=((premissions !=null)? " , permission=?" :"");
		String query= "UPDATE users SET name=?, usergroup=?,email=?, phone=?, address=?"+isPermissionUpdate+isPhotoUpdate+ " WHERE login=? ";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1,name);
		prepareStatement.setString(2,group);
		prepareStatement.setString(3,email);
		if (phoneNumber!=null)
		{
			prepareStatement.setString(4,phoneNumber);
		}
		else
		{
			prepareStatement.setNull(4, java.sql.Types.VARCHAR);
		}
		if (address!=null)
		{
			prepareStatement.setString(5,address);
		}
		else
		{
			prepareStatement.setNull(5, java.sql.Types.VARCHAR);
		}
		if (premissions!=null)
		{
			prepareStatement.setString(6, premissions);
			if (photo!=null)
			{
				prepareStatement.setBlob(7, photo.getBinaryStream());
				prepareStatement.setString(8, login);
			}
			else
			{
				prepareStatement.setString(7, login);
			}
		}
		else
		{
			if (photo!=null)
			{
				prepareStatement.setBlob(6, photo.getBinaryStream());
				prepareStatement.setString(7, login);
			}
			else
			{
				prepareStatement.setString(6, login);
			}
		}
		
		return prepareStatement;
	}
	
	
	public PreparedStatement[] setUpdateUserDet(Connection conn, User previousUser) throws SQLException
	{
		PreparedStatement[] statements;
		boolean isGroupLeader= previousUser.getGroup().equals(previousUser.getLogin());
		if (isGroupLeader)
		{
			statements= new PreparedStatement[2];
			//we will move the rest of the team member with him too.
			statements[0]= GroupLeaderUpdate(conn);
			statements[1]= GroupMembersUpdate(conn, previousUser);
			
		}
		else
		{
			//no one but the user will move group
			statements= new PreparedStatement[1];
			statements[0]= GroupLeaderUpdate(conn);
		}
		return statements;
		
	}
	
	private PreparedStatement GroupMembersUpdate(Connection conn,
			User previousUser) throws SQLException {
		String query= "UPDATE users  SET usergroup=? WHERE usergroup=? ";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1,this.group);
		prepareStatement.setString(2,previousUser.getGroup());
		return prepareStatement;
	}


	public boolean isGroupLeader()
	{
		return this.group.equals(this.login);
	}
	
	public PreparedStatement setInsertUser(Connection conn) throws SQLException
	{
	
		String query= "INSERT INTO users (`login`,`password`,`name`,`permission`,`usergroup`,`phone`,`address`,`photo`,`email`) VALUES(?,?,?,?,?,?,?,?,?)";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1, login);
		prepareStatement.setString(2,password);
		prepareStatement.setString(3,name);
		prepareStatement.setString(4,premissions);
		prepareStatement.setString(5,group);
		if (phoneNumber!=null)
		{
			prepareStatement.setString(6,phoneNumber);
		}
		else
		{
			prepareStatement.setNull(6, java.sql.Types.VARCHAR);
		}
		if (address!=null)
		{
			prepareStatement.setString(7,address);
		}
		else
		{
			prepareStatement.setNull(7, java.sql.Types.VARCHAR);
		}
		if (photo!=null)
		{
			prepareStatement.setBlob(8, photo.getBinaryStream());
		}
		else
		{
			prepareStatement.setNull(8, java.sql.Types.BLOB);
		}
		prepareStatement.setString(9,email);
		return prepareStatement;
	}
	
	public static PreparedStatement getUserPicture(Connection conn,String username) throws SQLException
	{

		String query= "SELECT photo FROM users  WHERE login=? ";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1, username);
		return prepareStatement;
	}
	
	
	
	public static PreparedStatement getUserDetails(Connection conn,String username) throws SQLException
	{

		String query= "SELECT U.login,password,name,permission,usergroup,phone,address,U.photo, UR.rolename,email FROM users U,user_roles UR WHERE U.login=UR.login AND U.login=? ";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1, username);
		return prepareStatement;
	}
	
	public static PreparedStatement getAllUsersNoPicture(Connection conn) throws SQLException
	{

		String query= "SELECT U.login , password , name ,permission, usergroup , phone , address ,photo, UR.rolename, email FROM users U, user_roles UR WHERE U.login=UR.login ";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		return prepareStatement;
	}
	
	public PreparedStatement setUpdateGroupsOnDelete(Connection conn,String group) throws SQLException
	{
		String query= "UPDATE users SET usergroup=login WHERE usergroup=? ";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1, group);
		return prepareStatement;
	}
	public  PreparedStatement setDeleteUser(Connection conn) throws SQLException
	{
		String query= "DELETE FROM users WHERE login=? ";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1, login);
		return prepareStatement;
	}
	public  PreparedStatement setDeleteUserRole(Connection conn) throws SQLException
	{
		String query= "DELETE  FROM user_roles WHERE login=? ";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1, login);
		return prepareStatement;
	}
	
	public  PreparedStatement setUpdatePassword(Connection conn) throws SQLException
	{
		String query= "UPDATE users SET password=? WHERE login=? ";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1, password);
		prepareStatement.setString(2, login);
		return prepareStatement;
	}
	
	public  abstract PreparedStatement setUpdateRole(Connection statement) throws SQLException;
	public  abstract PreparedStatement setInsertRole(Connection statement) throws SQLException;
	public abstract UserType getRole();


	public static PreparedStatement getUserGroups(Connection conn) throws SQLException {
		String query= "SELECT DISTINCT usergroup FROM users WHERE NOT(usergroup='root')";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		return prepareStatement;
	}


	public PreparedStatement setGetUserGroupLeader(Connection conn, User user) throws SQLException {
		String query= "SELECT usergroup FROM users WHERE login=? AND (usergroup=login)";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1, user.getGroup());
		return prepareStatement;
	}
		
}	
