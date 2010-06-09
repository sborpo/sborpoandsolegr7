package cs236369.hw5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;


import cs236369.hw5.db.DbManager;

public  class ReservationManager {
	
	public static enum Avilability {
		AVAILABLE(1), TAKEN(0), NOT_AVAILABLE(-1);

		private int actualValue;

		private Avilability(int value) {
			actualValue = value;
		}

		static public int compare(Avilability av1, Avilability av2) {
			if (av1.actualValue > av2.actualValue) {
				return 1;
			}
			if (av1.actualValue == av2.actualValue) {
				return 0;
			}
			return -1;
		}
	}
	
	public static class AvailabilityResult {
		Avilability avilability;
		HashSet<Long> instruments = new HashSet<Long>();;

		/**
		 * @return the avilability
		 */
		public Avilability getAvilability() {
			return avilability;
		}

		/**
		 * @param avilability
		 *            the avilability to set
		 */
		public void setAvilability(Avilability avilability) {
			this.avilability = avilability;
		}

		/**
		 * @return the instruments
		 */
		public HashSet<Long> getInstruments() {
			return instruments;
		}
		
	

	}
	
	public static class ReservationTable
	{
		String [][] arr;
		public ReservationTable(int year,int month,int day,int k ,String type) throws SQLException {
			
			ReservationManager.AvailabilityResult[] res=ReservationManager.Search(new TimeSlot(year,month,day),k,type);
			int index=0;
			TimeSlot t1= new TimeSlot(year,month,day);
			TimeSlot t2 = new TimeSlot(year, month, day);
			arr = new String[TimeSlot.numOfSlotsInDay()+1][8];
			arr[0][0]="Time/Date";
			for (int i=1; i<=TimeSlot.numOfSlotsInDay();i++)
			{
				arr[i][0]=TimeSlot.getSlotTime(t1.getSlotNumber());
				t1=t1.nextSlot();
			}
			for (int j=1; j<8; j++)
			{
				arr[0][j]=t2.getDay()+"/"+t2.getMonth();
				for (int i=1; i<=TimeSlot.numOfSlotsInDay(); i++)
				{
					arr[i][j]=String.valueOf(res[index].getAvilability().actualValue)+";";
					String values="";
					for (Long instrument : res[index].getInstruments()) {
						values=values+" "+instrument.toString();
					}
					arr[i][j]=arr[i][j]+values;
					index++;
				}
				t2=new TimeSlot(t2.getYear(), t2.getSlotNumber()+TimeSlot.numOfSlotsInDay());
				
			}
		}
		public String[][] getReservationTable()
		{
			return arr;
		}
	}
	
	
	private static ResultSet executeOccupiedSlotsQuery(Connection conn,TimeSlot initialeSlot,String type) throws SQLException
	{
		String query= "SELECT id,year,slotbegin,slotend FROM reservations R, instruments I" +
		" WHERE ((R.slotbegin>=? AND R.year=?) OR (R.slotbegin<=? AND R.year=?)) AND I.id=R.instid AND I.type=?" +
		" ORDER BY id,year,slotbegin";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setInt(1, initialeSlot.getSlotNumber());
		prepareStatement.setInt(2, initialeSlot.getYear());
		int slotNumberWeekLater=(initialeSlot.getSlotNumber()+TimeSlot.numOfSlotInWeek()-1);
		if (slotNumberWeekLater>TimeSlot.numberOfTimeSlotsInAYear)
		{
			prepareStatement.setInt(3,slotNumberWeekLater-TimeSlot.numberOfTimeSlotsInAYear);
		}
		else
		{
			prepareStatement.setInt(3,-1);
		}
		prepareStatement.setInt(4, initialeSlot.getYear()+1);
		prepareStatement.setString(5, type);
		return prepareStatement.executeQuery();
		
	}
	
	private static class InstrumentAvilability
	{
		private Avilability[] avilibilityArr;
		private long instId;
		public Avilability[] getAvilibilityArr() {
			return avilibilityArr;
		}
		public void setAvilibilityArr(Avilability[] avilibilityArr) {
			this.avilibilityArr = avilibilityArr;
		}
		public long getInstId() {
			return instId;
		}
		public void setInstId(long instId) {
			this.instId = instId;
		}
	
	}
	
	public static LinkedList<InstrumentAvilability> getOccupiedSlotsFromDatabase(TimeSlot initialeSlot,String type) throws SQLException
	{
		Connection conn=DbManager.DbConnections.getInstance().getConnection();
		ResultSet set= executeOccupiedSlotsQuery(conn,initialeSlot,type);
		LinkedList<InstrumentAvilability> availibilityArrs = new LinkedList<InstrumentAvilability>();
		int flag=1,i=0;
		long currentInst=-1,nextInst;
		Avilability[] arr=null ;
		if (set.next()==false){flag=0;}
		while ((flag==1))
		{
			nextInst= set.getLong("id");
			if (currentInst!=nextInst)
			{
				
				arr = new Avilability[TimeSlot.numOfSlotInWeek()];
				i=0;
			}
			currentInst=nextInst;
			for (;(i+initialeSlot.getSlotNumber()<set.getInt("slotbegin"))&&(i<TimeSlot.numOfSlotInWeek()); i++)
			{
					arr[i]=Avilability.TAKEN;
			}
			for (; (i+initialeSlot.getSlotNumber()<set.getInt("slotend"))&&(i<TimeSlot.numOfSlotInWeek()); i++)
			{
					arr[i]=Avilability.NOT_AVAILABLE;
			}
			if (set.next()==false)
			{
					flag=1;
					for (;i<TimeSlot.numOfSlotInWeek(); i++)
					{
						arr[i]=Avilability.TAKEN;
					}
					addInstrumentAviablitily(currentInst,arr,availibilityArrs);
					break;
			}
			if (set.getLong("id")!=currentInst)
			{
				for (;i<TimeSlot.numOfSlotInWeek(); i++)
				{
					arr[i]=Avilability.TAKEN;
				}
				addInstrumentAviablitily(currentInst,arr,availibilityArrs);
			}

		}
		return availibilityArrs;
		
	}
	
	private static void addInstrumentAviablitily(long currentInst,Avilability[] arr,LinkedList<InstrumentAvilability> availibilityArrs )
	{
		InstrumentAvilability avi = new InstrumentAvilability();
		avi.setAvilibilityArr(arr);
		avi.setInstId(currentInst);
		availibilityArrs.add(avi);
	}

	public static AvailabilityResult[] Search(TimeSlot slot, int k, String type) throws SQLException {
		AvailabilityResult[] totalAvialableSlots = initializeTotalAvailableResults(TimeSlot.numOfSlotInWeek()); // init
																												// to
																												// -1
		Avilability[] occupiedSlotsForCurrInstrument = null;
		LinkedList<InstrumentAvilability> list= getOccupiedSlotsFromDatabase(slot, type);
		for (InstrumentAvilability avilabitly : list) {
			mergeCurrInstrument(k, totalAvialableSlots,
					avilabitly.getAvilibilityArr(), avilabitly.getInstId());
		}
		return totalAvialableSlots;
	}

	private static void mergeCurrInstrument(int k,
			AvailabilityResult[] totalAvialableSlots,
			Avilability[] occupiedSlotsForCurrInstrument, Long currInstument) {
		Integer i = new Integer (0);
		Integer j= new Integer(0);
		for ( j = 0; j < occupiedSlotsForCurrInstrument.length; j++) {
			if (occupiedSlotsForCurrInstrument[j] == Avilability.NOT_AVAILABLE) {
				i=markAvialability(k, occupiedSlotsForCurrInstrument, i, j);
			}
		}
		// a correction for the situation that 0,0,-1,-1,0,0,0,0,0,0...0
		//we should mark the all availble spots
		markAvialability(k, occupiedSlotsForCurrInstrument, i, j-1);
		
		for (j = 0; j < totalAvialableSlots.length; j++) {
			if (Avilability.compare(totalAvialableSlots[j].getAvilability(),
					occupiedSlotsForCurrInstrument[j]) == 0
					&& !totalAvialableSlots[j].getAvilability().equals(
							Avilability.NOT_AVAILABLE)) {
				totalAvialableSlots[j].getInstruments().add(currInstument);
			}
			if (Avilability.compare(totalAvialableSlots[j].getAvilability(),
					occupiedSlotsForCurrInstrument[j]) == -1) {
				totalAvialableSlots[j].getInstruments().clear();
				totalAvialableSlots[j].getInstruments().add(currInstument);
				totalAvialableSlots[j].setAvilability(occupiedSlotsForCurrInstrument[j]);
			}
			
			

		}
	}

	private static AvailabilityResult[] initializeTotalAvailableResults(
			int numberOfTimeSlotsInAYear) {
		AvailabilityResult[] temp = new AvailabilityResult[numberOfTimeSlotsInAYear];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = new AvailabilityResult();
			temp[i].setAvilability(Avilability.NOT_AVAILABLE);
		}
		return temp;
	}

	private static int markAvialability(int k,
			Avilability[] occupiedSlotsForCurrInstrument, Integer i, Integer j) {
		while ((j - i >= k)) {
			if (occupiedSlotsForCurrInstrument[i] != Avilability.NOT_AVAILABLE) {
				occupiedSlotsForCurrInstrument[i] = Avilability.AVAILABLE;
			}
			i++;
		}
		while (j - i > 0) {
			if (occupiedSlotsForCurrInstrument[i] != Avilability.NOT_AVAILABLE) {
				occupiedSlotsForCurrInstrument[i] = Avilability.TAKEN;
			}
			i++;
		}
		return i;
	}
	

}
