package cs236369.hw5;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeSlot {
	
	public static int MAX_K=288;
	@Override
	public String toString() {
		return "("+year+","+month+","+day+","+new Integer(slotNumberInDay()).toString()+")";
	}
	public int slotNumberInDay()
	{
		return ((slotNumber-1)%numOfSlotsInDay()+1);
	}
	public boolean isLessOrEqualThen(TimeSlot second)
	{
		if (year<second.year)
		{
			return true;
		}
		if (year>second.year)
		{
			return false;
		}
		if (month<second.month)
		{
			return true;
		}
		if (month>second.month)
		{
			return false;
		}
		if (day<second.day)
		{
			return true;
		}
		if (day>second.day)
		{
			return false;
		}
		return slotNumberInDay()<=second.slotNumberInDay();
		
	}
	public TimeSlot(int year, int slotNumber) {
		super();
		if (slotNumber>numberOfTimeSlotsInAYear)
		{
			this.year=year+1;
			this.slotNumber=slotNumber-numberOfTimeSlotsInAYear;
		}
		else if (slotNumber<1)
		{
			this.year=year-1;
			this.slotNumber=numberOfTimeSlotsInAYear+slotNumber;
		}
		else
		{
			this.year = year;
			this.slotNumber=slotNumber;
		}
		this.month = (this.slotNumber-1)/(numOfSlotsInDay()*30)+1;
		this.day = ((this.slotNumber-1) %(numOfSlotsInDay()*30))/numOfSlotsInDay()+1;
	}
	public static int slotSizeInMinutes;
	public static int numberOfTimeSlotsInAYear ;
	
	static 
	{
		slotSizeInMinutes=15; 
		numberOfTimeSlotsInAYear= (60/slotSizeInMinutes)*24*30*12;	
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
	
	public static TimeSlot addTimeSlot(TimeSlot src,int timeslots)
	{
		int sltNum=src.getSlotNumber();
		int year= src.getYear();
		int nextSlot=sltNum+timeslots;
		if (nextSlot>numberOfTimeSlotsInAYear)
		{
			year++;
			nextSlot=nextSlot-numberOfTimeSlotsInAYear;
		}
		return new TimeSlot(year,nextSlot);
		
	}
	public TimeSlot nextSlot()
	{
		if ((getSlotNumber()+1)>numberOfTimeSlotsInAYear)
		{
			return new TimeSlot(getYear()+1,1);
		}
		return  new TimeSlot(getYear(),getSlotNumber()+1);
	}
	
	public static int numOfSlotsInDay()
	{
		return (numberOfTimeSlotsInAYear/12/30);
	}
	public static int numOfSlotInWeek()
	{
		return (numOfSlotsInDay()*7);
	}
	
	public static int numOfSlotsInHour()
	{
		return 60/slotSizeInMinutes;
	}
	
	public TimeSlot(int year,int month,int day)
	{
		this.year=year;
		this.month=month;
		this.day=day;
		slotNumber=30*numOfSlotsInDay()*(month-1)+(day-1)*numOfSlotsInDay()+1;
	}
	
	
	public static String getSlotTime(int slotNumber)
	{
		slotNumber=slotNumber-1;
		int thehour=(slotNumber%numOfSlotsInDay())/numOfSlotsInHour();
		int quarter =((slotNumber)%numOfSlotsInHour());
		String hourStr="";
		String minuteStr="";
		if (thehour<10)
		{
			hourStr=hourStr+"0";
		}
		if (quarter==0)
		{
			minuteStr=minuteStr+"0";
		}
		return hourStr+String.valueOf(thehour)+":"+minuteStr+String.valueOf(quarter*slotSizeInMinutes);
		
	}
	
	public String getDatePrint()
	{
		return String.valueOf(day)+"/"+String.valueOf(month)+"/"+String.valueOf(year);
	}
	
	public static TimeSlot getTodayTimeSlot()
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String[] date=sdf.format(cal.getTime()).split("/");
		return new TimeSlot(Integer.valueOf(date[2]), Integer.valueOf(date[1]),Integer.valueOf(date[0]));
	}
	
	
}
