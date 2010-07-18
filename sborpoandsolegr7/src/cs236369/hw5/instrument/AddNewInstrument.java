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
import cs236369.hw5.users.UserManager;
import cs236369.hw5.users.UserUtils;
import cs236369.hw5.users.UserManager.Unauthenticated;

/**
 * Servlet implementation class AddNewInstrument
 */
public class AddNewInstrument extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddNewInstrument() {
		super();
		// TODO Auto-generated constructor stub
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
				addInstrumentCheckParameters(params, err);

			}

			@Override
			public void returnLinkSetter(ErrorInfoBean err) {
				err.setLink("AddInstrument.jsp");
				err.setLinkStr("Try again");

			}

			@Override
			public void manipulate(HashMap<String, String> params,
					Object image, Object type) throws SQLException,
					InstrumentExists {
				InstrumentManager.addInstrument(
						params.get(InstrumentManager.ID),
						params.get(InstrumentManager.Description), 
						params.get(InstrumentManager.Location), 
						params.get(InstrumentManager.Premission), 
						params.get(InstrumentManager.TimeSlot), 
						params.get(InstrumentManager.Type));

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
			Utils.manipulateInstrument(request, response, manipulator);
		} catch (ParametersExp e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (cs236369.hw5.users.UserUtils.ParametersExp e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void addInstrumentCheckParameters(HashMap<String, String> params,
			ErrorInfoBean err) throws cs236369.hw5.Utils.ParametersExp {
		if (!(params.containsKey(InstrumentManager.ID)
		//		&& params.containsKey(InstrumentManager.Description)
				&& params.containsKey(InstrumentManager.Location)
				&& params.containsKey(InstrumentManager.Premission)
				&& params.containsKey(InstrumentManager.TimeSlot) && params
				.containsKey(InstrumentManager.Type))) {
			err.setErrorString("Parameters Error");
			err.setReason("You must fill the mandatory fields");
			throw new Utils.ParametersExp(err);
		}

	}

}
