package cs236369.hw5;

public class TimeSlot {
	public TimeSlot(int year, int slotNumber) {
		super();
		this.year = year;
		this.month = slotNumber%12;
		this.day = slotNumber %30;
		this.slotNumber = (slotNumber/12)/30;
	}
	int year;
	int month;
	int day;
	int slotNumber;
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}
	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}
	/**
	 * @return the slotNumber
	 */
	public int getSlotNumber() {
		return slotNumber;
	}
}
