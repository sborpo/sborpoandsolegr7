package cs236369.hw5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Administrator extends User{

	


	public Administrator(String login, String password, String name,
			String premissions, String group, String phoneNumber,
			String address, byte[] photo) {
		super(login, password, name, premissions, group, phoneNumber, address, photo);

	}

	public Administrator(String login) {
		super(login);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PreparedStatement setUpdateRole(Connection conn) throws SQLException {
		String query= "INSERT INTO user_roles VALUES(?,?);";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1,login);
		prepareStatement.setString(2,"admin");
		return prepareStatement;
	}


}
