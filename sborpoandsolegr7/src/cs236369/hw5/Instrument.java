package cs236369.hw5;

import java.sql.Connection;
import java.sql.PreparedStatement;

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
	

	private String description = null;
	private long id = -1;
	private Integer timeSlot = null;
	private String type = null;
	
	/**
	 * create an Instrument using given parameters
	 * @param description
	 * @param id
	 * @param timeSlot
	 * @param type
	 */
	public Instrument(String description, long id, Integer timeSlot, String type) {
		super();
		this.description = description;
		this.id = id;
		this.timeSlot = timeSlot;
		this.type = type;
	}
	
	public void UpdateInstrument(String description, long id, Integer timeSlot, String type) {
		this.description = description;
		this.id = id;
		this.timeSlot = timeSlot;
		this.type = type;
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

	public int getTimeSlot() {
		return timeSlot;
	}

	public String getType() {
		return type;
	}

	public static PreparedStatement getDetails(Connection conn, int id2) {
		// TODO Auto-generated method stub
		return null;
	}

	public PreparedStatement setUpdateInstDet(Connection conn) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
