package cs236369.hw5.instrument;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;

import cs236369.hw5.ErrorInfoBean;
import cs236369.hw5.DeafaultManipulator;
import cs236369.hw5.Utils;
import cs236369.hw5.User.UserType;
import cs236369.hw5.users.UserManager;
import cs236369.hw5.users.UserUtils;
import cs236369.hw5.users.UserManager.UserExists;
import cs236369.hw5.users.UserManager.UserNotExists;
import cs236369.hw5.users.UserUtils.ParametersExp;

/**
 * Servlet implementation class AddNewUser
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

		DeafaultManipulator manipulator = new DeafaultManipulator() {
			
			@Override
			public void paramsChecker(HashMap<String, String> params, ErrorInfoBean err)
					throws ParametersExp {
				addUserCheckParameters(params,err);
				
			}
			
			

			@Override
			public void returnLinkSetter(ErrorInfoBean err) {
				err.setLink("AddInstrument.jsp"); err.setLinkStr("Try again");
				
			}



			@Override
			public void manipulate(HashMap<String, String> params,
					Object image, Object type) throws UserExists,
					UserNotExists, SQLException {
				UserManager.AddUser(params.get(UserManager.Usern), params.get(UserManager.Password), params.get(UserManager.Group), "", params.get(UserManager.Name), params.get(UserManager.PhoneNumber), params.get(UserManager.Address),(Blob) image,(UserType) type);
				
			}
		};
		UserUtils.manipulateUser(request, response, manipulator);

	}
	


	private void addUserCheckParameters(HashMap<String, String> params,ErrorInfoBean err) throws UserUtils.ParametersExp {
		if (!(params.containsKey(UserManager.Usern)&&params.containsKey(UserManager.Password)&&params.containsKey(UserManager.PassConfirm)&&params.containsKey(UserManager.Group)&&params.containsKey(UserManager.UserTypen)&&params.containsKey(UserManager.Name)&&params.containsKey(UserManager.Captcha)))
		{
			err.setErrorString("Parameters Error");
			err.setReason("You must fill the mandatory fields");
			throw new UserUtils.ParametersExp(err);
		}
		if (!(params.get(UserManager.Password).equals(params.get(UserManager.PassConfirm))))
			{
				err.setErrorString("Password Error");
				err.setReason("The password and the confirmation password don't match");
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
		

	}

}
