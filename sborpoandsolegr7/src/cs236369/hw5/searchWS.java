package cs236369.hw5;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import cs236369.hw5.ReservationManager.Period;



public class searchWS {

	public String[] search(String[] keywords, int k){

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String[] date=sdf.format(cal.getTime()).split("/");
		try{
	//	TimeSlot initialeTimeSlot= new TimeSlot(Integer.valueOf(date[2]), Integer.valueOf(date[1]),Integer.valueOf(date[0]));
		TimeSlot initialeTimeSlot = new TimeSlot(2009, 11, 2);
		HashMap<Long,Period> slots=ReservationManager.searchForSlotsAv(initialeTimeSlot, keywords, k);
		ArrayList<String> avSlots= new ArrayList<String>();
		for (Period period : slots.values()) {
			avSlots.add("sborpoandsolegr7||"+period.getInstId()+"||"+period.getStart());
		}
		if (avSlots.size()==0)
		{
			return new String[0];
		}
		String [] answer = new String[avSlots.size()];
		avSlots.toArray(answer);
		return answer;
		}
		catch(SQLException e)
		{
			return new String[0];
		}
	}
}
