package cs236369.hw5.instrument;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import cs236369.hw5.User;
import cs236369.hw5.db.DbManager;

/**
 * ID - Identifies the instrument, in the lab.
Type - The type of the instrument (e.g., MRI, NMR, etc.)
Permission – The required permission code for reserving the instrument.
TimeSlot - The length of the time slots for this instrument.
Description – General description of the instrument.
Instruments may have additional properties according to your choice.
 * @author Oleg
 *
 */

public class Instrument {

	//private static AtomicInteger idGenerator = new AtomicInteger(getBiggestIdFromDB());
	private String description = null;
	private long id = -1;
	private Integer timeSlot = null;
	private String type = null;
	private Integer premission = null;
	private String location = null;

	/**
	 * create an Instrument using given parameters
	 * @param description
	 * @param id
	 * @param timeSlot
	 * @param type
	 */
//	public Instrument(String description, Integer timeSlot, String type, Integer premission, String location) {
//		super();
//		this.description = description;
//		this.id = idGenerator.addAndGet(1);
//		this.timeSlot = timeSlot;
//		this.type = type;
//		this.premission = premission;
//		this.location = location;
//	}

	public Instrument(Integer id,String type, Integer premission, Integer timeSlot, String description,  String location) {
		super();
		this.id = id;
		this.description = description;
		this.timeSlot = timeSlot;
		this.type = type;
		this.premission = premission;
		this.location = location;
	}

	public void UpdateInstrument(String description, Integer timeSlot, String type, Integer premission, String location) {
		this.description = description;
		this.timeSlot = timeSlot;
		this.type = type;
		this.premission = premission;
		this.location = location;
	}
	
	

	public PreparedStatement setInsertInstrument(Connection conn) throws SQLException
	{

		String query= "INSERT INTO instruments (`id`,`type`,`permission`,`timeslot`,`description`,`location`) VALUES(?,?,?,?,?,?)";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1,Integer.toString((int) id));
		prepareStatement.setString(2,type);
		prepareStatement.setInt(3,premission);
		prepareStatement.setLong(4,timeSlot);
		if (description != null) {
			prepareStatement.setString(5,description);
		}
		else {
			prepareStatement.setNull(5, java.sql.Types.VARCHAR);
		}
		prepareStatement.setString(6,location);

		return prepareStatement;
	}

	/**
	 * create Instrument form a given DB entry
	 * @param id
	 */
	public Instrument(long id) {
		// TODO search Instrument in db.
	}

	public String getDescription() {
		return description;
	}

	public long getId() {
		return id;
	}
	
	public static int getGenID () {
		return 0;
	}
	


	public int getTimeSlot() {
		return timeSlot;
	}

	public String getType() {
		return type;
	}

	public static PreparedStatement getDetails(Connection conn, int id) throws SQLException {
		String query= "SELECT * FROM instruments WHERE login=? ";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setLong(1, id);
		return prepareStatement;
	}

	public PreparedStatement setUpdateInstDet(Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the premission
	 */
	public Integer getPremission() {
		return premission;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	public static PreparedStatement getAllInstruments(Connection conn) throws SQLException {
		String query= "SELECT * FROM instruments";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		return prepareStatement;
	}


}
