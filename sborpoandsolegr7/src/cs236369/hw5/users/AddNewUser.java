package cs236369.hw5.users;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cs236369.hw5.ErrorInfoBean;
import cs236369.hw5.DeafaultManipulator;
import cs236369.hw5.Utils;
import cs236369.hw5.User.UserType;
import cs236369.hw5.Utils.ParametersExp;
import cs236369.hw5.instrument.InstrumentManager.InstrumentExists;
import cs236369.hw5.users.UserManager.LeaderNotExists;
import cs236369.hw5.users.UserManager.Unauthenticated;
import cs236369.hw5.users.UserManager.UserExists;
import cs236369.hw5.users.UserManager.UserNotExists;

/**
 * Servlet implementation class AddNewUser
 */
public class AddNewUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddNewUser() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ErrorInfoBean err= Utils.notSupported();
		Utils.forwardToErrorPage(err,request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DeafaultManipulator manipulator = new DeafaultManipulator() {
			
			@Override
			public void paramsChecker(HashMap<String, String> params, ErrorInfoBean err)
					throws ParametersExp {
				addUserCheckParameters(params,err);
				
			}
			
			@Override
			public void manipulate(HashMap<String, String> params, Object imageBlob,
					Object databaseUserType) throws UserExists, UserNotExists, SQLException, LeaderNotExists {
				UserManager.AddUser(params.get(UserManager.Usern), params.get(UserManager.Password), params.get(UserManager.Group), params.get(UserManager.Permission), params.get(UserManager.Name), params.get(UserManager.PhoneNumber), params.get(UserManager.Address),(Blob) imageBlob,(UserType) databaseUserType,params.get(UserManager.Email));
				
			}

			@Override
			public void returnLinkSetter(ErrorInfoBean err) {
				err.setLink("addUser.jsp"); err.setLinkStr("Try again");
				
			}

			@Override
			public void authenticate(HashMap<String, String> params,
					HttpServletRequest request, HttpServletResponse respone)
					throws Unauthenticated {
				
			}
		};
		try {
			UserUtils.manipulateUser(request, response, manipulator);
		} catch (InstrumentExists e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (ParametersExp e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private ArrayList<String> requiredFieldsToAddUser()
	{
		ArrayList<String> required = new ArrayList<String>();
		required.add(UserManager.Usern);
		required.add(UserManager.Password);
		required.add(UserManager.PassConfirm);
		required.add(UserManager.UserTypen);
		required.add(UserManager.Name);
		required.add(UserManager.Captcha);
		required.add(UserManager.Email);
		return required;
	}

	private void addUserCheckParameters(HashMap<String, String> params,ErrorInfoBean err) throws Utils.ParametersExp {
		 ArrayList<String> requiered = requiredFieldsToAddUser();
		 for (String field : requiered) {
			if (!params.containsKey(field))
			{
				err.setErrorString("Parameters Error");
				err.setReason("You must fill the mandatory fields");
				throw new Utils.ParametersExp(err);
			}
		}
		if (!(params.get(UserManager.Password).equals(params.get(UserManager.PassConfirm))))
			{
				err.setErrorString("Password Error");
				err.setReason("The password and the confirmation password don't match");
				throw new Utils.ParametersExp(err);
			}
		if (!UserUtils.isValidEmail(params.get(UserManager.Email)))
		{
			err.setErrorString("Email Error");
			err.setReason("The email that you specified is not a valid one");
			throw new Utils.ParametersExp(err);
		}
		if (params.containsKey(UserManager.PhoneNumber))
		{
			if (!params.get(UserManager.PhoneNumber).equals(""))
			{
				if (!(UserUtils.containsOnlyNumbers(params.get(UserManager.PhoneNumber))))
				{
					err.setErrorString("Phone Number Error");
					err.setReason("The phone number should contain only numbers");
					throw new Utils.ParametersExp(err);
				}
			}
		}
		else
		{
			params.put(UserManager.PhoneNumber, null);
		}
		if (!params.containsKey(UserManager.Address))
		{
			params.put(UserManager.Address, null);
		}
		params.put(UserManager.Permission, "");
		if (params.get(UserManager.UserTypen).equals("Admin"))
		{
			if ((!params.containsKey(UserManager.AdminAuth))|| (!(params.get(UserManager.AdminAuth).equals(UserUtils.authorizationStr))))
			{
				err.setErrorString("Authentication Key Error");
				err.setReason("The authentication key was not supplied");
				throw new Utils.ParametersExp(err);
			}
			params.put(UserManager.Permission, null);
		}
		validateGroup(params,err);
		if ((!(params.get(UserManager.UserTypen).equals("Admin"))) && (!(params.get(UserManager.UserTypen).equals("Researcher"))))
		{
			err.setErrorString("User Type Error");
			err.setReason("The user type should be only Reasearcher or Admin");
			throw new Utils.ParametersExp(err);
		}
		if (!UserManager.isParamLengthGood(params))
		{
			err.setErrorString("Parameter Length Error");
			err.setReason("Some of your parameters may be too long");
			throw new Utils.ParametersExp(err);
		}
		

	}

	public static void validateGroup(HashMap<String, String> params,ErrorInfoBean err) throws Utils.ParametersExp  {
		if (params.get(UserManager.UserTypen).equals("Admin"))
		{
			params.put(UserManager.Group, "root");
		}
		else{
			if (!params.containsKey(UserManager.Group))
			{
				err.setErrorString("Group Error");
				err.setReason("You didn't supplied a group leader");
				throw new Utils.ParametersExp(err);
			}
			if (params.get(UserManager.Group).equals(params.get(UserManager.Usern)))
			{
				return;
			}
			ArrayList<String> groups= new ArrayList<String>();
			try {
				groups = UserManager.getGroups();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (!(groups.contains(params.get(UserManager.Group))))
			{
				err.setErrorString("Group Error");
				err.setReason("The group leader that you have entered doesn't exists in the system");
				throw new Utils.ParametersExp(err);
			}
		}
		
	}

}
