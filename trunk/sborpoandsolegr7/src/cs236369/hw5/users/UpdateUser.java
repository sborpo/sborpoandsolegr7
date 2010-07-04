package cs236369.hw5.users;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
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
		UserUtils.forwardToErrorPage(err,request,response);
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
					throws ParametersExp {
				updateUserCheckParameters(params,err);
				
			}
			
			@Override
			public void manipulate(HashMap<String, String> params, Object imageBlob,
					Object databaseUserType) throws UserExists, UserNotExists, SQLException {
				UserManager.updateUser(params.get(UserManager.Usern), null, params.get(UserManager.Group), null, params.get(UserManager.Name), params.get(UserManager.PhoneNumber), params.get(UserManager.Address),(Blob) imageBlob, (UserType)databaseUserType);
				
			}

			@Override
			public void returnLinkSetter(ErrorInfoBean err) {
				err.setLink("viewUsers.jsp"); err.setLinkStr("Try again");
				
			}
		};
		UserUtils.manipulateUser(request, response, manipulator);
	}

	private void updateUserCheckParameters(HashMap<String, String> params,
			ErrorInfoBean err) throws cs236369.hw5.users.UserUtils.ParametersExp {
		if (!(params.containsKey(UserManager.Group)&&params.containsKey(UserManager.UserTypen)&&params.containsKey(UserManager.Name)&&params.containsKey(UserManager.Captcha)))
		{
			err.setErrorString("Parameters Error");
			err.setReason("The mandatory fields should be filled");
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
