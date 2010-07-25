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
		DeafaultManipulator manipulator = new DeafaultManipulator() {

			@Override
			public void paramsChecker(HashMap<String, String> params,
					ErrorInfoBean err) throws ParametersExp {
				makeReservationCheckParameters(params, err);

			}

			@Override
			public void returnLinkSetter(ErrorInfoBean err) {
				err.setLink("viewReservations.jsp");
				err.setLinkStr("Try again");

			}

			@Override
			public void manipulate(HashMap<String, String> params,
					Object image, Object type) throws SQLException,
					InstrumentExists, ReservationOverlapingException {
				ReservationManager.makeReservation(params.get("id"),
				params.get("slotYear"),
				params.get("slotNum"),
				params.get("k"), params.get("userID"));

			}

			@Override
			public void authenticate(HashMap<String, String> params,
					HttpServletRequest request, HttpServletResponse respone)
					throws Unauthenticated {
				if ((request.getUserPrincipal()==null) || (!UserUtils.isAdmin(request)))
				{
					throw new UserManager.Unauthenticated();
				}	
			}
		};
		try {
			Utils.manipulateReservation(request, response, manipulator);
		} catch (InstrumentNotExists e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileTooBigExp e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
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
		if (id<0 || slotNum<0 || slotYear<0 || k<0) {
			err.setErrorString("Parameters Error");
			err.setReason("All values must be above zero");
			throw new Utils.ParametersExp(err);
		}
		
	}

}
