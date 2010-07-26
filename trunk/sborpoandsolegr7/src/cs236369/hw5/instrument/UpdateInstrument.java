package cs236369.hw5.instrument;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs236369.hw5.DeafaultManipulator;
import cs236369.hw5.ErrorInfoBean;
import cs236369.hw5.Utils;
import cs236369.hw5.Utils.ParametersExp;
import cs236369.hw5.instrument.InstrumentManager.InstrumentExists;
import cs236369.hw5.instrument.InstrumentManager.InstrumentNotExists;
import cs236369.hw5.users.UserManager;
import cs236369.hw5.users.UserUtils;
import cs236369.hw5.users.UserManager.Unauthenticated;

/**
 * Servlet implementation class UpdateInstrument
 */
public class UpdateInstrument extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpdateInstrument() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ErrorInfoBean err = Utils.notSupported();
		Utils.forwardToErrorPage(err, request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		DeafaultManipulator manipulator = new DeafaultManipulator() {

			@Override
			public void paramsChecker(HashMap<String, String> params,
					ErrorInfoBean err) throws ParametersExp {
				updateInstrumentCheckParameters(params, err);

			}

			@Override
			public void returnLinkSetter(ErrorInfoBean err) {
				err.setLink("updateInstruments.jsp");
				err.setLinkStr("Try again");

			}

			@Override
			public void manipulate(HashMap<String, String> params,
					Object image, Object type) throws SQLException,
					InstrumentExists, InstrumentNotExists {
				InstrumentManager.updateInstrument(params
						.get(InstrumentManager.ID), params
						.get(InstrumentManager.Type), params
						.get(InstrumentManager.Premission), params
						.get(InstrumentManager.TimeSlot), params
						.get(InstrumentManager.Location), params
						.get(InstrumentManager.Description));

			}

			@Override
			public void authenticate(HashMap<String, String> params,
					HttpServletRequest request, HttpServletResponse respone)
					throws Unauthenticated {
				if ((request.getUserPrincipal() == null)
						|| (!UserUtils.isAdmin(request))) {
					throw new UserManager.Unauthenticated();
				}
			}
		};
		try {
			Utils.manipulateInstrument(request, response, manipulator);
		} catch (ParametersExp e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (cs236369.hw5.users.UserUtils.ParametersExp e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstrumentNotExists e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void updateInstrumentCheckParameters(
			HashMap<String, String> params, ErrorInfoBean err)
			throws cs236369.hw5.Utils.ParametersExp {
		if (!(params.containsKey(InstrumentManager.ID)
				&& params.containsKey(InstrumentManager.Location)
				&& params.containsKey(InstrumentManager.Premission)
				&& params.containsKey(InstrumentManager.TimeSlot) 
				&& params.containsKey(InstrumentManager.Type))) {
			err.setErrorString("Parameters Error");
			err.setReason("You must fill the mandatory fields");
			throw new Utils.ParametersExp(err);
		}
		try {
			Integer.parseInt(params.get(InstrumentManager.ID));

		} catch (NumberFormatException e) {
			err.setErrorString("Parameters Error");
			err.setReason("ID must be a number");
			throw new Utils.ParametersExp(err);
		}
		try {
			Integer.parseInt(params.get(InstrumentManager.Premission));
		} catch (NumberFormatException e) {
			err.setErrorString("Parameters Error");
			err.setReason("Permission must be a number");
			throw new Utils.ParametersExp(err);
		}

		int timeSlot = 0;

		try {
			timeSlot = Integer.parseInt(params.get(InstrumentManager.TimeSlot));
		} catch (NumberFormatException e) {
			err.setErrorString("Parameters Error");
			err.setReason("Time-Slot must be a number");
			throw new Utils.ParametersExp(err);
		}
		if (timeSlot != InstrumentManager.DEFUALT) {
			err.setErrorString("Parameters Error");
			err.setReason("Unsupported Time-Slot");
			throw new Utils.ParametersExp(err);
		}
	}

}
