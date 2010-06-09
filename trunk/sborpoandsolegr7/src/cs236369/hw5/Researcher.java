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
			String premissions, String group, String phoneNumber,
			String active, String address, Image photo) {
		super(login, password, name, premissions, group, phoneNumber, active,
				address, photo);
		// TODO Auto-generated constructor stub
	}
	
	public Researcher()
	{
		
	}


	public Researcher(String login) {
		super(login);
	}

	

	

}
