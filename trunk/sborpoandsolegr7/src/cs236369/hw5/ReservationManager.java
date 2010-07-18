package cs236369.hw5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
		public ReservationTable(int year,int month,int day,int k ,String type,String login) throws SQLException {
			
			ReservationManager.AvailabilityResult[] res=ReservationManager.Search(new TimeSlot(year,month,day),k,type,login);
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
	
	
	private static ResultSet executeOccupiedSlotsQuery(Connection conn,TimeSlot initialeSlot,String type, int k,String login) throws SQLException
	{
		String query= " SELECT id,year,slotbegin,slotend,I.type AS type FROM " +
		" (SELECT INST.* FROM instruments INST,users U WHERE U.login=? AND INST.type=? AND ((U.permission IS NULL) OR (U.permission LIKE (SELECT CONCAT('%,',INST.permission,',%'))) OR (U.permission LIKE (SELECT CONCAT(INST.permission,',%'))))  )I" +
		"  LEFT OUTER JOIN reservations R" +
		" ON ((R.slotbegin>=? AND R.year=?) OR (R.slotbegin<=? AND R.year=?)) AND I.id=R.instid AND I.type=?" +
		" ORDER BY id,year,slotbegin ";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1, login);
		prepareStatement.setString(2, type);
		prepareStatement.setInt(3, initialeSlot.getSlotNumber());
		prepareStatement.setInt(4, initialeSlot.getYear());
		int slotNumberWeekLater=(initialeSlot.getSlotNumber()+TimeSlot.numOfSlotInWeek()+k-1);
		if (slotNumberWeekLater>TimeSlot.numberOfTimeSlotsInAYear)
		{
			prepareStatement.setInt(5,slotNumberWeekLater-TimeSlot.numberOfTimeSlotsInAYear);
		}
		else
		{
			prepareStatement.setInt(5,-1);
		}
		prepareStatement.setInt(6, initialeSlot.getYear()+1);
		prepareStatement.setString(7, type);
		
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
	
	
	public static class Period
	{
		@Override
		public String toString() {
			return "Period [end=" + end + ", instId=" + instId + ", start="
					+ start + "]";
		}
		public long getInstId() {
			return instId;
		}
		public TimeSlot getStart() {
			return start;
		}
		public TimeSlot getEnd() {
			return end;
		}
		public Period(long instId, TimeSlot start, TimeSlot end) {
			super();
			this.instId = instId;
			this.start = start;
			this.end = end;
		}
		private long instId;
		private TimeSlot start;
		private TimeSlot end;
		
	}
	public static HashMap<Long,Period> searchForSlotsAv(TimeSlot initialeTimeSlot,String [] descriptions , int k) throws SQLException
	{
		
		ResultSet set=null;Connection conn=null;
		StringBuilder keywordsStr = new StringBuilder();
		for (String keyword : descriptions) {
			keywordsStr.append(keyword+" ");
		}
		try{	
		 conn=DbManager.DbConnections.getInstance().getConnection();
		 conn.setAutoCommit(false);
		 conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		 set= executeAvailiablePeriods(conn,initialeTimeSlot,keywordsStr.toString(),k);
		 HashMap<Long,TimeSlot> maxSlot=getLatestTimeSlot(conn,keywordsStr.toString(),initialeTimeSlot);
		 HashMap<Long,Period> periodList= getShortestTimeSlot(conn,keywordsStr.toString(),initialeTimeSlot,k);
		 long instId=-1;
		 while (set.next())
		 {
			 if (set.getLong("instid")!=instId)
			 {
				 instId=set.getLong("instid");
				 if (!periodList.containsKey(instId))
				 {
					 periodList.put(instId,new Period(instId, new TimeSlot(set.getInt("syear"),set.getInt("send")), new TimeSlot(set.getInt("eyear"),set.getInt("ebegin"))));
				 }
			 }
		 }
		conn.commit();
		for (long inst : maxSlot.keySet()) {
			if (!periodList.containsKey(inst))
			{
				periodList.put(inst, new Period(inst, maxSlot.get(inst), null));
			}
		}
		return periodList;
		}
		finally
		{
			if (set!=null){set.close();}
			if (conn!=null){conn.close();}
		}
	}
	
	
	private static HashMap<Long,Period> getShortestTimeSlot(Connection conn,String keywords,TimeSlot initialeTimeSlot,int k) throws SQLException {
		ResultSet set=null;
		HashMap<Long,Period> slot= new HashMap<Long, Period>();
		try{
		String query= "SELECT INST.id , M2.* FROM " +
				"( SELECT * FROM instruments WHERE (MATCH (type,description) AGAINST (?  IN BOOLEAN MODE))" +
				")INST LEFT OUTER JOIN " +
				"reservations M2 " +
				" ON ((year>? ) OR ((year=?)AND (slotbegin>?))) " +
				"ORDER BY INST.id  , M2.year ,M2.slotbegin  ";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setInt(2, initialeTimeSlot.getYear());
		prepareStatement.setInt(3, initialeTimeSlot.getYear());
		prepareStatement.setInt(4, initialeTimeSlot.getSlotNumber());
		prepareStatement.setString(1, keywords);
		set= prepareStatement.executeQuery();
		 long instId=-1;
		while (set.next())
		{
			//check nullable , it means that there are no reservations after initiale time slot , so we can return the initiale time slot
			set.getInt("slotbegin");
			if (set.wasNull())
			{
				slot.put(set.getLong("id"),new Period(set.getLong("id"),initialeTimeSlot,null));
				continue;
			}
			 if (set.getLong("instid")!=instId)
			 {
				 instId=set.getLong("instid");
				 if (TimeSlot.addTimeSlot(initialeTimeSlot,k).isLessOrEqualThen(new TimeSlot(set.getInt("year"), set.getInt("slotbegin"))))
				 {
					 slot.put(instId,new Period(instId,initialeTimeSlot, new TimeSlot(set.getInt("year"), set.getInt("slotbegin"))));
				 }
				
			 }
		}
		return slot;
		}
		finally
		{
			if (set!=null)
			{
				set.close();
			}
		}
		
	}
	private static HashMap<Long,TimeSlot> getLatestTimeSlot(Connection conn,String keywords,TimeSlot initialeTimeSlot) throws SQLException {
		ResultSet set=null;
		HashMap<Long,TimeSlot> slot= new HashMap<Long, TimeSlot>();
		try{
		String query= "SELECT INST.id , M2.* FROM " +
				"( SELECT * FROM instruments WHERE (MATCH (type,description) AGAINST (?  IN BOOLEAN MODE))" +
				")INST LEFT OUTER JOIN " +
				"reservations M2 " +
				" ON ((year>? ) OR ((year=?)AND (slotbegin>?))) AND (INST.id=M2.instid) " +
				"ORDER BY INST.id  , M2.year DESC,M2.slotbegin DESC ";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setInt(2, initialeTimeSlot.getYear());
		prepareStatement.setInt(3, initialeTimeSlot.getYear());
		prepareStatement.setInt(4, initialeTimeSlot.getSlotNumber());
		prepareStatement.setString(1, keywords);
		set= prepareStatement.executeQuery();
		 long instId=-1;
		while (set.next())
		{
			//check nullable , it means that there are no reservations after initiale time slot , so we can return the initiale time slot
			set.getInt("slotbegin");
			if (set.wasNull())
			{
				continue;
			}
			 if (set.getLong("instid")!=instId)
			 {
				 instId=set.getLong("instid");
				 if (set.getInt("slotbegin")<set.getInt("slotend"))
					{
						slot.put(set.getLong("instid"), new TimeSlot(set.getInt("year"), set.getInt("slotend")));
					}
					else
					{
						slot.put(set.getLong("instid"), new TimeSlot(set.getInt("year")+1, set.getInt("slotend")));
					}
			 }
		}
		return slot;
		}
		finally
		{
			if (set!=null)
			{
				set.close();
			}
		}
		
	}

	private static ResultSet executeAvailiablePeriods(Connection conn,
			TimeSlot initialeTimeSlot, String keywords, int k) throws SQLException {

		//we are getting timeperiods were we have k slots.
		String query= "SELECT S1.instid, S1.year AS syear, S1.slotbegin AS sbegin, S1.slotend AS send,S2.year AS eyear,S2.slotbegin AS ebegin, S2.slotend AS eend FROM reservations S1, reservations S2 ,instruments I " +
			//get the instruments that match the keywords
			" WHERE (I.id=S1.instid) AND (MATCH (type,description) AGAINST (?  IN BOOLEAN MODE)) "+
			//handle the case that we are in the same year
				" AND ((S1.year>?) OR ((S1.year=?) AND (S1.slotbegin>?)))" +
				"   AND  (( (S1.year=S2.year) AND (S1.slotbegin<S1.slotend) AND ((S1.slotend+?)<=S2.slotbegin) AND ( S1.instid=S2.instid) AND (NOT EXISTS (SELECT * FROM reservations S3 WHERE (S3.year=S1.year) AND (S3.slotbegin>S1.slotbegin) AND (S3.slotbegin<S2.slotbegin) AND (S3.instid=S1.instid))) ) " +
				" OR " +
				//handle thae case that we are not in the same year
				"    (   ((S1.year+1)=S2.year)  AND ( S1.instid=S2.instid) " +
				"        AND ( ((S1.slotbegin<S1.slotend) AND ((S1.slotend+?)<=(?+S2.slotbegin))) OR ((S1.slotbegin>S1.slotend) AND ((S1.slotend+?)<=(S2.slotbegin))) ) " +
				"        AND  (NOT EXISTS (SELECT * FROM reservations S3 WHERE (S3.instid=S1.instid) AND ((S3.year=S1.year) AND (S3.slotbegin>S1.slotbegin)) OR ((S3.year=S2.year) AND (S3.slotbegin<S2.slotbegin)))      )       ))" +
				" ORDER BY syear,sbegin";
		PreparedStatement prepareStatement = conn.prepareStatement(query);
		prepareStatement.setString(1, keywords);
		prepareStatement.setInt(2, initialeTimeSlot.getYear());
		prepareStatement.setInt(3, initialeTimeSlot.getYear());
		prepareStatement.setInt(4, initialeTimeSlot.getSlotNumber());
		prepareStatement.setInt(5,k);
		prepareStatement.setInt(6,k);
		prepareStatement.setInt(7,TimeSlot.numberOfTimeSlotsInAYear);
		prepareStatement.setInt(8,k);
		return prepareStatement.executeQuery();
	}

	public static LinkedList<InstrumentAvilability> getOccupiedSlotsFromDatabase(TimeSlot initialeSlot,String type, int k,String login) throws SQLException
	{
		
		ResultSet set=null;Connection conn=null;
		try{
		 conn=DbManager.DbConnections.getInstance().getConnection();
		 set= executeOccupiedSlotsQuery(conn,initialeSlot,type,k,login);
		LinkedList<InstrumentAvilability> availibilityArrs = new LinkedList<InstrumentAvilability>();
		int flag=1,i=0;
		long currentInst=-1,nextInst;
		Avilability[] arr=null ;
		if (set.next()==false){
			//TODO: handle no instruments in the table
			flag=0;
		}
		while ((flag==1))
		{
			nextInst= set.getLong("id");
			if (currentInst!=nextInst)
			{
				
				arr = new Avilability[TimeSlot.numOfSlotInWeek()+k];
				i=0;
			}
			currentInst=nextInst;
			//handle an id that is available (it's fields will be NULL 
			//because of the LEFT OUTER JOIN
			set.getInt("slotbegin");
			if (!set.wasNull())
			{
				int offset=0;
				//to the case that ----|endyear|---start---end---|endweek|----
				if (set.getInt("year")!=initialeSlot.getYear())
				{
					offset=TimeSlot.numberOfTimeSlotsInAYear;
				}
				int endOffset=0;
				//to the case that ----start---|endyear|---end-----
				if (set.getInt("slotbegin")>set.getInt("slotend"))
				{
					endOffset=TimeSlot.numberOfTimeSlotsInAYear;
				}
				//The instrument has hours where he is not availible
				for (;(i+initialeSlot.getSlotNumber()<(offset+set.getInt("slotbegin")))&&(i<(TimeSlot.numOfSlotInWeek()+k)); i++)
				{
					arr[i]=Avilability.TAKEN;
				}
				for (; (i+initialeSlot.getSlotNumber()<(offset+endOffset+set.getInt("slotend")))&&(i<(TimeSlot.numOfSlotInWeek()+k)); i++)
				{
					arr[i]=Avilability.NOT_AVAILABLE;
				}
		
			}
			if (set.next()==false)
			{
					flag=1;
					for (;i<TimeSlot.numOfSlotInWeek()+k; i++)
					{
						arr[i]=Avilability.TAKEN;
					}
					addInstrumentAviablitily(currentInst,arr,availibilityArrs);
					break;
			}
			if (set.getLong("id")!=currentInst)
			{
				for (;i<TimeSlot.numOfSlotInWeek()+k; i++)
				{
					arr[i]=Avilability.TAKEN;
				}
				addInstrumentAviablitily(currentInst,arr,availibilityArrs);
			}

		}
			return availibilityArrs;
		}
		finally
		{
			if (set!=null){set.close();}
			if (conn!=null){conn.close();}
		}
		
	}
	
	private static void addInstrumentAviablitily(long currentInst,Avilability[] arr,LinkedList<InstrumentAvilability> availibilityArrs )
	{
		InstrumentAvilability avi = new InstrumentAvilability();
		avi.setAvilibilityArr(arr);
		avi.setInstId(currentInst);
		availibilityArrs.add(avi);
	}

	public static AvailabilityResult[] Search(TimeSlot slot, int k, String type,String login) throws SQLException {
		AvailabilityResult[] totalAvialableSlots = initializeTotalAvailableResults(TimeSlot.numOfSlotInWeek()+k); 
		Avilability[] occupiedSlotsForCurrInstrument = null;
		LinkedList<InstrumentAvilability> list= getOccupiedSlotsFromDatabase(slot, type,k,login);
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
			int numOfSlots) {
		AvailabilityResult[] temp = new AvailabilityResult[numOfSlots];
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
