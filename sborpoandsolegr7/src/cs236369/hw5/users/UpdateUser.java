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
import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.fileupload.FileUploadException;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;

import cs236369.hw5.ErrorInfoBean;
import cs236369.hw5.DeafaultManipulator;
import cs236369.hw5.Utils;
import cs236369.hw5.InstrumentManager.InstrumentExists;
import cs236369.hw5.User.UserType;
import cs236369.hw5.users.UserManager.UserExists;
import cs236369.hw5.users.UserManager.UserNotExists;
import cs236369.hw5.users.UserUtils.ParametersExp;

/**
 * Servlet implementation class UpdateUser
 */
public class UpdateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUser() {
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
		//TODO: admin update
		//check if user tries to change it's data , and not someone else
		DeafaultManipulator manipulator = new DeafaultManipulator() {
			
			@Override
			public void paramsChecker(HashMap<String, String> params, ErrorInfoBean err)
					throws Utils.ParametersExp, ParametersExp {
				updateUserCheckParameters(params,err);
				
			}
			
			@Override
			public void manipulate(HashMap<String, String> params, Object imageBlob,
					Object databaseUserType) throws UserExists, UserNotExists, SQLException {
				UserManager.updateUser(params.get(UserManager.Usern), null, params.get(UserManager.Group), null, params.get(UserManager.Name), params.get(UserManager.PhoneNumber), params.get(UserManager.Address),(Blob) imageBlob, (UserType)databaseUserType,params.get(UserManager.Email));
				
			}

			@Override
			public void returnLinkSetter(ErrorInfoBean err) {
				err.setLink("viewUsers.jsp"); err.setLinkStr("Try again");
				
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
	
	private ArrayList<String> requiredFieldsToUpdateUser()
	{
		ArrayList<String> required = new ArrayList<String>();
		required.add(UserManager.Usern);
		required.add(UserManager.Group);
		required.add(UserManager.UserTypen);
		required.add(UserManager.Name);
		required.add(UserManager.Captcha);
		required.add(UserManager.Email);
		return required;
	}

	private void updateUserCheckParameters(HashMap<String, String> params,
			ErrorInfoBean err) throws cs236369.hw5.users.UserUtils.ParametersExp {
		
		 ArrayList<String> requiered = requiredFieldsToUpdateUser();
		 for (String field : requiered) {
			if (!params.containsKey(field))
			{
				err.setErrorString("Parameters Error");
				err.setReason("You must fill the mandatory fields");
				throw new UserUtils.ParametersExp(err);
			}
		}
		if (!UserUtils.isValidEmail(params.get(UserManager.Email)))
			{
				err.setErrorString("Email Error");
				err.setReason("The email that you specified is not a valid one");
				throw new UserUtils.ParametersExp(err);
			}
		if (params.containsKey(UserManager.PhoneNumber))
		{
			if (!(UserUtils.containsOnlyNumbers(params.get(UserManager.PhoneNumber))))
			{
				err.setErrorString("Phone Number Error");
				err.setReason("The phone number should contain only numbers");
				throw new UserUtils.ParametersExp(err);
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
		if ((!(params.get(UserManager.UserTypen).equals("Admin"))) && (!(params.get(UserManager.UserTypen).equals("Researcher"))))
		{
			err.setErrorString("User Type Error");
			err.setReason("The user type should be only Reasearcher or Admin");
			throw new UserUtils.ParametersExp(err);
		}
		//TODO : check if group leader is on
		
	}

}
