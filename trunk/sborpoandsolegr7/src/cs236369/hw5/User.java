package cs236369.hw5;

import java.io.InputStream;
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
	
	/**
	 * create a user with given parameters
	 * @param login
	 * @param password
	 * @param name
	 * @param premissions
	 * @param group
	 * @param phoneNumber
	 * @param address
	 * @param stream
	 */
	public User(String login, String password, String name, String premissions,
			String group, String phoneNumber, String address,
			Blob stream) {
		super();
		this.login = login;
		this.password = password;
		this.name = name;
		this.premissions = premissions;
		this.group = group;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.photo = stream;
	}
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
		// TODO add DB call here
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
	
	public PreparedStatement setInsertUser(Connection conn) throws SQLException
	{
		//TODO: handle BLOB
		String query= "INSERT INTO users (`login`,`password`,`name`,`permission`,`usergroup`,`phone`,`address`,`photo`) VALUES(?,?,?,?,?,?,?,?)";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1, login);
		prepareStatement.setString(2,password);
		prepareStatement.setString(3,name);
		prepareStatement.setString(4,premissions);
		prepareStatement.setString(5,group);
		prepareStatement.setString(6,phoneNumber);
		prepareStatement.setString(7,address);
		prepareStatement.setBlob(8, photo.getBinaryStream());
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

		String query= "SELECT U.login,password,name,permission,usergroup,phone,address,UR.rolename FROM users U,user_roles UR WHERE U.login=UR.login AND U.login=? ";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1, username);
		return prepareStatement;
	}
	
	public static PreparedStatement getAllUsersNoPicture(Connection conn) throws SQLException
	{

		String query= "SELECT U.login , password , name ,permission, usergroup , phone , address ,photo, UR.rolename FROM users U,user_roles UR WHERE U.login=UR.login ";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		return prepareStatement;
	}
	
	public  PreparedStatement setDeleteUser(Connection conn) throws SQLException
	{
		String query= "DELETE FROM users WHERE login=? ";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1, login);
		return prepareStatement;
	}
	public  abstract PreparedStatement setUpdateRole(Connection statement) throws SQLException;
	public abstract UserType getRole();
		
}	
