package cs236369.hw5.users;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs236369.hw5.DeafaultManipulator;
import cs236369.hw5.ErrorInfoBean;
import cs236369.hw5.Utils;
import cs236369.hw5.User.UserType;
import cs236369.hw5.instrument.InstrumentManager.InstrumentExists;
import cs236369.hw5.instrument.InstrumentManager.InstrumentNotExists;
import cs236369.hw5.users.UserManager.LeaderNotExists;
import cs236369.hw5.users.UserManager.Unauthenticated;
import cs236369.hw5.users.UserManager.UserExists;
import cs236369.hw5.users.UserManager.UserNotExists;

/**
 * Servlet implementation class AdminUpdateUser
 */
public class AdminUpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminUpdateUser() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
DeafaultManipulator manipulator = new DeafaultManipulator() {
			
			@Override
			public void paramsChecker(HashMap<String, String> params, ErrorInfoBean err)
					throws Utils.ParametersExp {
				UpdateUser.updateUserCheckParameters(params,err);
				
			}
			
			@Override
			public void manipulate(HashMap<String, String> params, Object imageBlob,
					Object databaseUserType) throws UserExists, UserNotExists, SQLException, LeaderNotExists {
				UserManager.updateUser(params.get(UserManager.Usern),null, params.get(UserManager.Group),  params.get(UserManager.Permission), params.get(UserManager.Name), params.get(UserManager.PhoneNumber), params.get(UserManager.Address),(Blob) imageBlob, (UserType)databaseUserType,params.get(UserManager.Email));
				
			}

			@Override
			public void returnLinkSetter(ErrorInfoBean err) {
				err.setLink("viewUsers.jsp"); err.setLinkStr("Try again");
				
			}
			
			@Override
			public void authenticate(HashMap<String, String> params,
					HttpServletRequest request, HttpServletResponse respone) throws Unauthenticated {
				if (request.getUserPrincipal()==null)
				{
					throw new UserManager.Unauthenticated();
				}
				if (UserUtils.isAdmin(request))
				{
					return;
				}
				throw new UserManager.Unauthenticated();
				
			}
			
		};
		try {
			UserUtils.manipulateUser(request, response, manipulator);
		} catch (InstrumentExists e) {
			e.printStackTrace();
		} catch (cs236369.hw5.Utils.ParametersExp e) {
			e.printStackTrace();
		} catch (InstrumentNotExists e) {
			e.printStackTrace();
		}
	}

}
