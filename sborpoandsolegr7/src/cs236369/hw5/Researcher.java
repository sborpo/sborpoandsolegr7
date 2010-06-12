package cs236369.hw5;

import java.awt.Image;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;

import cs236369.hw5.db.DbManager;

public class Researcher extends User {

	

	public Researcher(String login, String password, String name,
			String premissions, String group, String phoneNumber, String address, byte[] photo) {
		super(login, password, name, premissions, group, phoneNumber,address,
				photo);
		// TODO Auto-generated constructor stub
	}
	
	public Researcher()
	{
		
	}


	public Researcher(String login) {
		super(login);
	}

	@Override
	public PreparedStatement setUpdateRole(Connection conn) throws SQLException {
		String query= "INSERT INTO user_roles VALUES(?,?);";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1,login);
		prepareStatement.setString(2,"researcher");
		return prepareStatement;
		
	}

	@Override
	public UserType getRole() {
		return UserType.REASEARCHER;
	}





	

	

}
