package cs236369.hw5;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs236369.hw5.Utils.ParametersExp;
import cs236369.hw5.users.UserManager;
import cs236369.hw5.users.UserUtils;
import cs236369.hw5.users.UserManager.Unauthenticated;

/**
 * Servlet implementation class DeleteReservedTimeSlot
 */
public class DeleteReservedTimeSlot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteReservedTimeSlot() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HashMap<String, String> params= Utils.paramGetter(request, new String[] {"id","year","slotNum","userId"});
		ErrorInfoBean err = new ErrorInfoBean();
		try {
			DeleteReservationCheckParameters(request, params, err);
			ReservationManager.deleteReservationFromDb(params.get("id"),params.get("year"),params.get("slotNum"),params.get("userId"));
			Utils.forwardToSuccessPage("/sborpoandsolegr7/viewUsersReservations.jsp?"+UserManager.Usern+"="+request.getUserPrincipal().getName(), request, response);
			return;
		} catch (ParametersExp e) {
			
		} catch (Unauthenticated e) {
			response.sendError(501);
			return;
		} catch (SQLException e) {
			err.setErrorString("Database Error");
			err.setReason("An error occured while trying to remove reservation");
		}
		Utils.forwardToErrorPage(err, request, response);

	}
	
	protected void DeleteReservationCheckParameters(HttpServletRequest request,
			HashMap<String, String> params, ErrorInfoBean err) throws ParametersExp, Unauthenticated {
		if (!(params.containsKey("id")
				&& params.containsKey("year")
				&& params.containsKey("slotNum")
				&& params.containsKey("userId"))) {
			err.setErrorString("Parameters Error");
			err.setReason("You must fill the mandatory fields");
			throw new Utils.ParametersExp(err);
		}
		long id;
		int slotYear,slotNum,k;
		try {
			id =Long.parseLong(params.get("id"));	
		} catch (NumberFormatException e) {
			err.setErrorString("Parameters Error");
			err.setReason("ID must be a number");
			throw new Utils.ParametersExp(err);
		}
		try {
			slotYear=Integer.parseInt(params.get("year"));
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
		if (id<0 || slotNum<0 || slotNum>TimeSlot.numberOfTimeSlotsInAYear || slotYear<0) {
			err.setErrorString("Parameters Error");
			err.setReason("The values are not legal");
			throw new Utils.ParametersExp(err);
		}
		if ((!request.getUserPrincipal().getName().equals(params.get("userId")))&&(!UserUtils.isAdmin(request)))
		{
			throw new UserManager.Unauthenticated();
		}
		
	}

}
