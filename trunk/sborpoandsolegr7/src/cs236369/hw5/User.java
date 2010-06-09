package cs236369.hw5;

import java.awt.Image;

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
public class User {
	
	private String login;
	private String password;
	private String name;
	private String premissions;
	private String group;
	private String phoneNumber;
	private String active;
	private String address;
	private Image  photo;
	
	/**
	 * create a user with given parameters
	 * @param login
	 * @param password
	 * @param name
	 * @param premissions
	 * @param group
	 * @param phoneNumber
	 * @param active
	 * @param address
	 * @param photo
	 */
	public User(String login, String password, String name, String premissions,
			String group, String phoneNumber, String active, String address,
			Image photo) {
		super();
		this.login = login;
		this.password = password;
		this.name = name;
		this.premissions = premissions;
		this.group = group;
		this.phoneNumber = phoneNumber;
		this.active = active;
		this.address = address;
		this.photo = photo;
	}
	public User()
	{
		
	}
	
	
	public void
	
	
	
	
	updateUser(String login, String password, String name, String premissions,
			String group, String phoneNumber, String active, String address,
			Image photo) {
		this.login = login;
		this.password = password;
		this.name = name;
		this.premissions = premissions;
		this.group = group;
		this.phoneNumber = phoneNumber;
		this.active = active;
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
	 * @return the active
	 */
	public String getActive() {
		return active;
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
	public Image getPhoto() {
		return photo;
	}
	
	
}
