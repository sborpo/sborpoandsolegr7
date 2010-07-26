package cs236369.hw5;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;



public class Administrator extends User{

	


	public Administrator(String login, String password, String name,
			String premissions, String group, String phoneNumber,
			String address, Blob stream,String email) {
		super(login, password, name, premissions, group, phoneNumber, address, stream,email);

	}

	public Administrator(String login) {
		super(login);
	}

	@Override
	public PreparedStatement setInsertRole(Connection conn) throws SQLException {
		String query= "INSERT INTO user_roles VALUES(?,?);";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1,login);
		prepareStatement.setString(2,"admin");
		return prepareStatement;
	}

	@Override
	public UserType getRole() {
		return UserType.ADMIN;
		
	}

	@Override
	public PreparedStatement setUpdateRole(Connection conn)
			throws SQLException {
		String query= "UPDATE user_roles SET rolename=? WHERE login=?";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1,"admin");
		prepareStatement.setString(2,login);
		return prepareStatement;
	}


}
