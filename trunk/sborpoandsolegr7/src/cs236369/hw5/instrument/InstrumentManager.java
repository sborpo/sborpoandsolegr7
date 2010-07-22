package cs236369.hw5.instrument;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import cs236369.hw5.db.DbManager;
import cs236369.hw5.db.DbManager.DbConnections.SqlError;

public class InstrumentManager {
	public static class InstrumentExists extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}

	public static class InstrumentNotExists extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
	}

	public static final String Captcha = "captcha";
	public static final Integer DEFUALT = 15;
	public static String ID = "id";
	public static String Type = "type";
	public static String Premission = "permission";
	public static String TimeSlot = "timeslot";
	public static String Description = "description";
	public static String Location = "location";
	public static String NotSpecified = "Not Specified";

	public static void updateInstrument(String id, String type, String permission, String timeslot, String location,String description) throws SQLException,
	InstrumentNotExists {
		Instrument inst = null;
		try {
			inst = new Instrument(Integer.parseInt(id), type, Integer.parseInt(permission), Integer.parseInt(timeslot), description, location);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conn = null;
		ResultSet set = null;
		try {
			conn = DbManager.DbConnections.getInstance().getConnection();
			// TODO: verify this is the needed isolation level.
			// When we read data , we don't aquire locks , so we don't care if
			// someone has deleted
			// the row or change it. Becuase update is done only on existing
			// rows. (the admin could have been deleted the
			// user after he changed his details.
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			conn.setAutoCommit(false);
			PreparedStatement instrumentExists = Instrument
			.getDetails(conn, Integer.parseInt(id));
			set = instrumentExists.executeQuery();
			if (!set.next()) {
				throw new InstrumentNotExists();
			}
			PreparedStatement statementInstruments = inst
			.setUpdateInstDet(conn);
			statementInstruments.execute();
			conn.commit();
		} catch (InstrumentNotExists ex) {
			conn.rollback();
			throw ex;
		} catch (SQLException ex) {
			conn.rollback();
			throw ex;
		} finally {
			if (set != null) {
				set.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	public static void addInstrument(String id, String description,
			String location, String permission, String timeslot, String type)
	throws SQLException, InstrumentExists {
		Instrument instrument = null;
		try {
			instrument = new Instrument(Integer.parseInt(id),type,
					Integer.parseInt(permission), Integer
					.parseInt(timeslot),description , location);
		} catch (NumberFormatException e) {
			// TODO: add error here
			e.printStackTrace();
		}

		Connection conn = null;

		try {
			conn = DbManager.DbConnections.getInstance().getConnection();
			conn.setAutoCommit(false);
			PreparedStatement statementInstruments = instrument
			.setInsertInstrument(conn);
			statementInstruments.execute();
			conn.commit();
		} catch (SQLException ex) {
			conn.rollback();
			if (SqlError.PRIMARY_KEY_ERROR.errorNumber() == ex.getErrorCode()) {
				throw new InstrumentExists();
			}
			throw ex;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}

	public static Instrument getInstrumentDetails(int id) throws SQLException {
		Connection conn = null;
		ResultSet set = null;
		try {
			conn = DbManager.DbConnections.getInstance().getConnection();
			PreparedStatement statementInstruments = Instrument.getDetails(
					conn, id);
			set = statementInstruments.executeQuery();
			Instrument instrument = null;
			if (set.next()) {
				instrument = setInstrumentFromRow(set);
			}
			return instrument;
		} finally {
			if (set != null) {
				set.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
	}

	public static LinkedList<Instrument> getInstruments() throws SQLException {
		Connection conn = null;
		ResultSet set = null;
		LinkedList<Instrument> instrumentsList = new LinkedList<Instrument>();
		try {
			conn = DbManager.DbConnections.getInstance().getConnection();
			PreparedStatement statementUsers = Instrument
			.getAllInstruments(conn);
			set = statementUsers.executeQuery();
			while (set.next()) {

				Instrument instrument = null;
				instrument = setInstrumentFromRow(set);
				instrumentsList.add(instrument);
			}
			return instrumentsList;
		} finally {
			if (set != null) {
				set.close();
			}
			if (conn != null) {
				conn.close();
			}

		}
	}

	static Instrument setInstrumentFromRow(ResultSet set)
	throws SQLException {
		if (!set.wasNull()) {
			return new Instrument(set.getInt("id"), set.getString("type"), set
					.getInt("permission"), DEFUALT, set
					.getString("description"), set.getString("location"));
		}
		return null;
	}

	public static Boolean isInstrumentExists (int id) throws SQLException {
		Connection conn=null;
		ResultSet set= null;
		try{
			conn=DbManager.DbConnections.getInstance().getConnection();
			PreparedStatement userExists= Instrument.getDetails(conn, id); //TODO: change
			set= userExists.executeQuery();
			if (!set.next())
			{
				return false;

			}
			return true;
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

	public static void removeInstrument(String id) throws SQLException, InstrumentNotExists, NumberFormatException {
		Connection conn=null;
		ResultSet set= null;
		try{
			int instrumentID = Integer.parseInt(id);
			conn=DbManager.DbConnections.getInstance().getConnection();			
			PreparedStatement instrumentRemoval= Instrument.removeInstrumnt(conn, instrumentID); //TODO: change
			set= instrumentRemoval.executeQuery();
			//TODO: finish
			
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
