package cs236369.hw5.instrument;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.sql.rowset.serial.SerialBlob;

import cs236369.hw5.User;
import cs236369.hw5.User.UserType;
import cs236369.hw5.db.DbManager;
import cs236369.hw5.db.DbManager.DbConnections.SqlError;

public class InstrumentManager {
	public static class InstrumentExists extends Exception {
	}

	public static class InstrumentNotExists extends Exception {
	}

	public static final String Captcha = "captcha";
	public static String ID = "instrumentID";
	public static String Type = "type";
	public static String Premission = "permission";
	public static String TimeSlot = "timeslot";
	public static String Description = "description";
	public static String Location = "location";
	public static String NotSpecified = "Not Specified";

	public static void updateInstrument(int id, String pass, String group,
			String permission, String name, String phone, String address,
			Blob stream, UserType type) throws SQLException,
			InstrumentNotExists {
		Instrument inst = new Instrument(id);
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
					.getDetails(conn, id);
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

	public static void addInstrument(String description,
			String location, String permission, String timeslot, String type)
			throws SQLException, InstrumentExists {
		Instrument instrument = null;
		try {
			instrument = new Instrument(description,
					Integer.parseInt(timeslot), type, Integer
							.parseInt(permission), location);
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

	public static Instrument getUserDetails(int id) throws SQLException {
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
					.getAllInstrumentsNoPicture(conn);
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

	private static Instrument setInstrumentFromRow(ResultSet set)
			throws SQLException {
		if (!set.wasNull()) {
			return new Instrument(set.getInt("id"), set.getString("type"), set
					.getInt("permission"), set.getInt("timeslot"), set
					.getString("description"), set.getString("location"));
		}
		return null;
	}
}
