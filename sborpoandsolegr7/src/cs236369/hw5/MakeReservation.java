package cs236369.hw5;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;

import cs236369.hw5.ReservationManager.ReservationOverlapingException;
import cs236369.hw5.Utils.ParametersExp;
import cs236369.hw5.instrument.InstrumentManager.InstrumentExists;
import cs236369.hw5.instrument.InstrumentManager.InstrumentNotExists;
import cs236369.hw5.users.UserManager;
import cs236369.hw5.users.UserUtils;
import cs236369.hw5.users.UserManager.Unauthenticated;
import cs236369.hw5.users.UserManager.UserNotExists;
import cs236369.hw5.users.UserUtils.FileTooBigExp;

/**
 * Servlet implementation class MakeReservation
 */
public class MakeReservation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeReservation() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HashMap<String, String> params= Utils.paramGetter(request, new String [] {"id","slotYear","slotNum","k","userId"});
		ErrorInfoBean err = new ErrorInfoBean();
		try{
			makeReservationCheckParameters(params, err);
			ReservationManager.makeReservation(params.get("id"),params.get("slotYear"),params.get("slotNum"),params.get("k"), params.get("userId"));
			Utils.forwardToSuccessPage("/sborpoandsolegr7/viewUsersReservations.jsp?"+UserManager.Usern+"="+params.get("userId"), request, response);
			return;
		}catch (ParametersExp e){
			
		} catch (SQLException e) {
			err.setErrorString("Database Error");
			err.setReason("There was an error updating the database ");
		} catch (ReservationOverlapingException e) {
			err.setErrorString("Requested Slot Error");
			err.setReason("The requested time slot is overlapping with other slot,<br/> please try other instrument" +
					"or other timeslot. ");
		} catch (InstrumentNotExists e) {
			err.setErrorString("Instrument Error");
			err.setReason("The requested instrument doesn't exists ");
		} catch (UserNotExists e) {
			err.setErrorString("User Error");
			err.setReason("The username which tries to reserve time slot <br/> doesn't exists in the system ");
		}

		Utils.forwardToErrorPage(err, request, response);
		
		
		
		
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ErrorInfoBean err = Utils.notSupported();
		Utils.forwardToErrorPage(err, request, response);
	}


	protected void makeReservationCheckParameters(
			HashMap<String, String> params, ErrorInfoBean err) throws ParametersExp {
		if (!(params.containsKey("id")
				&& params.containsKey("slotYear")
				&& params.containsKey("slotNum")
				&& params.containsKey("k")
				&& params.containsKey("userId"))) {
			err.setErrorString("Parameters Error");
			err.setReason("You must fill the mandatory fields");
			throw new Utils.ParametersExp(err);
		}
		int id,slotYear,slotNum,k;
		try {
			id=Integer.parseInt(params.get("id"));	
		} catch (NumberFormatException e) {
			err.setErrorString("Parameters Error");
			err.setReason("ID must be a number");
			throw new Utils.ParametersExp(err);
		}
		try {
			slotYear=Integer.parseInt(params.get("slotYear"));
		} catch (NumberFormatException e) {
			err.setErrorString("Parameters Error");
			err.setReason("Slot year must be a number");
			throw new Utils.ParametersExp(err);
		}
		try {
			slotNum=Integer.parseInt(params.get("slotNum"));
		} catch (NumberFormatException e) {
			err.setErrorString("Parameters Error");
			err.setReason("Slot number must be a number");
			throw new Utils.ParametersExp(err);
		}
		try {
			k=Integer.parseInt(params.get("k"));
		} catch (NumberFormatException e) {
			err.setErrorString("Parameters Error");
			err.setReason("K must be a number");
			throw new Utils.ParametersExp(err);
		}
		if (id<0 || slotNum<0 || slotYear<0 || slotNum>TimeSlot.numberOfTimeSlotsInAYear || k<0) {
			err.setErrorString("Parameters Error");
			err.setReason("The values are not legal");
			throw new Utils.ParametersExp(err);
		}
		
	}

}
