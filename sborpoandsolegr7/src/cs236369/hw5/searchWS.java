package cs236369.hw5;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import cs236369.hw5.ReservationManager.Period;



public class searchWS {

	public String[] search(String[] keywords, int k){
		TimeSlot initialeTimeSlot= TimeSlot.getTodayTimeSlot();
		try{
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
