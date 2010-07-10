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
import cs236369.hw5.users.UserManager.LeaderNotExists;
import cs236369.hw5.users.UserManager.Unauthenticated;
import cs236369.hw5.users.UserManager.UserExists;
import cs236369.hw5.users.UserManager.UserNotExists;
import cs236369.hw5.users.UserUtils.ParametersExp;

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
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
DeafaultManipulator manipulator = new DeafaultManipulator() {
			
			@Override
			public void paramsChecker(HashMap<String, String> params, ErrorInfoBean err)
					throws Utils.ParametersExp, ParametersExp {
				UpdateUser.updateUserCheckParameters(params,err);
				
			}
			
			@Override
			public void manipulate(HashMap<String, String> params, Object imageBlob,
					Object databaseUserType) throws UserExists, UserNotExists, SQLException, LeaderNotExists {
				UserManager.updateUser(params.get(UserManager.Usern), params.get(UserManager.Permission), params.get(UserManager.Group), null, params.get(UserManager.Name), params.get(UserManager.PhoneNumber), params.get(UserManager.Address),(Blob) imageBlob, (UserType)databaseUserType,params.get(UserManager.Email));
				
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
		} catch (ParametersExp e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstrumentExists e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (cs236369.hw5.Utils.ParametersExp e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
